package com.nfrpaiva.taime.dominio.test

import com.fasterxml.jackson.annotation.JsonIgnore
import org.assertj.core.util.Lists
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

    constructor(nome: String, vararg cursos: Curso) : this(nome = nome) {
        cursos.forEach { addCurso(it) }
        Lists.newArrayList("")
    }

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