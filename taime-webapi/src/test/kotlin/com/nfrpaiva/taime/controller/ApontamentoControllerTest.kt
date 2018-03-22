package com.nfrpaiva.taime.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nfrpaiva.taime.dominio.*
import com.nfrpaiva.taime.dto.ApontamentoDTO
import com.nfrpaiva.taime.test.MockBeans
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.*

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [(ApontamentoController::class)])
@Import(MockBeans::class)
class ApontamentoControllerTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var apontamentoRepository: ApontamentoRepository

    @MockBean
    private lateinit var trabalhoRepository: TrabalhoRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val trabalhoID = 1L;
    private val trabalho = Trabalho(trabalhoID, "", Cliente(1L, "", "Nilton"))

    @Test
    fun testInsertPontamento() {
        val dto = ApontamentoDTO(0L, "Um Apontamento", LocalDateTime.now(), LocalDateTime.now().plusDays(1), trabalhoID)
        val apontamento = Apontamento(1L, dto.nome, dto.inicio, dto.fim, trabalho)

        val apontamentoString = objectMapper.writeValueAsString(dto)
        BDDMockito.`when`(trabalhoRepository.findById(trabalhoID)).thenReturn(Optional.of(trabalho))
        BDDMockito.`when`(apontamentoRepository.save(dto.toEntity(trabalho))).thenReturn(dto.toEntity(trabalho))

        mockMvc.perform(put("/apontamento").content(apontamentoString)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is2xxSuccessful)
    }

    @Test
    fun testInsertPontamentoSemTrabalho() {
        val dto = ApontamentoDTO(0L, "Um Apontamento", LocalDateTime.now(), LocalDateTime.now().plusDays(1), trabalhoID)
        val apontamento = Apontamento(1L, dto.nome, dto.inicio, dto.fim, trabalho)

        val apontamentoString = objectMapper.writeValueAsString(dto)
        BDDMockito.`when`(trabalhoRepository.findById(trabalhoID)).thenReturn(Optional.empty())

        mockMvc.perform(put("/apontamento").content(apontamentoString)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError)
    }

}