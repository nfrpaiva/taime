package com.nfrpaiva.taime.infra

import com.nfrpaiva.taime.dominio.Apontamento
import com.nfrpaiva.taime.dto.ApontamentoDTO
import java.util.*

fun List<Apontamento>.toDTO(): List<ApontamentoDTO> {
    return this.map { it.toDTO() }
}

fun Apontamento.toDTO(): ApontamentoDTO {
    return ApontamentoDTO(this.id, this.descricao, this.inicio, this.fim, this.trabalho.id)
}

fun Optional<Apontamento>.toDTO(): Optional<ApontamentoDTO> {
    if (this.isPresent) {
        return Optional.of(this.get().toDTO())
    } else {
        return Optional.empty<ApontamentoDTO>()
    }
}