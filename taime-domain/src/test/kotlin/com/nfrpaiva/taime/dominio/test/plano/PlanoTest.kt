package com.nfrpaiva.taime.dominio.test.plano

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.annotation.Commit
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@RunWith(SpringRunner::class)
@DataJpaTest
@Commit
@ComponentScan(basePackageClasses = [PlanoTest::class])
@EnableJpaRepositories(basePackageClasses = [PlanoTest::class])
@EntityScan(basePackageClasses = [PlanoTest::class])
class PlanoTest {

    @Autowired
    lateinit var repository: PlanoRepository;

    @Test
    fun insert() {
        for(i in 1..100)
        repository.save(Plano())
    }

}

@Entity
class Plano(@Id
            @GeneratedValue
            var id: Long = -1)

interface PlanoRepository : JpaRepository<Plano, Long>