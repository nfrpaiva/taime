package com.nfrpaiva.taime.dominio.test

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializationFeature.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.*
import javax.persistence.GenerationType.*


@RunWith(SpringRunner::class)
@DataJpaTest
class UmTesteComEntidades {

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun teste() {
        val size = 20
        val curso = Curso()

        for (i in 1..size) {
            entityManager.persist(Aluno(curso = curso, nome = "Aluno $i"))
        }

        val query = entityManager.createNamedQuery(Aluno.FIND_ALL, Aluno::class.java)
        val result = query.resultList
        assertThat(result).hasSize(size)
        entityManager.clear()
        val curso1 = entityManager.find(Curso::class.java, curso.id)
        assertThat(curso1.alunos).hasSize(size)
    }

}

@Entity
@NamedQuery(name = Aluno.FIND_ALL, query = "select a from Aluno a")
data class Aluno(
        @Id
        @GeneratedValue(strategy = SEQUENCE,  generator = "SQ_ALUNO_GEN")
        @SequenceGenerator(name = "SQ_ALUNO_GEN", sequenceName = "SQ_ALUNO")
        var id: Long? = null,
        var nome: String,
        @JsonIgnore
        @ManyToOne(cascade = [CascadeType.PERSIST])
        @JoinColumn(foreignKey = ForeignKey(name = "FK_CURSO_ID"))
        var curso: Curso

) {
    companion object {
        const val FIND_ALL = "Aluno.findAll"
    }

    init {
        curso.alunos.add(this)
    }

    override fun toString(): String {
        return this.json()
    }

}

@Entity
data class Curso(
        @Id
        @GeneratedValue(strategy = SEQUENCE,  generator = "SQ_CURSO_GEN")
        @SequenceGenerator(name = "SQ_CURSO_GEN", sequenceName = "SQ_CURSO")
        var id: Long? = null,
        @OneToMany(mappedBy = "curso")
        var alunos: MutableList<Aluno> = mutableListOf()
) {
    override fun toString(): String {
        return "Curso: ${this.json()}"
    }
}

fun Any.json(): String {
    val mapper = ObjectMapper()
    mapper.enable(INDENT_OUTPUT)
    return mapper.writeValueAsString(this)
}