package com.nfrpaiva.taime.test.fake

import javax.persistence.*

@Entity
data class User(
        @Id
        var id: Int? = null,
        var name: String? = null,
        var email: String? = null,
        var phone: String? = null,
        @ManyToOne(cascade = [CascadeType.ALL])
        var address: Address? = null
)