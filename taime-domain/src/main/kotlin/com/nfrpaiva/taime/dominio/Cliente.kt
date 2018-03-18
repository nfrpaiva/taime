package com.nfrpaiva.taime.dominio

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Cliente(@Id
                   @GeneratedValue(strategy = GenerationType.AUTO)
                   var id: Long = 0,
                   var nome: String,
                   override val userID: String = "Nilton"
) : OwnerAtributesEntity(userID)