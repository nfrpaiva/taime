package com.nfrpaiva.taime.dominio.test.qbr

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
@JsonPropertyOrder(value = ["codigo", "perguntas"])
data class Questionario(@Id
                        @Column(name = "COD_QUESTIONARIO", columnDefinition = "NUMBER(11)")
                        val codigo: Long,
                        @Column(columnDefinition = "VARCHAR2(255)")
                        val descricao: String
) {
    @OneToMany(mappedBy = "questionario")
    val perguntas: List<QuestionarioPergunta> = mutableListOf()
}