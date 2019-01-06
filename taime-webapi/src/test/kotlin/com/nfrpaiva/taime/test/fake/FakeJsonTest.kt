package com.nfrpaiva.taime.test.fake

import com.nfrpaiva.taime.infra.json
import com.nfrpaiva.taime.infra.jsonIdented
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.domain.Example
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.annotation.Commit
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate

@RunWith(SpringRunner::class)
@DataJpaTest
@Commit
@ComponentScan(basePackageClasses = [FakeJsonTest::class])
@EnableJpaRepositories(basePackageClasses = [FakeJsonTest::class])
@EntityScan(basePackageClasses = [FakeJsonTest::class])
class FakeJsonTest {

    val log = LoggerFactory.getLogger(this.javaClass)!!

    @Autowired
    lateinit var repository: UserRepository

    @Test
    fun callJsonPlaceHolder() {
        val template = RestTemplate()
        val url = "https://jsonplaceholder.typicode.com/users"
        val request = template.getForEntity(url, Array<User>::class.java)
        val body = request.body!!
        body.forEach {
            log.info("Um json:  ${it.json()}")
            repository.saveAndFlush(it)
        }
        assertThat(repository.count()).isEqualTo(10)
        assertThat(repository.count(Example.of(User(name = "Leanne Graham")))).isEqualTo(1)
    }

    @Test
    fun `checar se a função json aceita nulo sem problemas`() {
        var valorNulo: String? = null
        assertThat(valorNulo.json()).isEqualTo("null")
    }
}