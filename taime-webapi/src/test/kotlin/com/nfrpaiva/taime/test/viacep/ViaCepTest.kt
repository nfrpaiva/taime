package com.nfrpaiva.taime.test.viacep

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate

class ViaCepTest {
    private val formato = "json"
    private val url = "https://viacep.com.br/ws/{cep}/{formato}/"
    private val template = RestTemplate()
    @Test
    fun consultarCep() {
        var result = template.getForEntity(url, RespostaCep::class.java, "06192150", formato)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body).isNotNull
        var resposta = result.body!!
        with(resposta) {
            assertThat(cep).isEqualTo("06192-150")
            assertThat(localidade).isEqualTo("Osasco")
        }
    }

    data class RespostaCep(var cep: String = "", var localidade: String = "")

}

