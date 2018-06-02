package com.nfrpaiva.taime.dominio.test.qbr

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.nfrpaiva.taime.infra.json
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.annotations.NotNull
import org.junit.After
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


        val questionario1Pergunta1 = QuestionarioPergunta(1L, questionario, pergunta1, 1)
        val questionario1Pergunta2 = QuestionarioPergunta(2L, questionario, pergunta2, 2)

        em.persist(questionario1Pergunta1)
        em.persist(questionario1Pergunta2)

        val qpResposta1 = QuestionarioPerguntaResposta(1L, questionario1Pergunta1, resposta1,1)
        val qpResposta2 = QuestionarioPerguntaResposta(2L, questionario1Pergunta1, resposta2,2)
        val qpResposta3 = QuestionarioPerguntaResposta(3L, questionario1Pergunta2, resposta3,1)
        val qpResposta4 = QuestionarioPerguntaResposta(4L, questionario1Pergunta2, resposta4,2)

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

    @Test
    fun buscaSomenteSemRespostaPai() {
        val query = "select q from Questionario q join fetch q.perguntas p where p.questionarioRespostaPai is null"
        val questionario = em.createQuery(query, Questionario::class.java).resultList
        assertThat(questionario).hasSize(1)
        assertThat(questionario[0].perguntas).hasSize(1)
        println(questionario.json())

    }
    @After
    fun cleanup(){
        em.createQuery("update QuestionarioPergunta a set a.questionarioRespostaPai = null").executeUpdate()
        em.createQuery("delete from QuestionarioPerguntaResposta").executeUpdate()
        em.createQuery("delete from QuestionarioPergunta").executeUpdate()
        em.createQuery("delete from Questionario").executeUpdate()
        em.createQuery("delete from Pergunta").executeUpdate()
        em.createQuery("delete from Resposta").executeUpdate()
        em.flush()
        em.clear()
    }
}

@Entity
@JsonPropertyOrder(value = ["id", "perguntas"])
data class Questionario(@Id
                        @Column(columnDefinition = "NUMBER(11)")
                        val id: Long) {
    @OneToMany(mappedBy = "questionario")
    val perguntas: List<QuestionarioPergunta> = mutableListOf()
}

@Entity
@JsonPropertyOrder(value = ["id", "pergunta", "respostas"])
@Table(uniqueConstraints = [UniqueConstraint(name = "UK_QP_ORDEM", columnNames = ["ordem", "questionario_id"])])

data class QuestionarioPergunta(@Id
                                @Column(columnDefinition = "NUMBER(11)")
                                val id: Long,
                                @JsonIgnore
                                @OneToOne
                                @JoinColumn(foreignKey = ForeignKey(name = "FK_QP_TO_Q") )
                                val questionario: Questionario,
                                @OneToOne
                                @JoinColumn(foreignKey = ForeignKey(name = "FK_QP_TO_P") )
                                val pergunta: Pergunta,
                                @NotNull
                                val ordem: Int,
                                @JsonIgnore
                                @ManyToOne
                                @JoinColumn(foreignKey = ForeignKey(name = "FK_QP_TO_QPR") )
                                var questionarioRespostaPai: QuestionarioPerguntaResposta? = null
) {
    @OneToMany(mappedBy = "questionarioPergunta")
    val respostas: List<QuestionarioPerguntaResposta> = mutableListOf()
}

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "UK_QPR_ORDEM", columnNames = ["ordem", "questionario_pergunta_id"])])
data class QuestionarioPerguntaResposta(@Id
                                        @Column(columnDefinition = "NUMBER(11)")
                                        val id: Long,
                                        @JsonIgnore
                                        @OneToOne
                                        @JoinColumn(foreignKey = ForeignKey(name = "FK_QPR_TO_QP") )
                                        val questionarioPergunta: QuestionarioPergunta,
                                        @OneToOne
                                        @JoinColumn(name="RESPOSTA_ID", foreignKey = ForeignKey(name = "FK_QPR_TO_R") )
                                        val resposta: Resposta,
                                        @NotNull
                                        val ordem: Int,
                                        @OneToMany(mappedBy = "questionarioRespostaPai")
                                        val questionarioPerguntaDependentes: List<QuestionarioPergunta> = mutableListOf()
) {

    override fun toString(): String {
        return "QuestionarioPerguntaResposta(id=$id)"
    }
}

@Entity
data class Pergunta(@Id
                    @Column(columnDefinition = "NUMBER(11)")
                    val id: Long,
                    @Column(columnDefinition = "VARCHAR2(255)")
                    val descricao: String)

@Entity
data class Resposta(@Id
                    @Column(name="RESPOSTA_ID", columnDefinition = "NUMBER(11)")
                    val id: Long,
                    @Column(columnDefinition = "VARCHAR2(255)")
                    val descricao: String)
