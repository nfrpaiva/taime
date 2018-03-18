package com.nfrpaiva.taime.test

import com.nfrpaiva.taime.dominio.TrabalhoRepository
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MockBeans{

    @Bean
    fun getTrabalhoRepository() = Mockito.mock(TrabalhoRepository::class.java)

}