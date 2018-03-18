package com.nfrpaiva.taime.controller

import com.nfrpaiva.taime.dominio.ApontamentoRepository
import com.nfrpaiva.taime.dominio.TrabalhoRepository
import com.nfrpaiva.taime.dto.ApontamentoDTO
import com.nfrpaiva.taime.exception.TaimeException
import com.nfrpaiva.taime.infra.defaultOrNotFound
import com.nfrpaiva.taime.infra.responseOK
import com.nfrpaiva.taime.infra.toDTO
import com.nfrpaiva.taime.infra.toEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("apontamento")
class ApontamentoController {

    @Autowired
    lateinit var apontamentoRepository: ApontamentoRepository

    @Autowired
    lateinit var trabalhoRepository: TrabalhoRepository

    @GetMapping
    fun findAll(): ResponseEntity<List<ApontamentoDTO>> = apontamentoRepository.findAll().toDTO().responseOK()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApontamentoDTO> = apontamentoRepository
            .findById(id).toDTO().defaultOrNotFound()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): Unit = apontamentoRepository.deleteById(id)

    @PutMapping
    fun insert(@RequestBody apontamentoDTO: ApontamentoDTO): ResponseEntity<ApontamentoDTO> {
        val trabalho = trabalhoRepository.findById(apontamentoDTO.trabalhoID)
                .orElseThrow { TaimeException("Trabalho não Encontrado") }
        return ResponseEntity.ok(apontamentoRepository.save(apontamentoDTO.toEntity(trabalho)).toDTO())
    }
}
