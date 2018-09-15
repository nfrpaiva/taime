package com.nfrpaiva.taime.t1

import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.annotation.Commit
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
@EntityScan(basePackageClasses = [TesteIsolado::class])
@EnableJpaRepositories(basePackageClasses = [TesteIsolado::class])
@ComponentScan(basePackageClasses = [TesteIsolado::class])
class TesteIsolado {

    @Autowired
    lateinit var pinoRepository: PinoRepository

    @Test
    @Commit
    fun teste() {
        var filhos = listOf(Filho(1, "filho1"), Filho(2, "filho2"))
        val pai = Pai(1L, "Um nome")
        pai.filhos = filhos
        pinoRepository.saveAndFlush(pai)
        val one = pinoRepository.findById(pai.id)
        Assertions.assertThat(one.isPresent).isTrue()
    }
}