package com.nfrpaiva.taime.application

import com.nfrpaiva.taime.application.dto.ApontamentoDTO
import com.nfrpaiva.taime.application.dto.toDTO
import com.nfrpaiva.taime.dominio.ApontamentoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
sealed class JobApp {

    @Autowired
    lateinit var apontamentoRepository: ApontamentoRepository

    fun todosOsApontamentos () :List<ApontamentoDTO>  = apontamentoRepository.findAll().toDTO()

}


