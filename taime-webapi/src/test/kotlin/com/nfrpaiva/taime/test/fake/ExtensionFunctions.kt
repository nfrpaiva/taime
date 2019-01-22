package com.nfrpaiva.taime.test.fake

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

fun Any?.toJson(): String {
    val mapper = ObjectMapper()
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true)
    return if (this != null) mapper.writeValueAsString(this) else "null"
}