package com.nfrpaiva.taime

import com.nfrpaiva.taime.dominio.Cliente
import com.nfrpaiva.taime.dominio.ClienteRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class TaimeApplication{
    @Bean
    fun init (clienteRepository: ClienteRepository) = CommandLineRunner {
        clienteRepository.saveAll( mutableListOf(Cliente(nome="Nilton Paiva"),
                Cliente(nome="Barbara Carolina")))
    }
}

fun main(args: Array<String>) {
    runApplication<TaimeApplication>(*args)
}