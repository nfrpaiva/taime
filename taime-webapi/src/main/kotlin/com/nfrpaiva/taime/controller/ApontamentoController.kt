package com.nfrpaiva.taime.controller

import com.nfrpaiva.taime.dominio.ApontamentoRepository
import com.nfrpaiva.taime.dominio.ApontamentoService
import com.nfrpaiva.taime.dto.ApontamentoDTO
import com.nfrpaiva.taime.dto.toDTO
import com.nfrpaiva.taime.exception.TaimeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("apontamento")
class ApontamentoController {

    @Autowired
    lateinit var apontamentoRepository: ApontamentoRepository

    @Autowired
    lateinit var apontamentoService: ApontamentoService

    @GetMapping
    fun findAll(): ResponseEntity<List<ApontamentoDTO>> = ok(apontamentoRepository.findAll().map { it.toDTO() })

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApontamentoDTO> = ok(apontamentoRepository.findById(id).orElseThrow { TaimeException("Trabalho não econtrado") }.toDTO())

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): Unit = apontamentoRepository.deleteById(id)

    @PutMapping
    fun insert(@RequestBody apontamentoDTO: ApontamentoDTO): ResponseEntity<ApontamentoDTO> = ok(apontamentoService.criarApontamento(apontamentoDTO.id, apontamentoDTO.nome, apontamentoDTO.inicio, apontamentoDTO.fim, apontamentoDTO.trabalhoID).toDTO())
}
