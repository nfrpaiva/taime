package com.nfrpaiva.taime.t1

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PinoRepository : JpaRepository<Pai, Long>