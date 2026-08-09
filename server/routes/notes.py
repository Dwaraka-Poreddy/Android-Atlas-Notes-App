"""
routes/notes.py

Notes resource, scoped per-user, plus a batch /notes/sync endpoint that
implements last-write-wins conflict resolution for an offline-first
client's sync queue.

All endpoints here require authentication (see auth.get_current_user) and
every read/write is filtered to the current user's own notes - a note id
that exists but belongs to someone else is treated the same as a note id
that doesn't exist at all, so ownership is never leaked.
"""

import uuid
from datetime import datetime, timezone
from typing import Optional

from fastapi import APIRouter, Depends, HTTPException, Query

import storage
from auth import get_current_user
from schemas import NoteListResponse, Note, SyncRequest, SyncResponse

router = APIRouter()

NOTES_FILE = "notes.json"


def _now() -> str:
    return datetime.now(timezone.utc).isoformat()


@router.get("/notes", response_model=NoteListResponse)
def get_notes(
    search: Optional[str] = Query(None, description="Search in note title or content"),
    page: int = Query(1, ge=1),
    pageSize: int = Query(20, ge=1, le=100),
    current_user: dict = Depends(get_current_user),
):
    notes = storage.load_json(NOTES_FILE)
    notes = [n for n in notes if n["userId"] == current_user["id"] and not n["deleted"]]

    if search:
        search_lower = search.lower()
        notes = [
            n for n in notes
            if search_lower in n["title"].lower() or search_lower in n["content"].lower()
        ]

    start = (page - 1) * pageSize
    end = start + pageSize

    return {"notes": notes[start:end]}


@router.get("/notes/{note_id}", response_model=Note)
def get_note(note_id: str, current_user: dict = Depends(get_current_user)):
    notes = storage.load_json(NOTES_FILE)

    for note in notes:
        if note["id"] == note_id and note["userId"] == current_user["id"]:
            return note

    raise HTTPException(status_code=404, detail=f"Note {note_id} not found")


@router.post("/notes/sync", response_model=SyncResponse)
def sync_notes(body: SyncRequest, current_user: dict = Depends(get_current_user)):
    notes = storage.load_json(NOTES_FILE)
    results = []

    for op in body.operations:
        try:
            if op.operationType == "UPSERT" and op.noteId is None:
                new_note = {
                    "id": str(uuid.uuid4()),
                    "userId": current_user["id"],
                    "title": op.title or "",
                    "content": op.content or "",
                    "folderOrLabel": op.folderOrLabel,
                    "updatedAt": _now(),
                    "deleted": False,
                }
                notes.append(new_note)
                results.append({"localId": op.localId, "status": "applied", "note": new_note})

            elif op.operationType == "UPSERT":
                existing = next(
                    (n for n in notes if n["id"] == op.noteId and n["userId"] == current_user["id"]),
                    None,
                )
                if existing is None:
                    results.append({"localId": op.localId, "status": "error", "message": "Note not found"})
                    continue

                if op.clientUpdatedAt >= existing["updatedAt"]:
                    existing["title"] = op.title
                    existing["content"] = op.content
                    existing["folderOrLabel"] = op.folderOrLabel
                    existing["updatedAt"] = _now()
                    results.append({"localId": op.localId, "status": "applied", "note": existing})
                else:
                    results.append({"localId": op.localId, "status": "conflict", "note": existing})

            elif op.operationType == "DELETE":
                existing = next(
                    (n for n in notes if n["id"] == op.noteId and n["userId"] == current_user["id"]),
                    None,
                )
                if existing is None:
                    results.append({"localId": op.localId, "status": "error", "message": "Note not found"})
                    continue

                existing["deleted"] = True
                existing["updatedAt"] = _now()
                results.append({"localId": op.localId, "status": "applied", "note": existing})

            else:
                results.append({
                    "localId": op.localId,
                    "status": "error",
                    "message": f"Unknown operationType: {op.operationType}",
                })

        except Exception as exc:  # noqa: BLE001 - one bad operation must not fail the batch
            results.append({"localId": op.localId, "status": "error", "message": str(exc)})

    storage.save_json(NOTES_FILE, notes)

    return {"results": results}
