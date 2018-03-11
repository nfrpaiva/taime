
package com.nfrpaiva.taime.dominio

import org.assertj.core.api.Assertions.*
import org.junit.Test
import java.time.LocalDateTime

class TrabalhoTest {

    @Test
    fun `criar um novo apontamento comecando agora` (){
        val trabalho = Trabalho(cliente = Cliente(nome="Um Cliente"), id = -1)
        val apontamento: Apontamento  = trabalho.novoApontamento(descricao = "Um novo apontamento")
        assertThat(apontamento.trabalho).isEqualTo(trabalho)
        assertThat(apontamento.fim).isEqualTo(LocalDateTime.MIN)
    }

}