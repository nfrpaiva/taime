package com.nfrpaiva.taime.controller

import com.nfrpaiva.taime.dominio.ApontamentoRepository
import com.nfrpaiva.taime.dto.ApontamentoDTO
import com.nfrpaiva.taime.infra.defaultOrNotFound
import com.nfrpaiva.taime.infra.responseOK
import com.nfrpaiva.taime.infra.toDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("apontamento")
class ApontamentoController {

    @Autowired
    lateinit var apontamentoRepository: ApontamentoRepository

    @GetMapping
    fun findAll(): ResponseEntity<List<ApontamentoDTO>> = apontamentoRepository.findAll().toDTO().responseOK()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApontamentoDTO> = apontamentoRepository.findById(id).toDTO().defaultOrNotFound()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long):Unit =  apontamentoRepository.deleteById(id)

}





