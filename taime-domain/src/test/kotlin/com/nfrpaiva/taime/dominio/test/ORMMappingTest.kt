package com.nfrpaiva.taime.dominio.test

import com.nfrpaiva.taime.App
import com.nfrpaiva.taime.dominio.Cliente
import com.nfrpaiva.taime.dominio.ClienteRepository
import com.nfrpaiva.taime.dominio.Trabalho
import com.nfrpaiva.taime.dominio.TrabalhoRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManager

@RunWith(SpringRunner::class)
@DataJpaTest
class ORMMappingTest {

    @Autowired
    lateinit var em: EntityManager

    @Autowired
    lateinit var clienteRepository: ClienteRepository

    @Autowired
    lateinit var trabalhoRepository: TrabalhoRepository

    @Test
    fun `todas as entidas devem estar com os relacionamentos correto`() {
        assertThat(em).isNotNull
    }

    @Test
    fun `deveria conseguir salvar um cliente sem trabalho`() {
        val cliente = clienteRepository.saveAndFlush(Cliente(nome = "Um cliente", id = 0))
        assertThat(cliente.id).isNotEqualTo(0)
        assertThat(clienteRepository.findById(cliente.id).get().nome).isEqualTo("Um cliente")
    }

    @Test
    fun `deveria salvar um trabalho`() {
        val cliente = clienteRepository.saveAndFlush(Cliente(nome = "Um Cliente"))
        val trabalho = trabalhoRepository.saveAndFlush(Trabalho(nome = "Um Trabalho", cliente = cliente))
        val trabalhoResult = trabalhoRepository.findById(trabalho.id).get()
        assertThat(trabalhoResult.nome).isEqualTo("Um Trabalho")
        assertThat(trabalhoResult.cliente.nome).isEqualToIgnoringCase("Um cliente")
    }


}

