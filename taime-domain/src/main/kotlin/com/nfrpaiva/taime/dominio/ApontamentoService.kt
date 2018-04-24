package com.nfrpaiva.taime.dominio

import com.nfrpaiva.taime.exception.TaimeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ApontamentoService {

    @Autowired
    lateinit var trabalhoRepository: TrabalhoRepository

    @Autowired
    lateinit var apontamentoRepository: ApontamentoRepository

    @Throws(TaimeException::class)
    fun criarApontamento(id: Long, nome: String, inicio: LocalDateTime, fim: LocalDateTime, trabalhoID: Long): Apontamento {
        return apontamentoRepository.save(Apontamento(id, nome, inicio, fim,
                trabalhoRepository.findById(trabalhoID).orElseThrow { TaimeException("Trabalho não econtrado") }))
    }

}
