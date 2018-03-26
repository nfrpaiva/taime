package com.nfrpaiva.taime.controller


import com.nfrpaiva.taime.dominio.Cliente
import com.nfrpaiva.taime.dominio.ClienteRepository
import com.nfrpaiva.taime.dto.ClienteDTO
import com.nfrpaiva.taime.dto.toDTO
import com.nfrpaiva.taime.infra.defaultOrNotFound
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("cliente")
class ClienteController {

    @Autowired
    lateinit var clienteRepository: ClienteRepository

    @GetMapping
    fun findAll() = ResponseEntity.ok(clienteRepository.findAll())

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = clienteRepository.findById(id).defaultOrNotFound()

    @PutMapping
    fun insert(@RequestBody dto: ClienteDTO): ResponseEntity<ClienteDTO> = ResponseEntity.ok(clienteRepository.save(Cliente(dto.id, dto.nome)).toDTO())

}