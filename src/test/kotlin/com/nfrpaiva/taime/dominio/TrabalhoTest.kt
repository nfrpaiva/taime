package com.nfrpaiva.taime.dominio

import org.assertj.core.api.Assertions.*
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.theories.suppliers.TestedOn
import java.time.LocalDateTime

class TrabalhoTest {

    @Test
    fun `criar um novo apontamento comecando agora`() {
        val trabalho = Trabalho(cliente = Cliente(nome = "Um Cliente"), id = -1, descricao = "O Trabalho")
        val apontamento = Apontamento(descricao = "Um novo apontamento", trabalho = trabalho)
        trabalho.add(apontamento)
        assertThat(apontamento.trabalho).isEqualTo(trabalho)
        assertThat(apontamento.fim).isEqualTo(LocalDateTime.MIN)
        assertThat(trabalho.apontamentos).hasSize(1)
    }

    @Test
    fun `falar ao adicionar um apontamento a um trabalho que ele nao percente`() {
        val trabalho1 = Trabalho(cliente = Cliente(nome = "Um Cliente"), id = -1, descricao = "Trabalho 1")
        val trabalho2 = Trabalho(cliente = Cliente(nome = "Um Cliente"), id = -2, descricao = "Trabalho 2")

        val apontamento2 = Apontamento(descricao = "Um novo apontamento", trabalho = trabalho2)

        try {
            trabalho1.add(apontamento2)
            Assert.fail("Não deve ser possível adicionar um apontamento que não pertence ao trabalho")
        } catch (e: Exception) {

        }
    }

}