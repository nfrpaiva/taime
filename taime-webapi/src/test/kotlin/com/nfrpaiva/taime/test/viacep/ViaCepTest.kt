package com.nfrpaiva.taime.test.viacep

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate

class ViaCepTest {

    private val template = RestTemplate()

    @Test
    fun consultarCep() {
        val url = "https://viacep.com.br/ws/06192150/json/"
        val result = template.getForEntity(url, RespostaCep::class.java)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body).isNotNull
        val resposta = result.body!!
        with(resposta) {
            assertThat(cep).isEqualTo("06192-150")
            assertThat(localidade).isEqualTo("Osasco")
        }
    }

    @Test
    fun listaCep() {
        val url = "https://viacep.com.br/ws/SP/Osasco/vitorio/json"
        val result = template.getForEntity(url, emptyArray<RespostaCep>().javaClass)
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body).isNotNull
        val resposta = result.body!!

        with(resposta[0]!!) {
            assertThat(cep).isEqualTo("06070-220")
            assertThat(localidade).isEqualTo("Osasco")
        }

        with(resposta[1]!!) {
            assertThat(cep).isEqualTo("06192-150")
            assertThat(localidade).isEqualTo("Osasco")
        }
    }

}