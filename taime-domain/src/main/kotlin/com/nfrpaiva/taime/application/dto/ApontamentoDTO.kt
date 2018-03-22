package com.nfrpaiva.taime.application.dto

import java.time.LocalDateTime

class ApontamentoDTO (val id: Long,
                      val nome: String,
                      val inicio: LocalDateTime,
                      val fim: LocalDateTime,
                      val trabalhoID: Long)