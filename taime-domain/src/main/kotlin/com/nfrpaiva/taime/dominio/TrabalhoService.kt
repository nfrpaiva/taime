package com.nfrpaiva.taime.dominio

import com.nfrpaiva.taime.exception.TaimeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.LocalDateTime

@Service
class TrabalhoService(val clock: Clock) {

    @Autowired
    private lateinit var trabalhoRepository: TrabalhoRepository

    @Autowired
    private lateinit var apontamentoRepository: ApontamentoRepository

    @Throws(TaimeException::class)
    fun novoApontamento(trabalhoID: Long,
                        descricao: String="Sem descrição",
                        inicio: LocalDateTime = LocalDateTime.now(clock),
                        fim: LocalDateTime = LocalDateTime.MIN): Apontamento {
        val apontamento = Apontamento(inicio = inicio,
                fim = fim,
                descricao = descricao,
                trabalho = trabalhoRepository.findById(trabalhoID)
                        .orElseThrow { TaimeException("Trabalho não Encontrado") })
        return apontamentoRepository.save(apontamento)
    }
}
