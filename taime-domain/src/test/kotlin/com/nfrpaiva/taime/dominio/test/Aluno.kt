package com.nfrpaiva.taime.dominio.test

import com.fasterxml.jackson.annotation.JsonIgnore
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
    val cursos: MutableSet<Curso> = mutableSetOf()

    override fun toString(): String {
        return this.json()
    }

}