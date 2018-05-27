package com.nfrpaiva.taime.dominio.test

import javax.persistence.*

@Entity
data class Curso(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CURSO_GEN")
        @SequenceGenerator(name = "SQ_CURSO_GEN", sequenceName = "SQ_CURSO")
        var id: Long = 0,
        @OneToMany(mappedBy = "curso")
        var alunos: MutableList<Aluno> = mutableListOf()
) {
    override fun toString(): String {
        return "Curso: ${this.json()}"
    }
}