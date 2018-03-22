package com.nfrpaiva.taime.vo

import com.nfrpaiva.taime.application.dto.ApontamentoDTO

fun ApontamentoDTO.toVO(): ApontamentoVO {
    return ApontamentoVO(this.id, this.nome, this.inicio, this.fim, this.trabalhoID)
}

fun ApontamentoVO.toDTO(): ApontamentoDTO {
    return ApontamentoDTO(this.id, this.nome, this.inicio, this.fim, this.trabalhoID)
}