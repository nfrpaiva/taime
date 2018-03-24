package com.nfrpaiva.taime.vo

import com.nfrpaiva.taime.dominio.Apontamento

fun Apontamento.toDTO(): ApontamentoVO {
    return ApontamentoVO(this.id, this.nome, this.inicio, this.fim, this.trabalho.id)
}