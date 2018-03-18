package com.nfrpaiva.taime.dominio

import javax.persistence.*

@Entity
data class Trabalho(@Id
                    @GeneratedValue(strategy = GenerationType.AUTO)
                    var id: Long = 0,
                    var nome: String,
                    @ManyToOne(cascade = [(CascadeType.ALL)])
                    var cliente: Cliente)