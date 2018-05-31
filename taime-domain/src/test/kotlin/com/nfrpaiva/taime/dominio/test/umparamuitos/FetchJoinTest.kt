package com.nfrpaiva.taime.dominio.test.umparamuitos

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.annotation.Commit
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.*

@RunWith(SpringRunner::class)
@DataJpaTest
@Commit
@ComponentScan(basePackageClasses = [FetchJoinTest::class])
@EnableJpaRepositories(basePackageClasses = [FetchJoinTest::class])
@EntityScan(basePackageClasses = [FetchJoinTest::class])
class FetchJoinTest {

    @Autowired
    lateinit var em: EntityManager

    @After
    fun clean() {
        em.clear()
        em.createQuery("select p from Pai p", Pai::class.java).resultList.forEach { em.remove(it) }
        em.flush()
        em.clear()
    }

    @Before
    fun popular() {
        persistir(pai())
    }

    @Test
    fun `pai sem join com filho`() {
        val result: Pai = em.find(Pai::class.java, 1L)
        assertThat(result.filhos).hasSize(2)
        em.clear()
        val list = em.createQuery("select p from Pai p", Pai::class.java).resultList
        assertThat(list).hasSize(1)
    }

    @Test
    fun `pai e filho com id 1`() {
        val query = "select p from Pai p join fetch p.filhos f where f.id = 1"
        val list = em.createQuery(query, Pai::class.java).resultList
        assertThat(list).hasSize(1)
        assertThat(list[0].filhos).hasSize(1)
        assertThat(list[0].filhos.iterator().next().id).isEqualTo(1)
    }

    @Test
    fun `pai e  filho com id 2`() {
        val query = "select p from Pai p join fetch p.filhos f where f.id = 2"
        val list = em.createQuery(query, Pai::class.java).resultList
        assertThat(list).hasSize(1)
        assertThat(list[0].filhos).hasSize(1)
        assertThat(list[0].filhos.iterator().next().id).isEqualTo(2)
    }

    @Test
    fun `pai e nenhum filho com id 3`() {
        val query = "select p from Pai p join fetch p.filhos f where f.id = 3"
        val list = em.createQuery(query, Pai::class.java).resultList
        assertThat(list).hasSize(0)
    }

    @Test
    fun `pai e todos os seus filhos`() {
        val query = "select p from Pai p join p.filhos f where f.id = 1"
        val list = em.createQuery(query, Pai::class.java).resultList
        assertThat(list).hasSize(1)
        assertThat(list[0].filhos).hasSize(2)
    }

    @Test
    fun `pai um filho e uma filha`() {
        val query = "select p from Pai p join fetch p.filhos f1 join fetch p.filhas f2 where f1.id = 1 and f2.id = 1"
        val list = em.createQuery(query, Pai::class.java).resultList
        assertThat(list).hasSize(1)
        assertThat(list[0].filhos).hasSize(1)
        assertThat(list[0].filhas).hasSize(1)
        assertThat(list[0].filhos.iterator().next().id).isEqualTo(1)
        assertThat(list[0].filhas.iterator().next().id).isEqualTo(1)
    }

    @Test
    fun `pai um filho e duas filha`() {
        val query = "select distinct p from Pai p " +
                "join fetch p.filhos f1 " +
                "join fetch p.filhas f2 " +
                "where f1.id = 1 and f2.id in (1,2)"
        val list = em.createQuery(query, Pai::class.java).resultList
        assertThat(list).hasSize(1)
        assertThat(list[0].filhos).hasSize(1)
        assertThat(list[0].filhas).hasSize(2)
        assertThat(list[0].filhos.iterator().next().id).isEqualTo(1)
        assertThat(list[0].filhas.map { it.id }).containsAll(listOf(1L, 2L))
    }

    private fun pai(): Pai {
        val pai = Pai(id = 1, nome = "Um Pai")
        pai.filhos = mutableSetOf(
                Filho(nome = "Filho 1", id = 1, pai = pai),
                Filho(nome = "Filho 2", id = 2, pai = pai)
        )
        pai.filhas = mutableSetOf(
                Filha(nome = "Filha 1", id = 1, pai = pai),
                Filha(nome = "Filha 2", id = 2, pai = pai),
                Filha(nome = "Filha 3", id = 3, pai = pai)
        )
        return pai
    }

    private fun persistir(pai: Pai) {
        em.persist(pai)
        em.flush()
        em.clear()
    }

}