package com.nfrpaiva.taime.dominio.test.qbr

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Resposta(@Id
                    @Column(name = "COD_RESPOSTA", columnDefinition = "NUMBER(11)")
                    val codigo: Long,
                    @Column(columnDefinition = "VARCHAR2(255)")
                    val descricao: String)