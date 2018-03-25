package com.nfrpaiva.taime.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nfrpaiva.taime.dominio.ClienteRepository
import com.nfrpaiva.taime.dominio.TrabalhoCommand
import com.nfrpaiva.taime.dominio.TrabalhoRepository
import com.nfrpaiva.taime.dominio.TrabalhoService
import com.nfrpaiva.taime.test.MockBeans
import com.nfrpaiva.taime.test.json
import com.nfrpaiva.taime.test.trabalho
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [(TrabalhoController::class)])
@Import(MockBeans::class)
class TrabalhoControllerTest{

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var trabalhoService: TrabalhoService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun inserirTrabalho() {
        val trabalho = trabalho()
        BDDMockito.`when`(trabalhoService.criarTrabalho(TrabalhoCommand(trabalho.id, trabalho.nome, trabalho.cliente.id))).thenReturn(trabalho)
        mockMvc.perform(put("/trabalho")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(trabalho.json(objectMapper)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andDo(print())
    }
}
