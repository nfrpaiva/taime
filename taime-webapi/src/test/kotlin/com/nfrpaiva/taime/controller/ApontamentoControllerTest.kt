package com.nfrpaiva.taime.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nfrpaiva.taime.dominio.*
import com.nfrpaiva.taime.exception.TaimeException
import com.nfrpaiva.taime.test.MockBeans
import com.nfrpaiva.taime.vo.ApontamentoVO
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
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

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [(ApontamentoController::class)])
@Import(MockBeans::class)
class ApontamentoControllerTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var apontamentoService: ApontamentoService

    @MockBean
    private lateinit var apontamentoRepository: ApontamentoRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val trabalhoID = 1L;
    private val trabalho = Trabalho(trabalhoID, "", Cliente(1L, "", "Nilton"))

    @Test
    fun testInsertPontamento() {
        val dto = ApontamentoVO(0L, "Um Apontamento", LocalDateTime.now(), LocalDateTime.now().plusDays(1), trabalhoID)
        val apontamentoString = objectMapper.writeValueAsString(dto)
        val apontamento = Apontamento(dto.id, dto.nome, dto.inicio, dto.fim, trabalho)
        BDDMockito.`when`(apontamentoService.criarApontamento(dto.id, dto.nome, dto.inicio, dto.fim, dto.trabalhoID)).thenReturn(apontamento)

        mockMvc.perform(put("/apontamento").content(apontamentoString)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is2xxSuccessful)
    }

    @Test
    fun testInsertPontamentoSemTrabalho() {
        val dto = ApontamentoVO(0L, "Um Apontamento", LocalDateTime.now(), LocalDateTime.now().plusDays(1), trabalhoID)
        val apontamentoString = objectMapper.writeValueAsString(dto)
        BDDMockito.`when`(apontamentoService.criarApontamento(dto.id, dto.nome, dto.inicio, dto.fim, dto.trabalhoID)).thenThrow(TaimeException("Trabalho não encontrado"))

        mockMvc.perform(put("/apontamento").content(apontamentoString)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError)
    }

}