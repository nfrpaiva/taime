package com.nfrpaiva.taime.controller

import com.nfrpaiva.taime.dominio.TrabalhoRepository
import com.nfrpaiva.taime.infra.defaultOrNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("trabalho")
class TrabalhoController{

    @Autowired
    lateinit var trabalhoRepository: TrabalhoRepository

    @GetMapping
    fun findAll (@RequestParam(required = false) nome:String?) = ResponseEntity.ok( if (nome == null) trabalhoRepository.findAll() else  trabalhoRepository.findByNomeContainingIgnoreCase(nome))

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = trabalhoRepository.findById(id).defaultOrNotFound()

}