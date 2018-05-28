package com.nfrpaiva.taime.dominio.test

import com.nfrpaiva.taime.dominio.Cliente
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.properties.Delegates

// TODO: Nao faz parte do projeto. é apenas um teste
class Umtest() {

    @Test
    fun test() {
        val o1 = Objeto("eu")
        val o2 = Objeto("eu")
        assertThat(o1).isEqualTo(o2)
    }

    @Test
    fun list() {
        val list = listOf("a", "b")
        assertThat(list.filter { it.equals("a") }.size).isEqualTo(1)
        val map = list.map { Objeto(it) }
        assertThat(map[0]).isEqualTo(Objeto("a"))
    }

    @Test
    fun lambda() {
        val x = listOf(1, 2, 3).fold(1, { a: Int, b: Int ->
            println("$a + $b = ${a + b}")
            a + b
        })
        println("Resultado = $x")

        assertThat(listOf(2, 2).fold(0, Int::plus)).isEqualTo(4)
    }

    @Test
    fun myLambTest() {
        val value = myFun(1, { a -> a * 2 })
        println("value = $value")
    }

    fun myFun(n: Int, foo: (a: Int) -> Int): Int {
        println("n = $n")
        println("foo(n) = ${foo(n)}")
        return foo(n)
    }

    fun <T> String.map(map: (String) -> T): T {
        return map(this)
    }

    @Test
    fun stringMap() {
        assertThat(Objeto("xx")).isEqualTo("xx".map { Objeto(it) })
    }

    @Test
    fun printStringAsMap() {
        assertThat('x').isEqualTo("xx"[0])
    }

    @Test
    fun filterTest() {
        val clientes = mutableListOf<Cliente>()
        val size = 100_000
        for (i in 1..size) {
            clientes.add(Cliente(nome = "Cliente numero $i", id = i.toLong()))
        }
        assertThat(clientes).hasSize(size)
        val halfSize = size / 2
        assertThat(clientes.filter { it.id <= halfSize }).hasSize(halfSize)
    }

    @Test
    fun testDelegate() {
        val o = Objeto("")
        o.observableProperty = "Fernandinho"
        o.observableProperty = "Fernandinho Segundo"
    }

    @Test
    fun testLazyProperty() {
        val o = Objeto("")
        println(o.lazyProperty)
        println(o.lazyProperty)
    }

    @Test
    fun testNotNullProperty() {
        val o = Objeto("")
        o.notNullProperty = "notNullProperty val"
        println(o.notNullProperty)
    }

}

data class Objeto(val nome: String) {
    var observableProperty: String by Delegates.observable("no observableProperty") { d, old, new ->
        println("$old - $new")
        println(d)
    }
    val lazyProperty: String by lazy {
        println("Storing lazyProperty")
        "Value of Lazy Property"
    }
    var notNullProperty: String by Delegates.notNull()
}

