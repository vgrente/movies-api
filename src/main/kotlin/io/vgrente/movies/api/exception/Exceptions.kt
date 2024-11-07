package io.vgrente.movies.api.exception

class ResourceNotFoundException(message: String) : RuntimeException(message)

class ValidationException(message: String) : RuntimeException(message)
