package com.nfrpaiva.taime.vo

import java.time.LocalDateTime

data class ApontamentoVO (val id: Long,
                          val nome: String,
                          val inicio: LocalDateTime,
                          val fim: LocalDateTime,
                          val trabalhoID: Long)
