package com.nfrpaiva.taime.dominio

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ApontamentoServiceTest {

    @InjectMocks
    private val service : ApontamentoService =  ApontamentoService()

    @Mock
    private  lateinit var apontamentoRepository: ApontamentoRepository

    @Mock
    private lateinit var trabalhoRepository: TrabalhoRepository

    @Test
    fun criarApontamento() {
        val id = 1L
        val nome = "Um Nome"
        val inicio  = LocalDateTime.now()
        val fim = LocalDateTime.now()
        val trabalhoID = 3L
        val trabalho = Trabalho(trabalhoID, "Um trabalho", Cliente(1L, "Nome do cliente"))

        val apontamento: Apontamento =  Apontamento(id, nome, inicio, fim, trabalho)
        BDDMockito.`when`(trabalhoRepository.findById(trabalhoID)).thenReturn(Optional.of(trabalho))
        BDDMockito.`when`(apontamentoRepository.save(apontamento)).thenReturn(apontamento)

        val result =  service.criarApontamento(id, nome, inicio, fim, trabalhoID)

        BDDMockito.verify(trabalhoRepository).findById(Mockito.anyLong())
        BDDMockito.verify(apontamentoRepository).save(apontamento)

    }
}