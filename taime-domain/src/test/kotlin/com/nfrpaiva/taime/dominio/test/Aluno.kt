package com.nfrpaiva.taime.dominio.test

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Aluno(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ALUNO_GEN")
        @SequenceGenerator(name = "SQ_ALUNO_GEN", sequenceName = "SQ_ALUNO")
        var id: Long = 0,
        var nome: String,
        @JsonIgnore
        @ManyToOne(cascade = [(CascadeType.PERSIST)])
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