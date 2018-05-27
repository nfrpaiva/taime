package com.nfrpaiva.taime.dominio.test

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CursoRepository : JpaRepository<Curso, Long>