package com.nfrpaiva.taime.dominio.test.qbr

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@JsonPropertyOrder(value = ["questionarioPergunta", "resposta", "ordem","questionarioPerguntaDependentes"])
@Entity
@Table(uniqueConstraints = [(UniqueConstraint(name = "UK_QPR_ORDEM", columnNames = ["ordem", "COD_QUESTIONARIO", "COD_PERGUNTA"]))])
@IdClass(QuestionarioPerguntaRespostaID::class)
data class QuestionarioPerguntaResposta(
        @Id
        @JsonIgnore
        @ManyToOne
        @JoinColumns(value = [
            (JoinColumn(name = "COD_QUESTIONARIO", referencedColumnName = "COD_QUESTIONARIO")),
            (JoinColumn(name = "COD_PERGUNTA", referencedColumnName = "COD_PERGUNTA"))
        ], foreignKey = ForeignKey(name = "FK_QPR_TO_QP"))
        val questionarioPergunta: QuestionarioPergunta,
        @Id
        @ManyToOne
        @JoinColumn(name = "COD_RESPOSTA", foreignKey = ForeignKey(name = "FK_QPR_TO_R"))
        val resposta: Resposta,
        @NotNull
        @Column(columnDefinition = "NUMBER(11)")
        val ordem: Int
) {
    @OneToMany(mappedBy = "questionarioRespostaPai")
    val questionarioPerguntaDependentes: MutableList<QuestionarioPergunta> = mutableListOf()

    override fun toString(): String {
        return "QuestionarioPerguntaResposta(questionarioPergunta=$questionarioPergunta, resposta=$resposta)"
    }


}