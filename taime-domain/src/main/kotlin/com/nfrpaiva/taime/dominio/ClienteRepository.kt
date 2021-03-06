package com.nfrpaiva.taime.dominio

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteRepository : JpaRepository<Cliente, Long> {
    fun findByNome(nome: String) : List<Cliente>
}