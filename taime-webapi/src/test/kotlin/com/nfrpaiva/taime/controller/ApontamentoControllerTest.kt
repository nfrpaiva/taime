package com.nfrpaiva.taime.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.nfrpaiva.taime.dominio.ApontamentoRepository
import com.nfrpaiva.taime.dominio.Cliente
import com.nfrpaiva.taime.dominio.Trabalho
import com.nfrpaiva.taime.dominio.TrabalhoRepository
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.util.*
import java.util.Optional.*

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

    @Test
    fun testInsertPontamento() {
        val trabalhoID = 1L;
        val apontamento = objectMapper.writeValueAsString(ApontamentoDTO(0L, "Um Apontamento",
                LocalDateTime.now(), LocalDateTime.now().plusDays(1), trabalhoID))
        println(apontamento)
        BDDMockito.`when`(trabalhoRepository.findById(trabalhoID))
                .thenReturn(of(Trabalho(trabalhoID,"", Cliente(1L,"","Nilton"))))

        mockMvc.perform(put("/apontamento").content(apontamento)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is2xxSuccessful)
    }

}