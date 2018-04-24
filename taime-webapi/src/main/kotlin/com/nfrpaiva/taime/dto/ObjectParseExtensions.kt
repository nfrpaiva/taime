package com.nfrpaiva.taime.dto

import com.nfrpaiva.taime.dominio.Apontamento
import com.nfrpaiva.taime.dominio.Cliente
import com.nfrpaiva.taime.dominio.Trabalho

fun Apontamento.toDTO(): ApontamentoDTO {
    return ApontamentoDTO(this.id, this.nome, this.inicio, this.fim, this.trabalho.id)
}
fun Trabalho.toDTO() : TrabalhoDTO {
    return TrabalhoDTO(this.id, this.nome, this.cliente.id)
}

fun Cliente.toDTO() : ClienteDTO{
    return ClienteDTO(this.id, this.nome)
}