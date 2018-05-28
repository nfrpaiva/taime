package com.nfrpaiva.taime.dominio.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManager


@RunWith(SpringRunner::class)
@DataJpaTest
class UmTesteComEntidades {

    @Autowired
    lateinit var alunoRepository: AlunoRepository
    @Autowired
    lateinit var cursoRepository: CursoRepository

    @Autowired
    lateinit var em: EntityManager

    @Test
    fun testOneToManyManyToOne() {
        val size = 1

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
        val result = cursoRepository.findById(curso(20).id).get()
        assertThat(result.alunos).hasSize(20)
        assertThat(result.alunos.filter { it.id % 2 == 0L }).hasSize(20 / 2)
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
            val aluno = Aluno(nome = "Aluno $i")
            aluno.cursos.add(curso)
            //curso.alunos.add(aluno)
            alunoRepository.saveAndFlush(aluno)
        }
        em.clear()
        return curso
    }
}