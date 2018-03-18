package com.nfrpaiva.taime.dominio

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Trabalho(@Id
                    @GeneratedValue(strategy = GenerationType.AUTO)
                    var id: Long = 0,
                    var nome: String,
//                    @OneToMany(mappedBy = "trabalho", cascade = arrayOf(CascadeType.ALL))
//                    val apontamentos: MutableList<Apontamento> = mutableListOf(),
                    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
                    var cliente: Cliente) {

}