package com.nfrpaiva.taime.dominio

import com.nfrpaiva.taime.exception.TaimeException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class TrabalhoServiceTest {

    val now = LocalDateTime.of(2018, 1, 1, 0, 0)

    private var clock: Clock = Clock.fixed(now.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault())

    @InjectMocks
    private val trabalhoService: TrabalhoService = TrabalhoService(clock)

    @Mock
    private lateinit var trabalhoRepository: TrabalhoRepository

    @Test
    fun `checar se as configurações de mock estão ok`() {
        assertThat(trabalhoService).isNotNull
        assertThat(clock).isNotNull
    }

    @Test
    fun `criar um novo Apontamento para um trabalho já existente sem informar a hora de inicio ou fim`() {

        val trabalhoID = 1L;
        val trabalho = Trabalho(descricao = "Um Trabalho", cliente = Cliente(nome = "Um Cliente"))

        BDDMockito.`when`(trabalhoRepository.findById(trabalhoID)).thenReturn(Optional.of(trabalho));
        var apontamento: Apontamento = trabalhoService.novoApontamento(trabalhoID)

        assertThat(apontamento.inicio).isEqualTo(now)
        assertThat(apontamento.fim).isEqualTo(LocalDateTime.MIN)
    }

    @Test
    fun `criar um novo Apontamento para um trabalho já existente informando inicio e fim`() {

        val trabalhoID = 1L;
        val trabalho = Trabalho(descricao = "Um Trabalho", cliente = Cliente(nome = "Um Cliente"))

        BDDMockito.`when`(trabalhoRepository.findById(trabalhoID)).thenReturn(Optional.of(trabalho));
        var apontamento: Apontamento = trabalhoService.novoApontamento(trabalhoID, now.plusHours(1), now.plusHours(4))

        assertThat(apontamento.inicio).isEqualTo(LocalDateTime.of(2018, 1, 1, 1, 0))
        assertThat(apontamento.fim).isEqualTo(LocalDateTime.of(2018, 1, 1, 4, 0))
    }

    @Test
    fun `criar um Apontamento mas não encontrar o trabalho`() {
        val trabalhoID = 1L;
        BDDMockito.`when`(trabalhoRepository.findById(1)).thenReturn(Optional.empty<Trabalho>())
        try{
            var apontamento: Apontamento = trabalhoService.novoApontamento(trabalhoID, now.plusHours(1), now.plusHours(4))
            fail("O trabalho não deveria ser sido encontrado")
        }catch (e: TaimeException){
            assertThat(e.message).isEqualTo("Trabalho não Encontrado")
        }catch (e: Exception){
            fail("A Exception TaimeException deveria ter sido lançada")
        }
    }
}