package com.nfrpaiva.taime.dominio.test

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.*
import javax.persistence.GenerationType.*


@RunWith(SpringRunner::class)
@DataJpaTest
class UmTesteComEntidades {

    @Autowired
    lateinit var alunoRepository: AlunoRepository
    @Autowired
    lateinit var cursoRepository: CursoRepository

    @Test
    fun teste() {
        val size = 20
        val curso = Curso()

        for (i in 1..size) {
            alunoRepository.save(Aluno(curso = curso, nome = "Aluno $i"))
        }
        val listAlunos = alunoRepository.findAll()
        val umCurso = cursoRepository.findById(curso.id!!).get()

        assertThat(listAlunos).hasSize(size)
        assertThat(umCurso.alunos).hasSize(size)
    }

}

@Repository
interface CursoRepository : JpaRepository<Curso, Long>

@Repository
interface AlunoRepository : JpaRepository<Aluno, Long>

@Entity
data class Aluno(
        @Id
        @GeneratedValue(strategy = SEQUENCE, generator = "SQ_ALUNO_GEN")
        @SequenceGenerator(name = "SQ_ALUNO_GEN", sequenceName = "SQ_ALUNO")
        var id: Long? = null,
        var nome: String,
        @JsonIgnore
        @ManyToOne(cascade = [CascadeType.PERSIST])
        @JoinColumn(foreignKey = ForeignKey(name = "FK_CURSO_ID"))
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
        @GeneratedValue(strategy = SEQUENCE, generator = "SQ_CURSO_GEN")
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