package com.nfrpaiva.taime.dto

import java.time.LocalDateTime

data class ApontamentoDTO (val id: Long,
                           val nome: String,
                           val inicio: LocalDateTime,
                           val fim: LocalDateTime,
                           val trabalhoID: Long)
