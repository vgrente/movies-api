package io.vgrente.movies.api.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(
        ex: Exception,
        request: WebRequest,
    ): ResponseEntity<ErrorDetails> {
        val errorDetails =
            ErrorDetails(
                LocalDateTime.now(),
                ex.message ?: "Unexpected error",
                request.getDescription(false),
            )
        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        ex: ResourceNotFoundException,
        request: WebRequest,
    ): ResponseEntity<ErrorDetails> {
        val errorDetails =
            ErrorDetails(
                LocalDateTime.now(),
                ex.message ?: "Resource not found",
                request.getDescription(false),
            )
        return ResponseEntity(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(
        ex: ValidationException,
        request: WebRequest,
    ): ResponseEntity<ErrorDetails> {
        val errorDetails =
            ErrorDetails(
                LocalDateTime.now(),
                ex.message ?: "Validation error",
                request.getDescription(false),
            )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }
}
