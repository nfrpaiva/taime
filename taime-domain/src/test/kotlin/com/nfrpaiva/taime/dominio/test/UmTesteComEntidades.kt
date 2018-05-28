package com.nfrpaiva.taime.dominio.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner


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

    private fun curso(alunos: Int): Curso {
        val curso = Curso()
        for (i in 1..alunos) {
            alunoRepository.save(Aluno(nome = "Aluno $i").curso(curso))
        }
        return curso
    }
}