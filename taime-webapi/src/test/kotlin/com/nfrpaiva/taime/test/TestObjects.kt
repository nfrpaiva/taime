package com.nfrpaiva.taime.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.nfrpaiva.taime.dominio.Cliente
import com.nfrpaiva.taime.dominio.Trabalho
import java.util.*

class TestObjects

fun trabalho(): Trabalho {

    return Trabalho(id(), "Um Trabalho", cliente())
}

fun cliente(): Cliente {
    return Cliente (id(), "Um Cliente", "TestUser")
}

fun Any.json (mapper: ObjectMapper) : String  = mapper.writeValueAsString(this)

private fun id() : Long = (Random().nextInt(100-1) + 1).toLong()
