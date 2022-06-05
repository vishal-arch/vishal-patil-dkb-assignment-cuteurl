package com.dkb.miniurl.controller

import com.fasterxml.jackson.core.JsonProcessingException
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import javax.persistence.EntityNotFoundException
import javax.servlet.http.HttpServletRequest

/**
 * The class represent a centralized exception handling for the exception that are thrown by the System
 */
@ControllerAdvice
class ExceptionController {

    data class ErrorObject(
        var title: String = "",
        var details: String = ""
    )

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class])
    fun handleException(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorObject> {
        commonErrorLogging(request.requestURI, exception)
        var error = ErrorObject(
            "An error occurred",
            "The operation cannot be completed, Please try after some time"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .body(error)

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [MethodArgumentNotValidException::class, JsonProcessingException::class])
    fun handleInputException(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorObject> {
        commonErrorLogging(request.requestURI, exception)
        var error = ErrorObject(
            "Request parameter validation failed",
            "Validation failed for provided input parameters."
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .body(error)

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun handleNotFoundException(
        exception: Exception, request: HttpServletRequest
    ): ResponseEntity<ErrorObject> {
        commonErrorLogging(request.requestURI, exception)
        var error =
            ErrorObject("Error while searching for a not existing entity", exception.message ?: "")
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .body(error)

    }

    private fun commonErrorLogging(requestUri: String, exp: Exception) {
        //Log here
    }

}