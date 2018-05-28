package com.nfrpaiva.taime.dominio.test

import javax.persistence.*

@Entity
data class Curso(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CURSO_GEN")
        @SequenceGenerator(name = "SQ_CURSO_GEN", sequenceName = "SQ_CURSO")
        var id: Long = 0
) {
    @ManyToMany(mappedBy = "cursos")
    val alunos: MutableSet<Aluno> = mutableSetOf()

    override fun toString(): String {
        return "Curso: ${this.json()}"
    }
}