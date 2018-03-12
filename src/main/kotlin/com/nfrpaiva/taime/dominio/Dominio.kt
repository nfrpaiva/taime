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
) {

    override fun toString(): String {
        return "Apontamento(id=$id, descricao='$descricao', inicio=$inicio, fim=$fim, trabalho=(id = ${trabalho.id}, descricao='${trabalho.descricao}'))"
    }
}

@Entity
data class Trabalho(@Id
                    @GeneratedValue(strategy = GenerationType.AUTO)
                    var id: Long = 0,
                    var descricao: String,
                    @OneToMany(mappedBy = "trabalho", cascade = arrayOf(CascadeType.ALL))
                    val apontamentos: MutableList<Apontamento> = mutableListOf<Apontamento>(),
                    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
                    var cliente: Cliente) {


    fun add(apontamento: Apontamento) {
        if (apontamento.trabalho == this) apontamentos.add(apontamento)
        else throw Exception("Esse apontamento não pertence a esse trabalho")
    }

    override fun toString(): String {
        return "Trabalho(id=$id, descricao='$descricao', apontamentos=$apontamentos, cliente=$cliente)"
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
