package com.nfrpaiva.taime.infra

import com.nfrpaiva.taime.dominio.Apontamento
import com.nfrpaiva.taime.dominio.Trabalho
import com.nfrpaiva.taime.dominio.TrabalhoRepository
import com.nfrpaiva.taime.dto.ApontamentoDTO
import com.nfrpaiva.taime.exception.TaimeException
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

fun ApontamentoDTO.toEntity(trabalhoRepository: TrabalhoRepository): Apontamento {
    val trabalho : Trabalho =   trabalhoRepository.findById(this.trabalhoID).orElseThrow { TaimeException("Trabalho não Encontrado") }
    return Apontamento(this.id, this.nome, this.inicio, this.fim,trabalho)
}