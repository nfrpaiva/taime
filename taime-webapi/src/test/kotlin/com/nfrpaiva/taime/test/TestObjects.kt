package com.nfrpaiva.taime.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.nfrpaiva.taime.dominio.Apontamento
import com.nfrpaiva.taime.dominio.Cliente
import com.nfrpaiva.taime.dominio.Trabalho
import org.slf4j.LoggerFactory
import java.util.*

class TestObjects{
}
    val log = LoggerFactory.getLogger(TestObjects::class.java)

fun trabalho(): Trabalho {

    return Trabalho(id(), "Um Trabalho", cliente())
}

fun cliente(): Cliente {
    return Cliente(id(), "Um Cliente", "Nilton")
}

fun Any.json(mapper: ObjectMapper): String {
    val result = mapper.writeValueAsString(this)
    log.debug(result)
    return result
}

fun apontamento() : Apontamento {
   return Apontamento(id(), "Um Apontamento",trabalho = trabalho())
}

private fun id(): Long = (Random().nextInt(10000 - 1) + 1).toLong()
