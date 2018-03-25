package com.nfrpaiva.taime.controller

import com.nfrpaiva.taime.dominio.ApontamentoRepository
import com.nfrpaiva.taime.dominio.ApontamentoService
import com.nfrpaiva.taime.exception.TaimeException
import com.nfrpaiva.taime.vo.ApontamentoVO
import com.nfrpaiva.taime.vo.toDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("apontamento")
class ApontamentoController {

    @Autowired
    lateinit var apontamentoRepository: ApontamentoRepository

    @Autowired
    lateinit var apontamentoService: ApontamentoService

    @GetMapping
    fun findAll(): ResponseEntity<List<ApontamentoVO>> = ok(apontamentoRepository.findAll().map { it.toDTO() })

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApontamentoVO> = ok(apontamentoRepository.findById(id).orElseThrow { TaimeException("Trabalho não econtrado") }.toDTO())

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): Unit = apontamentoRepository.deleteById(id)

    @PutMapping
    fun insert(@RequestBody apontamentoVO: ApontamentoVO): ResponseEntity<ApontamentoVO> = ok(apontamentoService.criarApontamento(apontamentoVO.id, apontamentoVO.nome, apontamentoVO.inicio, apontamentoVO.fim, apontamentoVO.trabalhoID).toDTO())
}
