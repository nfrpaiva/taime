package com.nfrpaiva.taime

import com.nfrpaiva.taime.dominio.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class TaimeApplication{
    @Bean
    fun init (clienteRepository: ClienteRepository,
              trabalhoRepository: TrabalhoRepository,
              apontamentoRepository: ApontamentoRepository) = CommandLineRunner {
        clienteRepository.saveAll( mutableListOf(Cliente(nome="Nilton Paiva"),
                Cliente(nome="Barbara Carolina")))
        val trabalho = trabalhoRepository.save(Trabalho(nome = "Um Trabalho", cliente = Cliente(nome="Um clente")))
        if (trabalho != null){
            val apontamento = apontamentoRepository.save(Apontamento(descricao = "Um Apontamento", trabalho = trabalho))
        }
    }
}

fun main(args: Array<String>) {
    runApplication<TaimeApplication>(*args)
}