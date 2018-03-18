package com.nfrpaiva.taime

import com.nfrpaiva.taime.dominio.*
import org.assertj.core.api.Assertions.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.Commit
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
    fun `deveria salvar um trabalho` (){
        val cliente = clienteRepository.saveAndFlush(Cliente(nome= "Um Cliente"))
        val trabalho = trabalhoRepository.saveAndFlush(Trabalho(nome = "Um Trabalho", cliente = cliente))
        val trabalhoResult = trabalhoRepository.findById(trabalho.id).get()
        assertThat(trabalhoResult.nome).isEqualTo("Um Trabalho")
        assertThat(trabalhoResult.cliente.nome).isEqualToIgnoringCase("Um cliente")
    }

    @Test
    fun `nao deveria inserir um apontamento de outro trabalho`(){
        val cliente = clienteRepository.saveAndFlush(Cliente(nome= "Um Cliente"))

        val trabalho1 = trabalhoRepository.saveAndFlush(Trabalho(nome = "Um Trabalho 1", cliente = cliente))

        val apontamento1 =  Apontamento(trabalho = trabalho1, descricao = "")
        val apontamento2 =  Apontamento(trabalho = trabalho1, descricao = "")
        println(apontamento1)
        println(apontamento2)

        trabalho1.apontamentos.add(apontamento2)
        val trabalho = trabalhoRepository.saveAndFlush(trabalho1)
        println(trabalho)

    }

}