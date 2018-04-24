package com.nfrpaiva.taime

import com.nfrpaiva.taime.dominio.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class TaimeApplication {
    @Bean
    fun init(clienteRepository: ClienteRepository,
             trabalhoRepository: TrabalhoRepository,
             apontamentoRepository: ApontamentoRepository) = CommandLineRunner {
        val clientes = mutableListOf<Cliente>()
        for (i in 1..100){
            clientes.add(Cliente(nome="$i - Cliente"))
        }
        clienteRepository.saveAll(clientes)
        val trabalho: Trabalho? = trabalhoRepository.save(Trabalho(nome = "Um Trabalho", cliente = Cliente(nome = "Um clente")))
        if (trabalho != null) {
            apontamentoRepository.save(Apontamento(nome = "Um Apontamento", trabalho = trabalho))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<TaimeApplication>(*args)
}