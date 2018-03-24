package com.nfrpaiva.taime.controller

import com.nfrpaiva.taime.dominio.ApontamentoRepository
import com.nfrpaiva.taime.dominio.ApontamentoService
import com.nfrpaiva.taime.exception.TaimeException
import com.nfrpaiva.taime.vo.ApontamentoVO
import com.nfrpaiva.taime.vo.toDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("apontamento")
class ApontamentoController {

    @Autowired
    lateinit var apontamentoRepository: ApontamentoRepository

    @Autowired
    lateinit var apontamentoService: ApontamentoService

    @GetMapping
    fun findAll(): ResponseEntity<List<ApontamentoVO>> = apontamentoRepository.findAll().map { it.toDTO() }.ok()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApontamentoVO> = apontamentoRepository.findById(id).orElseThrow { TaimeException("Trabalho não econtrado") }.toDTO().ok()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): Unit = apontamentoRepository.deleteById(id)

    @PutMapping
    fun insert(@RequestBody apontamentoVO: ApontamentoVO): ResponseEntity<ApontamentoVO> = apontamentoService.criarApontamento(apontamentoVO.id, apontamentoVO.nome, apontamentoVO.inicio, apontamentoVO.fim, apontamentoVO.trabalhoID).toDTO().ok()
}

private fun <T> Any.ok(): ResponseEntity<T> {
    return ResponseEntity.ok(this as T)
}
