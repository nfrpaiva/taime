package com.nfrpaiva.taime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaimeApplication

fun main(args: Array<String>) {
    runApplication<TaimeApplication>(*args)
}