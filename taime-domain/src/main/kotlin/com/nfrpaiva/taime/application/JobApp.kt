package com.nfrpaiva.taime.application

import com.nfrpaiva.taime.application.dto.ApontamentoDTO
import com.nfrpaiva.taime.application.dto.toDTO
import com.nfrpaiva.taime.application.dto.toDomain
import com.nfrpaiva.taime.dominio.ApontamentoRepository
import com.nfrpaiva.taime.dominio.TrabalhoRepository
import com.nfrpaiva.taime.exception.TaimeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class JobApp {

    @Autowired
    lateinit var apontamentoRepository: ApontamentoRepository

    @Autowired
    lateinit var trabalhoRepository: TrabalhoRepository

    fun todosOsApontamentos(): List<ApontamentoDTO> = apontamentoRepository.findAll().toDTO()
    @Throws(TaimeException::class)
    fun findApontamentoById(apontamentoID: Long): ApontamentoDTO = apontamentoRepository
            .findById(apontamentoID).orElseThrow { TaimeException("Apontamento não encontrado") }.toDTO()

    fun deleteApontamento(id: Long) = apontamentoRepository.deleteById(id)

    @Throws(TaimeException::class)
    fun inserirApontamento(apontamentoDTO: ApontamentoDTO) : ApontamentoDTO {
        return apontamentoRepository.save(
                apontamentoDTO.toDomain(trabalhoRepository.findById(apontamentoDTO.trabalhoID)
                        .orElseThrow { TaimeException("Trabalho não encontrado") })).toDTO()
    }

}