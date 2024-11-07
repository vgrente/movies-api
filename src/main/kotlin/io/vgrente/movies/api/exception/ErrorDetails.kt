package io.vgrente.movies.api.exception

import java.time.LocalDateTime

data class ErrorDetails(val timestamp: LocalDateTime, val message: String, val details: String)
