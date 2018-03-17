package com.nfrpaiva.taime.dominio

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Apontamento(@Id
                       @GeneratedValue(strategy = GenerationType.AUTO)
                       var id: Long = 0,
                       var descricao: String,
                       var inicio: LocalDateTime = LocalDateTime.now(),
                       var fim: LocalDateTime = LocalDateTime.MIN,
                       @ManyToOne
                       var trabalho: Trabalho
) {

    override fun toString(): String {
        return "Apontamento(id=$id, descricao='$descricao', inicio=$inicio, fim=$fim, trabalho=(id = ${trabalho.id}, descricao='${trabalho.descricao}'))"
    }
}

