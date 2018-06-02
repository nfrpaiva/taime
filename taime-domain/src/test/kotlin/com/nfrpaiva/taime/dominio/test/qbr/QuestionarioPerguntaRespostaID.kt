package com.nfrpaiva.taime.dominio.test.qbr

import java.io.Serializable

data class QuestionarioPerguntaRespostaID(var questionarioPergunta: QuestionarioPergunta? = null, var resposta: Resposta? = null) : Serializable