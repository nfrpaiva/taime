package com.nfrpaiva.taime.dominio.test.qbr

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.nfrpaiva.taime.infra.json
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.annotation.Commit
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.*

@RunWith(SpringRunner::class)
@DataJpaTest
@Commit
@ComponentScan(basePackageClasses = [QuestionarioTest::class])
@EnableJpaRepositories(basePackageClasses = [QuestionarioTest::class])
@EntityScan(basePackageClasses = [QuestionarioTest::class])
class QuestionarioTest {

    @Autowired
    lateinit var em: EntityManager

    @Before
    fun criar() {
        val questionario = Questionario(1L)
        em.persist(questionario)

        val pergunta1 = Pergunta(1L, "Tipo Seguro")
        val pergunta2 = Pergunta(2L, "Congenere")

        em.persist(pergunta1)
        em.persist(pergunta2)

        val questionario1Pergunta1 = QuestionarioPergunta(1L, questionario, pergunta1)
        val questionario1Pergunta2 = QuestionarioPergunta(2L, questionario, pergunta2)

        em.persist(questionario1Pergunta1)
        em.persist(questionario1Pergunta2)

        val resposta1Pertunta1 = Resposta(1L, "Novo", questionario1Pergunta1)
        val resposta2Pertunta1 = Resposta(2L, "Renovação", questionario1Pergunta1, questionario1Pergunta2)

        val resposta1Pertunta2 = Resposta(3L, "Porto Seguro", questionario1Pergunta2)
        val resposta2Pertunta2 = Resposta(4L, "Sompo", questionario1Pergunta2)

        em.persist(resposta1Pertunta1)
        em.persist(resposta2Pertunta1)
        em.persist(resposta1Pertunta2)
        em.persist(resposta2Pertunta2)

        em.flush()
        em.clear()
    }

    @Test
    fun buscarSimples() {
        val questionario = em.find(Questionario::class.java, 1L)
        Assertions.assertThat(questionario.perguntas).hasSize(2)
        println(questionario.json())
    }
}

@Entity
@JsonPropertyOrder(value = ["id", "perguntas"])
data class Questionario(@Id val id: Long) {
    @OneToMany(mappedBy = "questionario")
    val perguntas: List<QuestionarioPergunta> = mutableListOf()
}

@Entity
@JsonPropertyOrder(value = ["id", "pergunta", "respostas"])
data class QuestionarioPergunta(@Id val id: Long,
                                @JsonIgnore
                                @OneToOne
                                val questionario: Questionario,
                                @OneToOne
                                val pergunta: Pergunta) {
    @OneToMany(mappedBy = "questionarioPergunta")
    val respostas: List<Resposta> = mutableListOf()

}

@Entity
data class Resposta(@Id val id: Long,
                    val descricao: String,
                    @JsonIgnore
                    @ManyToOne val questionarioPergunta: QuestionarioPergunta,
                    @OneToOne val dependente: QuestionarioPergunta? = null
)

@Entity
data class Pergunta(@Id val id: Long, val descricao: String)
