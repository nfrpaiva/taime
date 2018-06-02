package com.nfrpaiva.taime.dominio.test.qbr

import java.io.Serializable

data class QuestionarioPerguntaID(val questionario: Questionario? = null, val pergunta: Pergunta? = null) : Serializable