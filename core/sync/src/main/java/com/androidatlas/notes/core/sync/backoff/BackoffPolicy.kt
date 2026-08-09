package com.androidatlas.notes.core.sync.backoff

import kotlin.random.Random

class BackoffPolicy {
    private val baseDelayMs = 1000L // 1 second
    private val maxDelayMs = 30000L // 30 seconds
    private val maxRetries = 5

    fun getDelayMs(attemptNumber: Int): Long {
        if (attemptNumber > maxRetries) return -1L // Stop retrying

        // Exponential backoff: 2^attempt * baseDelay
        val exponentialDelay = baseDelayMs * (1L shl attemptNumber)

        // Cap at max
        val cappedDelay = minOf(exponentialDelay, maxDelayMs)

        // Add jitter (random ±10%)
        val jitter = (cappedDelay * 0.1 * Random.nextDouble()).toLong()
        val jitterDirection = if (Random.nextBoolean()) 1 else -1

        return cappedDelay + (jitterDirection * jitter)
    }

    fun shouldRetry(attemptNumber: Int): Boolean {
        return attemptNumber <= maxRetries
    }
}
