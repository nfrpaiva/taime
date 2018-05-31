package com.nfrpaiva.taime.dominio.test.umparamuitos

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Pai(@Id var id: Long, var nome: String) {
    @OneToMany(mappedBy = "pai", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    var filhos: MutableSet<Filho> = mutableSetOf()
    @OneToMany(mappedBy = "pai", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    var filhas: MutableSet<Filha> = mutableSetOf()

}