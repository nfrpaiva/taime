package com.nfrpaiva.taime.test.fake

import org.assertj.core.api.Assertions
import org.junit.Test
import org.springframework.web.client.RestTemplate

class FakeJsonTest {
    @Test
    fun callJsonPlaceHolder() {
        val template = RestTemplate()
        val url = "https://jsonplaceholder.typicode.com/users"
        val request = template.getForEntity(url, Array<User>::class.java)
        println(request.body.toJson())
    }

    @Test
    fun `checar se a função json aceita nulo sem problemas`() {
        var valorNulo: String? = null
        Assertions.assertThat(valorNulo.toJson()).isEqualTo("null")
    }
}
