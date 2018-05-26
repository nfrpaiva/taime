package com.nfrpaiva.taime.dominio.test

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.*


@RunWith(SpringRunner::class)
@DataJpaTest
class UmTesteComEntidades {

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun teste() {
        val size = 2
        val curso = Curso()

        for (i in 1..size) {
            entityManager.persist(Aluno(curso = curso, nome = "Aluno $i"))
        }

        val result = entityManager.createQuery("select p from Aluno p", Aluno::class.java).resultList
        println(result)
        assertThat(result).hasSize(size)
        entityManager.clear()
        val curso1 = entityManager.find(Curso::class.java, curso.id)
        assertThat(curso1.alunos).hasSize(size)
        println(curso1)
    }

}

@Entity
data class Aluno(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,
        var nome: String,
        @JsonIgnore
        @ManyToOne(cascade = [CascadeType.PERSIST])
        var curso: Curso

) {
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
        @GeneratedValue(strategy = GenerationType.AUTO)
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
    mapper.enable(SerializationFeature.INDENT_OUTPUT)
    return mapper.writeValueAsString(this)
}