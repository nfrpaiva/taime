package com.nfrpaiva.taime.controller

import com.nfrpaiva.taime.application.JobApp
import com.nfrpaiva.taime.application.dto.toDTO
import com.nfrpaiva.taime.dominio.ApontamentoRepository
import com.nfrpaiva.taime.dominio.TrabalhoRepository
import com.nfrpaiva.taime.dto.ApontamentoVO
import com.nfrpaiva.taime.exception.TaimeException
import com.nfrpaiva.taime.infra.defaultOrNotFound
import com.nfrpaiva.taime.infra.responseOK
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("apontamento")
class ApontamentoController {

    @Autowired
    lateinit var jobApp: JobApp

    @Autowired
    lateinit var trabalhoRepository: TrabalhoRepository

    @GetMapping
    fun findAll(): ResponseEntity<List<ApontamentoVO>> = jobApp.todosOsApontamentos().map {ApontamentoVO(it.id, it.nome,it.inicio,it.fim,it.trabalhoID)}.responseOK()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApontamentoVO> = jobApp.findApontamentoById (id)
            apontamentoRepository.findById(id).toDTO().defaultOrNotFound()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): Unit = apontamentoRepository.deleteById(id)

//    @PutMapping
//    fun insert(@RequestBody apontamentoDTO: ApontamentoVO): ResponseEntity<ApontamentoVO> {
//        val trabalho = trabalhoRepository.findById(apontamentoDTO.trabalhoID)
//                .orElseThrow { TaimeException("Trabalho não Encontrado") }
//        return ResponseEntity.ok(apontamentoRepository.save(apontamentoDTO.toEntity(trabalho)).toDTO())
//    }
}
