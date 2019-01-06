package com.nfrpaiva.taime.dominio.test.mtv

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class MtvTest {

    @Autowired
    lateinit var service: SomeService

    @Test
    fun someTest() {
        val x = Pessoa.comNomeDe("Nilton")
        assertThat(x.falarNome()).isEqualTo("Meu nome é Nilton")
        assertThat(Pessoa("Nilton")).isEqualTo(Pessoa("Nilton"))
        assertThat(Pessoa("Jonh Doe")).isNotEqualTo(Pessoa("Nilton"))
        assertThat(service).isNotNull
        assertThat(service.somar(1, 1)).isEqualTo(2)
    }

    @Configuration
    class Config {
        @Bean
        fun someServices(): SomeService {
            return SomeService()
        }
    }

}