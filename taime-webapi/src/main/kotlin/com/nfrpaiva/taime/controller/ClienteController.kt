package com.nfrpaiva.taime.controller


import com.nfrpaiva.taime.dominio.ClienteRepository
import com.nfrpaiva.taime.infra.defaultOrNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("cliente")
class ClienteController {

    @Autowired
    lateinit var clienteRepository: ClienteRepository

    @GetMapping
    fun findAll() = ResponseEntity.ok(clienteRepository.findAll())

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = clienteRepository.findById(id).defaultOrNotFound()

}