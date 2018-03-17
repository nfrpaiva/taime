package com.nfrpaiva.taime.dominio

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface TrabalhoRepository : JpaRepository<Trabalho, Long>