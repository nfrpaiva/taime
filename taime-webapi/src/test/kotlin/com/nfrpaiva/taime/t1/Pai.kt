package com.nfrpaiva.taime.t1

import javax.persistence.*

@Entity
data class Pai(@Id @Column(name = "pai_id")
               var id: Long,
               var nome: String) {
    @OneToMany(mappedBy = "pai", cascade = [CascadeType.ALL])
    var filhos: List<Filho> = mutableListOf()
}