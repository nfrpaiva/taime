package com.nfrpaiva.taime.dominio.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.annotation.Commit
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.*

@RunWith(SpringRunner::class)
@DataJpaTest
@Commit
class QueryUmParaMuitosTest {


    @SpringBootApplication
    @Configuration
    class Inner {

    }

    @Autowired
    lateinit var em: EntityManager

    @Test
    fun testQuery() {
        val pai = Pai(id = 1, nome = "Um Pai")
        pai.filhos = mutableSetOf(Filho(nome = "Filho 1", id = 1, pai = pai), Filho(nome = "Filho 2", id = 2, pai = pai))
        em.persist(pai)
        em.flush()
        em.clear()
        val result: Pai = em.find(Pai::class.java, 1L)
        assertThat(result.filhos).hasSize(2)
        em.clear()
        val list1 = em.createQuery("select p from Pai p", Pai::class.java).resultList
        assertThat(list1).hasSize(1)

        em.clear()
        val list2 = em.createQuery("select p from Pai p join fetch p.filhos f where f.id =  1", Pai::class.java).resultList
        assertThat(list2).hasSize(1)
        assertThat(list2[0].filhos).hasSize(1)
        assertThat(list2[0].filhos.iterator().next().id).isEqualTo(1)
        em.clear()
        val list3 = em.createQuery("select p from Pai p join fetch p.filhos f where f.id =  2", Pai::class.java).resultList
        assertThat(list3).hasSize(1)
        assertThat(list3[0].filhos).hasSize(1)
        assertThat(list3[0].filhos.iterator().next().id).isEqualTo(2)
        em.clear()
        val list4 = em.createQuery("select p from Pai p join fetch p.filhos f where f.id =  3", Pai::class.java).resultList
        assertThat(list4).hasSize(0)

        em.clear()
        val list5 = em.createQuery("select p from Pai p join p.filhos f where f.id =  1", Pai::class.java).resultList
        assertThat(list5).hasSize(1)
        assertThat(list5[0].filhos).hasSize(2)

    }
}

@Entity
data class Pai(@Id var id: Long, var nome: String) {
    @OneToMany(mappedBy = "pai", cascade = [CascadeType.ALL], orphanRemoval = true)
    var filhos: MutableSet<Filho> = mutableSetOf()

}

@Entity
data class Filho(@Id var id: Long, var nome: String, @ManyToOne var pai: Pai)