package com.nfrpaiva.taime.dominio.test.umparamuitos

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class Filha(@Id var id: Long, var nome: String, @ManyToOne var pai: Pai)