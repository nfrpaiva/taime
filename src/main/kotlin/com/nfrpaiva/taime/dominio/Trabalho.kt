package com.nfrpaiva.taime.dominio

import javax.persistence.*

@Entity
data class Trabalho(@Id
                    @GeneratedValue(strategy = GenerationType.AUTO)
                    var id: Long = 0,
                    var descricao: String,
                    @OneToMany(mappedBy = "trabalho", cascade = arrayOf(CascadeType.ALL))
                    val apontamentos: MutableList<Apontamento> = mutableListOf(),
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