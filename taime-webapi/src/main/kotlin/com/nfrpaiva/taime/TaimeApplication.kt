package com.nfrpaiva.taime

import com.nfrpaiva.taime.dominio.Cliente
import com.nfrpaiva.taime.dominio.ClienteRepository
import com.nfrpaiva.taime.dominio.Trabalho
import com.nfrpaiva.taime.dominio.TrabalhoRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class TaimeApplication{
    @Bean
    fun init (clienteRepository: ClienteRepository,
              trabalhoRepository: TrabalhoRepository) = CommandLineRunner {
        clienteRepository.saveAll( mutableListOf(Cliente(nome="Nilton Paiva"),
                Cliente(nome="Barbara Carolina")))
        trabalhoRepository.save(Trabalho(nome = "Um Trabalho", cliente = Cliente(nome="Um clente")))
    }
}

fun main(args: Array<String>) {
    runApplication<TaimeApplication>(*args)
}