package com.nfrpaiva.taime.dominio

import java.time.LocalDate
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

)

@Entity
data class Trabalho(@Id
                    @GeneratedValue(strategy = GenerationType.AUTO)
                    var id: Long = 0,
                    var descricao: String = "",
                    @OneToMany(mappedBy = "trabalho")
                    var apontamentos: MutableList<Apontamento> = mutableListOf<Apontamento>(),
                    @ManyToOne
                    var cliente: Cliente) {
    fun novoApontamento(inicio: LocalDateTime = LocalDateTime.now(), fim: LocalDateTime = LocalDateTime.MIN, descricao: String = ""): Apontamento {
        val apontamento = Apontamento(inicio = inicio, fim = fim, descricao = descricao, trabalho = this)
        this.apontamentos.add(apontamento)
        return apontamento
    }
}

@Entity
data class Cliente(@Id
                   @GeneratedValue(strategy = GenerationType.AUTO)
                   var id: Long = 0,
                   var nome: String? = null,
                   @OneToMany(mappedBy = "cliente")
                   var trabalhos: MutableList<Trabalho> = mutableListOf<Trabalho>()
)
