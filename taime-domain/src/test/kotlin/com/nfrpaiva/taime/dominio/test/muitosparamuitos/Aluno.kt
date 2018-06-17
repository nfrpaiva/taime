package com.nfrpaiva.taime.dominio.test.muitosparamuitos

import com.fasterxml.jackson.annotation.JsonIgnore
import com.nfrpaiva.taime.infra.json
import javax.persistence.*
import javax.persistence.CascadeType.PERSIST

@Entity
data class Aluno(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ALUNO_GEN")
        @SequenceGenerator(name = "SQ_ALUNO_GEN", sequenceName = "SQ_ALUNO")
        var id: Long = 0,
        var nome: String
) {

    @JsonIgnore
    @ManyToMany(cascade = [PERSIST])
    @JoinTable(name = "aluno_has_cursos")
    private val _cursos: MutableSet<Curso> = mutableSetOf()

    val cursos: Set<Curso>
        @JsonIgnore
        get() {
            return _cursos.toSet()
        }

    fun addCurso(curso: Curso) {
        _cursos.add(curso)
        if (!curso.alunos.contains(this)) curso.addAluno(this)
    }

    fun removeCurso(curso: Curso) {
        _cursos.remove(curso)
        if (curso.alunos.contains(this)) curso.removeAluno(this)
    }

    override fun toString(): String {
        return this.json()
    }

}