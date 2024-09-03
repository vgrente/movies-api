package io.vgrente.movies_api.exception

import java.time.LocalDateTime

data class ErrorDetails(val timestamp: LocalDateTime, val message: String, val details: String )
