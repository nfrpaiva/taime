package com.nfrpaiva.taime.dominio.test.qbr

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.nfrpaiva.taime.infra.json
import org.assertj.core.api.Assertions.assertThat
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
        val resposta1 = Resposta(1L, "Novo")
        val resposta2 = Resposta(2L, "Renovação")
        em.persist(pergunta1)
        em.persist(resposta1)
        em.persist(resposta2)

        val pergunta2 = Pergunta(2L, "Congenere")
        val resposta3 = Resposta(3L, "Porto Seguro")
        val resposta4 = Resposta(4L, "Sompo")
        em.persist(pergunta2)
        em.persist(resposta3)
        em.persist(resposta4)


        val questionario1Pergunta1 = QuestionarioPergunta(1L, questionario, pergunta1)
        val questionario1Pergunta2 = QuestionarioPergunta(2L, questionario, pergunta2)

        em.persist(questionario1Pergunta1)
        em.persist(questionario1Pergunta2)

        val qpResposta1 = QuestionarioPerguntaResposta(1L, questionario1Pergunta1, resposta1)
        val qpResposta2 = QuestionarioPerguntaResposta(2L, questionario1Pergunta1, resposta2)
        val qpResposta3 = QuestionarioPerguntaResposta(3L, questionario1Pergunta2, resposta3)
        val qpResposta4 = QuestionarioPerguntaResposta(4L, questionario1Pergunta2, resposta4)

        questionario1Pergunta2.questionarioRespostaPai = qpResposta2


        em.persist(qpResposta1)
        em.persist(qpResposta2)
        em.persist(qpResposta3)
        em.persist(qpResposta4)


        em.flush()
        em.clear()
    }

    @Test
    fun buscarSimples() {
        val questionario = em.find(Questionario::class.java, 1L)
        assertThat(questionario.perguntas).hasSize(2)

        val questionarioPergunta1 = questionario.perguntas.filter { it.id == 1L }[0]
        val questionarioPergunta2 = questionario.perguntas.filter { it.id == 2L }[0]

        assertThat(questionarioPergunta1.respostas).hasSize(2)
        assertThat(questionarioPergunta2.respostas).hasSize(2)

        assertThat(questionarioPergunta1.respostas.filter { it.id == 1L }[0].resposta.descricao).isEqualTo("Novo")
        assertThat(questionarioPergunta2.respostas.filter { it.id == 4L }[0].resposta.descricao).isEqualTo("Sompo")

        assertThat(questionarioPergunta1.respostas[1].questionarioPerguntaDependentes).hasSize(1)
        assertThat(questionarioPergunta2.questionarioRespostaPai).isEqualTo(questionarioPergunta1.respostas.filter { it.id == 2L }[0])

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
                                val pergunta: Pergunta,
                                @JsonIgnore
                                @ManyToOne
                                var questionarioRespostaPai: QuestionarioPerguntaResposta? = null
) {
    @OneToMany(mappedBy = "questionarioPergunta")
    val respostas: List<QuestionarioPerguntaResposta> = mutableListOf()
}

@Entity
data class QuestionarioPerguntaResposta(@Id val id: Long,
                                        @JsonIgnore
                                        @OneToOne val questionarioPergunta: QuestionarioPergunta,
                                        @OneToOne val resposta: Resposta,
                                        @OneToMany(mappedBy = "questionarioRespostaPai")
                                        val questionarioPerguntaDependentes: List<QuestionarioPergunta> = mutableListOf()
) {

    override fun toString(): String {
        return "QuestionarioPerguntaResposta(id=$id)"
    }
}

@Entity
data class Pergunta(@Id val id: Long, val descricao: String)

@Entity
data class Resposta(@Id val id: Long, val descricao: String)
