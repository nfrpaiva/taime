package com.nfrpaiva.taime.dominio

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class TrabalhoService(val clock: Clock) {

    @Autowired
    private lateinit var trabalhoRepository: TrabalhoRepository

    fun novoApontamento(trabalhoID: Long, inicio:LocalDateTime = LocalDateTime.now(clock), fim: LocalDateTime =  LocalDateTime.MIN): Apontamento {
        return Apontamento(inicio = inicio,
                fim = fim,
                descricao = "Um Apontamento",
                trabalho = trabalhoRepository.findById(trabalhoID).get())
    }
}