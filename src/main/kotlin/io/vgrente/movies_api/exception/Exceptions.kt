package io.vgrente.movies_api.exception

class ResourceNotFoundException(message: String) : RuntimeException(message)

class ValidationException(message: String) : RuntimeException(message)
