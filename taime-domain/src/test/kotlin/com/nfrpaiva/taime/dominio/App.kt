package com.nfrpaiva.taime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@SpringBootApplication
@Configuration
class app {
    @Bean
    fun getClock() = Clock.systemDefaultZone()
}