package com.nfrpaiva.taime.t1

import javax.persistence.*

@Entity
data class Filho(@Id @Column(name = "filho_id")
                 var id: Long,
                 var nome: String) {
    @ManyToOne
    @JoinColumn(name = "pai_id")
    var pai: Pai? = null
}