package com.nfrpaiva.taime.dominio.test.umparamuitos

import com.nfrpaiva.taime.dominio.test.muitosparamuitos.UmTesteComEntidades
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.annotation.Commit
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.*

@RunWith(SpringRunner::class)
@DataJpaTest
@Commit
@ComponentScan(basePackageClasses = [QueryUmParaMuitosTest::class])
@EnableJpaRepositories(basePackageClasses = [QueryUmParaMuitosTest::class])
@EntityScan(basePackageClasses = [QueryUmParaMuitosTest::class])
class QueryUmParaMuitosTest {

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
        val list1 = em.createQuery("select p from Pai p", Pai::class.java).resultList
        assertThat(list1).hasSize(1)
    }

    @Test
    fun `pai e filho com id 1`() {
        val list2 = em.createQuery("select p from Pai p join fetch p.filhos f where f.id =  1", Pai::class.java).resultList
        assertThat(list2).hasSize(1)
        assertThat(list2[0].filhos).hasSize(1)
        assertThat(list2[0].filhos.iterator().next().id).isEqualTo(1)
    }

    @Test
    fun `pai e filho e filha com id 1`() {
        val list2 = em.createQuery("select distinct p from Pai p " +
                "join fetch p.filhos f join fetch p.filhas fa where f.id =  1 and fa.id <=2", Pai::class.java).resultList
        assertThat(list2).hasSize(1)
        assertThat(list2[0].filhos).hasSize(1)
        assertThat(list2[0].filhas).hasSize(2)
        assertThat(list2[0].filhos.iterator().next().id).isEqualTo(1)

    }

    @Test
    fun `pai e  filho com id 2`() {
        val list3 = em.createQuery("select p from Pai p join fetch p.filhos f where f.id =  2", Pai::class.java).resultList
        assertThat(list3).hasSize(1)
        assertThat(list3[0].filhos).hasSize(1)
        assertThat(list3[0].filhos.iterator().next().id).isEqualTo(2)
    }

    @Test
    fun `pai e nenhum filho com id 3`() {
        val list4 = em.createQuery("select p from Pai p join fetch p.filhos f where f.id =  3", Pai::class.java).resultList
        assertThat(list4).hasSize(0)
    }

    @Test
    fun `pai e todos os seus filhos`() {
        val list5 = em.createQuery("select p from Pai p join p.filhos f where f.id =  1", Pai::class.java).resultList
        assertThat(list5).hasSize(1)
        assertThat(list5[0].filhos).hasSize(2)
    }

    private fun pai(): Pai {
        val pai = Pai(id = 1, nome = "Um Pai")
        pai.filhos = mutableSetOf(Filho(nome = "Filho 1", id = 1, pai = pai), Filho(nome = "Filho 2", id = 2, pai = pai))
        pai.filhas = mutableSetOf(Filha(nome = "Filha 1", id = 1, pai = pai), Filha(nome = "Filha 2", id = 2, pai = pai), Filha(nome = "Filha 3", id = 3, pai = pai))
        return pai
    }

    private fun persistir(pai: Pai) {
        em.persist(pai)
        em.flush()
        em.clear()
    }

}

@Entity
data class Pai(@Id var id: Long, var nome: String) {
    @OneToMany(mappedBy = "pai", cascade = [CascadeType.ALL], orphanRemoval = true)
    var filhos: MutableSet<Filho> = mutableSetOf()
    @OneToMany(mappedBy = "pai", cascade = [CascadeType.ALL], orphanRemoval = true)
    var filhas: MutableSet<Filha> = mutableSetOf()
}

@Entity
data class Filho(@Id var id: Long, var nome: String, @ManyToOne var pai: Pai)

@Entity
data class Filha(@Id var id: Long, var nome: String, @ManyToOne var pai: Pai)