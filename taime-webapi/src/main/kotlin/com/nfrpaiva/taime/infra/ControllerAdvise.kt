package com.nfrpaiva.taime.infra

import com.nfrpaiva.taime.exception.TaimeException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvise {
    @ExceptionHandler(value = [TaimeException::class])
    fun badRequest(taimeException: TaimeException): ResponseEntity<*>{
        return ResponseEntity.badRequest().body(taimeException.message)
    }

}

