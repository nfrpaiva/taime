package com.nfrpaiva.taime.infra

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

fun Any?.json(): String {
    val mapper = ObjectMapper()
    return if (this != null) mapper.writeValueAsString(this) else "null"
}
fun Any?.jsonIdented(): String {
    val mapper = ObjectMapper()
    mapper.enable(SerializationFeature.INDENT_OUTPUT)
    return if (this != null) mapper.writeValueAsString(this) else "null"
}