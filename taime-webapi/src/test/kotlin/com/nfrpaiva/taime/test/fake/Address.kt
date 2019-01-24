package com.nfrpaiva.taime.test.fake

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Address(
        @Id
        @GeneratedValue
        val id: Long,
        var street: String,
        var city: String,
        var suite: String,
        var zipcode: String
)