package com.nfrpaiva.taime.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nfrpaiva.taime.dominio.ApontamentoService
import com.nfrpaiva.taime.dto.toDTO
import com.nfrpaiva.taime.exception.TaimeException
import com.nfrpaiva.taime.test.MockBeans
import com.nfrpaiva.taime.test.apontamento
import com.nfrpaiva.taime.test.json
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

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [(ApontamentoController::class)])
@Import(MockBeans::class)
class ApontamentoControllerTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var apontamentoService: ApontamentoService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testInsertPontamento() {
        val apontamento =  apontamento()
        val dto = apontamento.toDTO()
        BDDMockito.`when`(apontamentoService.criarApontamento(dto.id, dto.nome, dto.inicio, dto.fim, dto.trabalhoID)).thenReturn(apontamento)

        mockMvc.perform(put("/apontamento").content(dto.json(objectMapper))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful)
    }

    @Test
    fun testInsertPontamentoSemTrabalho() {
        val dto = apontamento().toDTO()
        BDDMockito.`when`(apontamentoService.criarApontamento(dto.id, dto.nome, dto.inicio, dto.fim, dto.trabalhoID)).thenThrow(TaimeException("Trabalho não encontrado"))

        mockMvc.perform(put("/apontamento").content(dto.json(objectMapper))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError)
    }

}