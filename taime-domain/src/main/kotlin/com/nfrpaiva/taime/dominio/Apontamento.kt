package com.nfrpaiva.taime.dominio

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Apontamento(@Id
                       @GeneratedValue(strategy = GenerationType.AUTO)
                       var id: Long = 0,
                       var nome: String,
                       var inicio: LocalDateTime = LocalDateTime.now(),
                       var fim: LocalDateTime = LocalDateTime.MIN,
                       @ManyToOne
                       var trabalho: Trabalho
)
