package com.nfrpaiva.taime.dominio.test

import org.junit.Test

class NothingTest {
    @Test
    fun test() {
        var x = if (umaCoisa()) {
            42
        } else {
            notfound("")
        }
        x = x + 40
    }

    private fun notfound(mensagem: String):Nothing {
           throw Exception(message = mensagem)
    }

    private fun umaCoisa(): Boolean {
        return false
    }
}