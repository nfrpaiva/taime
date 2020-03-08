package com.nfrpaiva.taime.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nfrpaiva.taime.dominio.Cliente
import com.nfrpaiva.taime.dominio.ClienteRepository
import com.nfrpaiva.taime.dto.toDTO
import com.nfrpaiva.taime.test.MockBeans
import com.nfrpaiva.taime.test.cliente
import com.nfrpaiva.taime.test.json
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(controllers = [(ClienteController::class)])
@Import(MockBeans::class)
class ClienteControllerTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var clienteRepository: ClienteRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `retornar uma lista de cliente`() {

        val clientes = listOf(Cliente(1, "Um Cliente"))
        BDDMockito.`when`(clienteRepository.findAll()).thenReturn(clientes)

        val result = mockMvc.perform(get("/cliente"))
                .andExpect(status().isOk)
                .andDo(print())
                .andReturn()
        Assertions.assertThat(result.response.contentAsString).isEqualTo(objectMapper.writeValueAsString(clientes))
    }

    @Test
    fun `inserir um cliente`() {
        val cliente = cliente()
        BDDMockito.`when`(clienteRepository.save(cliente)).thenReturn(cliente)
        val result = mockMvc.perform(put("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(cliente.toDTO().json(objectMapper)))
                .andExpect(status().is2xxSuccessful)
                .andDo(print())
                .andReturn().response.contentAsString
        Assertions.assertThat(cliente.toDTO().json(objectMapper)).isEqualTo(result)
    }

}