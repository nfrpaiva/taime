package com.nfrpaiva.taime.application.dto

import com.nfrpaiva.taime.dominio.Apontamento
import com.nfrpaiva.taime.dominio.Trabalho
import java.util.*

fun List<Apontamento>.toDTO(): List<ApontamentoDTO> {
    return this.map { it.toDTO() }
}

fun Apontamento.toDTO(): ApontamentoDTO {
    return ApontamentoDTO(this.id, this.descricao, this.inicio, this.fim, this.trabalho.id)
}

fun Optional<Apontamento>.toDTO(): Optional<ApontamentoDTO> {
    return if (this.isPresent) {
        Optional.of(this.get().toDTO())
    } else {
        Optional.empty()
    }
}
fun ApontamentoDTO.toDomain(trabalho:Trabalho): Apontamento {
    return Apontamento(this.id, this.nome, this.inicio, this.fim, trabalho)
}