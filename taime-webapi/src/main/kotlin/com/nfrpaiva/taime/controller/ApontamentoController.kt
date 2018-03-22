package com.nfrpaiva.taime.controller

import com.nfrpaiva.taime.application.JobApp
import com.nfrpaiva.taime.application.dto.ApontamentoDTO
import com.nfrpaiva.taime.vo.ApontamentoVO
import com.nfrpaiva.taime.infra.responseOK
import com.nfrpaiva.taime.vo.toDTO
import com.nfrpaiva.taime.vo.toVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("apontamento")
class ApontamentoController {

    @Autowired
    lateinit var jobApp: JobApp

    @GetMapping
    fun findAll(): ResponseEntity<List<ApontamentoVO>> = jobApp.todosOsApontamentos().map { it.toVO() }.responseOK()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApontamentoVO> = ResponseEntity.ok(jobApp.findApontamentoById(id).toVO())

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): Unit = jobApp.deleteApontamento(id)

    @PutMapping
    fun insert(@RequestBody apontamentoVO: ApontamentoVO): ResponseEntity<ApontamentoVO> = ResponseEntity.ok(jobApp.inserirApontamento(apontamentoVO.toDTO()).toVO())
}