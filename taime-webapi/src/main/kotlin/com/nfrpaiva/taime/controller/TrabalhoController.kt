package com.nfrpaiva.taime.controller

import com.nfrpaiva.taime.dominio.TrabalhoCommand
import com.nfrpaiva.taime.dominio.TrabalhoRepository
import com.nfrpaiva.taime.dominio.TrabalhoService
import com.nfrpaiva.taime.dto.TrabalhoDTO
import com.nfrpaiva.taime.dto.toDTO
import com.nfrpaiva.taime.infra.defaultOrNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("trabalho")
class TrabalhoController{

    @Autowired
    lateinit var trabalhoRepository: TrabalhoRepository

    @Autowired
    lateinit var trabalhoService: TrabalhoService

    @GetMapping
    fun findAll (@RequestParam(required = false) nome:String?) = ok( if (nome == null) trabalhoRepository.findAll() else  trabalhoRepository.findByNomeContainingIgnoreCase(nome))

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = trabalhoRepository.findById(id).defaultOrNotFound()

    @PutMapping
    fun insert (@RequestBody dto: TrabalhoDTO) : ResponseEntity<TrabalhoDTO> =  ok(trabalhoService.criarTrabalho(TrabalhoCommand(dto.nome, dto.clienteID)).toDTO())

}