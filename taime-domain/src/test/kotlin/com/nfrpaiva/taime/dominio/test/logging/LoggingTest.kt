package com.nfrpaiva.taime.dominio.test.logging

import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class LoggingTest {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Test
    fun logIt() {
        log.error("Um error")
        log.info("Uma info")
        log.debug("Um debug")
        log.warn("Um warn")
    }

}
