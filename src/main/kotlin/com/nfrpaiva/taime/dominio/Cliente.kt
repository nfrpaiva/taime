package com.nfrpaiva.taime.dominio

import javax.persistence.*

@Entity
data class Cliente(@Id
                   @GeneratedValue(strategy = GenerationType.AUTO)
                   var id: Long = 0,
                   var nome: String,
                   @OneToMany(mappedBy = "cliente")
                   var trabalhos: MutableList<Trabalho> = mutableListOf()
)