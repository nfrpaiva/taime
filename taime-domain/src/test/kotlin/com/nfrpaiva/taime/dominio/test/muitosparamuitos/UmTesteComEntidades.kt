package com.nfrpaiva.taime.dominio.test.muitosparamuitos

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@DataJpaTest(showSql = true)
@ComponentScan(basePackageClasses = [UmTesteComEntidades::class])
@EnableJpaRepositories(basePackageClasses = [UmTesteComEntidades::class])
@EntityScan(basePackageClasses = [UmTesteComEntidades::class])
class UmTesteComEntidades {

    @Autowired
    lateinit var alunoRepository: AlunoRepository
    @Autowired
    lateinit var cursoRepository: CursoRepository

    @Test
    fun testOneToManyManyToOne() {
        val size = 20
        val curso = curso(size)
        cursoRepository.save(curso)
        val listAlunos = alunoRepository.findAll()
        listAlunos.forEach { assertThat(it.id).isNotNull().isNotEqualTo(0) }
        val umCurso = cursoRepository.findById(curso.id).get()
        assertThat(listAlunos).hasSize(size)
        assertThat(umCurso.alunos).hasSize(size)
    }

    @Test
    fun testFilrarAlunos() {
        val curso = cursoRepository.save(curso(20))
        val result = cursoRepository.findById(curso.id).get()
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
    }

    private fun curso(alunos: Int): Curso {
        val curso = Curso()
        for (i in 1..alunos) {
            curso.addAluno(Aluno(nome = "Aluno $i"))
        }
        return curso
    }
}