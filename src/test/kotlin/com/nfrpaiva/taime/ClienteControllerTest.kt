package com.nfrpaiva.taime

import com.fasterxml.jackson.databind.ObjectMapper
import com.nfrpaiva.taime.dominio.Cliente
import com.nfrpaiva.taime.dominio.ClienteRepository
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner::class)
@WebMvcTest(controllers =  arrayOf(ClienteController::class))
class ClienteControllerTest {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var clienteRepository: ClienteRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `retornar uma lista de cliente` (){

        val clientes =  listOf(Cliente(1,"Um Cliente"))
        BDDMockito.`when`(clienteRepository.findAll()).thenReturn(clientes)

        val result = mockMvc.perform(get("/cliente"))
                .andExpect(status().isOk)
                .andDo(print())
                .andReturn();
        Assertions.assertThat(result.response.contentAsString).isEqualTo(objectMapper.writeValueAsString(clientes))
    }

}