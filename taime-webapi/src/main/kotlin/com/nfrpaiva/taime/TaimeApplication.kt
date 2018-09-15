package com.nfrpaiva.taime

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class TaimeApplication {

    @Bean
    fun init() = CommandLineRunner { }
}

fun main(args: Array<String>) {
    runApplication<TaimeApplication>(*args)
}