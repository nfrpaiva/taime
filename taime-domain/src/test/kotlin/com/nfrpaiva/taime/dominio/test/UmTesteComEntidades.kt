package com.nfrpaiva.taime.dominio.test

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.*
import javax.persistence.GenerationType.SEQUENCE


@RunWith(SpringRunner::class)
@DataJpaTest
class UmTesteComEntidades {

    @Autowired
    lateinit var alunoRepository: AlunoRepository
    @Autowired
    lateinit var cursoRepository: CursoRepository

    @Test
    fun testOneToManyManyToOne() {
        val size = 20

        val curso = curso(size)
        val listAlunos = alunoRepository.findAll()
        listAlunos.forEach { assertThat(it.id).isNotNull().isNotEqualTo(0) }
        val umCurso = cursoRepository.findById(curso.id).get()

        assertThat(listAlunos).hasSize(size)
        assertThat(umCurso.alunos).hasSize(size)
        println(umCurso)
    }

    @Test
    fun testFilrarAlunos() {
        val curso = curso(20)
        assertThat(curso.alunos).hasSize(20)
        assertThat(curso.alunos.filter { it.id % 2 == 0L }).hasSize(20 / 2)
    }

    private fun curso(alunos: Int): Curso {
        val curso = Curso()
        for (i in 1..alunos) {
            alunoRepository.save(Aluno(curso = curso, nome = "Aluno $i"))
        }
        return curso
    }

    @Test
    fun testeIDsCurso() {
        val curso = Curso()
        assertThat(curso.id).isEqualTo(0)
        cursoRepository.save(curso)
        assertThat(curso.id).isNotEqualTo(0)
    }

    @Test
    fun testIDsAluno() {
        val aluno = Aluno(nome = "Um Aluno")
        assertThat(aluno.id).isEqualTo(0)
        alunoRepository.save(aluno)
        assertThat(aluno.id).isNotEqualTo(0)
        println(aluno)
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
        var id: Long = 0,
        var nome: String,
        @JsonIgnore
        @ManyToOne(cascade = [CascadeType.PERSIST])
        @JoinColumn(foreignKey = ForeignKey(name = "FK_CURSO_ID"))
        var curso: Curso? = null


) {
    init {
        curso?.alunos?.add(this)
        println("Iniciando $nome com id $id")
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
        var id: Long = 0,
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