package com.nfrpaiva.taime

import com.nfrpaiva.taime.dominio.ClienteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@SpringBootApplication
class TaimeApplication {

}

fun main(args: Array<String>) {
    runApplication<TaimeApplication>(*args)
}


@RestController
@RequestMapping("cliente")
class ClienteController {

    @Autowired
    lateinit var clienteRepository: ClienteRepository

    @GetMapping
    fun findAll() = ResponseEntity.ok( clienteRepository.findAll() )

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long) = clienteRepository.findById(id).defaultOrNotFound()

}

fun <T> Optional<T>.defaultOrNotFound(): ResponseEntity<T> {
    return if (this.isPresent) ResponseEntity.ok(this.get()) else ResponseEntity.notFound().build<T>()
}
