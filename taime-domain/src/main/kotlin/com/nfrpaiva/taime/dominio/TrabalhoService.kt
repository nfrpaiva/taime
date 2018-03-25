package com.nfrpaiva.taime.dominio

import com.nfrpaiva.taime.exception.TaimeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TrabalhoService {

    @Autowired
    lateinit var clienteRepository: ClienteRepository

    @Autowired
    lateinit var trabalhoRepository: TrabalhoRepository

    @Throws(TaimeException::class)
    fun criarTrabalho(command: TrabalhoCommand): Trabalho {
        val trabalho = Trabalho(-1, command.nome,
                clienteRepository.findById(command.clienteID).orElseThrow { TaimeException("Cliente não encontrado") })
        return trabalhoRepository.save(trabalho)
    }
}