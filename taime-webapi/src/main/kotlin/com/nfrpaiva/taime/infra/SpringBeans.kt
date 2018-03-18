package com.nfrpaiva.taime.infra

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class SpringBeans{

    @Bean
    fun getClock (): Clock =  Clock.systemDefaultZone()

}
