package com.nfrpaiva.taime.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.nfrpaiva.taime.dominio.Apontamento
import com.nfrpaiva.taime.dominio.Cliente
import com.nfrpaiva.taime.dominio.Trabalho
import java.util.*

class TestObjects

fun trabalho(): Trabalho {

    return Trabalho(id(), "Um Trabalho", cliente())
}

fun cliente(): Cliente {
    return Cliente(id(), "Um Cliente", "TestUser")
}

fun Any.json(mapper: ObjectMapper): String {
    val result = mapper.writeValueAsString(this)
    println(result)
    return result
}

fun apontamento() : Apontamento {
   return Apontamento(id(), "Um Apontamento",trabalho = trabalho())
}

private fun id(): Long = (Random().nextInt(10000 - 1) + 1).toLong()
