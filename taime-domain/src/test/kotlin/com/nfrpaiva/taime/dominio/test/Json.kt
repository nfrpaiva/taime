package com.nfrpaiva.taime.dominio.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

fun Any.json(): String {
    val mapper = ObjectMapper()
    mapper.enable(SerializationFeature.INDENT_OUTPUT)
    return mapper.writeValueAsString(this)
}