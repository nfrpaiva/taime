package com.nfrpaiva.taime.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nfrpaiva.taime.dominio.TrabalhoCommand
import com.nfrpaiva.taime.dominio.TrabalhoService
import com.nfrpaiva.taime.dto.toDTO
import com.nfrpaiva.taime.exception.TaimeException
import com.nfrpaiva.taime.test.MockBeans
import com.nfrpaiva.taime.test.json
import com.nfrpaiva.taime.test.trabalho
import org.assertj.core.api.Assertions
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [(TrabalhoController::class)])
@Import(MockBeans::class)
class TrabalhoControllerTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var trabalhoService: TrabalhoService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun inserirTrabalho() {
        val trabalho = trabalho()
        BDDMockito.`when`(trabalhoService.criarTrabalho(TrabalhoCommand(trabalho.nome, trabalho.cliente.id))).thenReturn(trabalho)
        val result = mockMvc.perform(put("/trabalho")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(trabalho.toDTO().json(objectMapper)))
                .andExpect(status().is2xxSuccessful)
                .andDo(print()).andReturn().response.contentAsString
        Assertions.assertThat(trabalho.toDTO().json(objectMapper)).isEqualTo(result)
    }

    @Test
    fun `inserir trabalho e falar porque não encontrou o cliente`() {
        val trabalho = trabalho()
        BDDMockito.`when`(trabalhoService.criarTrabalho(TrabalhoCommand(trabalho.nome, trabalho.cliente.id))).thenThrow(TaimeException(""))
        mockMvc.perform(put("/trabalho")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError)
                .andDo(print())

    }
}
