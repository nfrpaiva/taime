package com.nfrpaiva.taime

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.time.Clock

@SpringBootApplication
//    @ComponentScan(basePackages = ["com.nfrpaiva.taime"])
//    @EnableJpaRepositories(basePackages = ["com.nfrpaiva.taime.dominio"])
//    @EntityScan(basePackages = ["com.nfrpaiva.taime.dominio"])
class App {
    @Bean
    fun getClock(): Clock = Clock.systemDefaultZone()
}