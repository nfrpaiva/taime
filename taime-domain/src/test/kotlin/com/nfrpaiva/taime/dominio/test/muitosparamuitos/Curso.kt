package com.nfrpaiva.taime.dominio.test.muitosparamuitos

import com.nfrpaiva.taime.infra.json
import javax.persistence.*

@Entity
data class Curso(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CURSO_GEN")
        @SequenceGenerator(name = "SQ_CURSO_GEN", sequenceName = "SQ_CURSO")
        var id: Long = 0
) {
    @ManyToMany(mappedBy = "_cursos", cascade = [CascadeType.PERSIST])
    private val _alunos: MutableSet<Aluno> = mutableSetOf()

    override fun toString(): String {
        return "Curso: ${this.json()}"
    }

    val alunos: Set<Aluno>
        get() {
            return _alunos.toSet()
        }

    fun addAluno(aluno: Aluno) {
        _alunos.add(aluno)
        if (!aluno.cursos.contains(this)) aluno.addCurso(this)
    }

    fun removeAluno(aluno: Aluno) {
        _alunos.remove(aluno)
        if (aluno.cursos.contains(this)) aluno.removeCurso(this)
    }
}