package com.nfrpaiva.taime.test

import com.nfrpaiva.taime.dominio.ApontamentoRepository
import com.nfrpaiva.taime.dominio.ClienteRepository
import com.nfrpaiva.taime.dominio.TrabalhoRepository
import org.mockito.Mockito
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MockBeans {

    @Bean
    fun getTrabalhoRepository(): TrabalhoRepository = Mockito.mock(TrabalhoRepository::class.java)

    @Bean
    fun getApontamentoRepository(): ApontamentoRepository = Mockito.mock(ApontamentoRepository::class.java)

    @Bean
    fun getClientRepository(): ClienteRepository = Mockito.mock(ClienteRepository::class.java)
}