package com.nfrpaiva.taime.dominio.test.qbr

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.jetbrains.annotations.NotNull
import javax.persistence.*

@Entity
@JsonPropertyOrder(value = ["pergunta", "respostas", "ordem"])
@Table(uniqueConstraints = [(UniqueConstraint(name = "UK_QP_ORDEM", columnNames = ["ordem", "COD_QUESTIONARIO"]))])
@IdClass(QuestionarioPerguntaID::class)
data class QuestionarioPergunta(@Id
                                @JsonIgnore
                                @ManyToOne
                                @JoinColumn(name = "COD_QUESTIONARIO", foreignKey = ForeignKey(name = "FK_QP_TO_Q"))
                                val questionario: Questionario,
                                @Id
                                @ManyToOne
                                @JoinColumn(name = "COD_PERGUNTA", foreignKey = ForeignKey(name = "FK_QP_TO_P"))
                                val pergunta: Pergunta,
                                @NotNull
                                @Column(columnDefinition = "NUMBER(11)")
                                val ordem: Int

) {
    @JsonIgnore
    @ManyToOne
    @JoinColumns(value = [
        (JoinColumn(name = "COD_QUESTIONARIO_PAI", referencedColumnName = "COD_QUESTIONARIO")),
        (JoinColumn(name = "COD_PERGUNTA_PAI", referencedColumnName = "COD_PERGUNTA")),
        (JoinColumn(name = "COD_RESPOSTA_PAI", referencedColumnName = "COD_RESPOSTA"))],
            foreignKey = ForeignKey(name = "FK_QP_TO_QPR"))

    var questionarioRespostaPai: QuestionarioPerguntaResposta? = null
    @OneToMany(mappedBy = "questionarioPergunta")
    val respostas: List<QuestionarioPerguntaResposta> = mutableListOf()
}