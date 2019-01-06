package com.nfrpaiva.taime.dominio.test.mtv

data class Pessoa(val nome: String) {

    companion object {
        @JvmStatic
        fun comNomeDe(nome: String): Pessoa {
            return Pessoa(nome)
        }
    }

    fun falarNome(): String {
        return "Meu nome é $nome"
    }

}