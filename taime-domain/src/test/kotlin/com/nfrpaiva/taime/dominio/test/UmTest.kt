package com.nfrpaiva.taime.dominio.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

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

    fun <T> String.map(mapx: (String) -> T): T {
        return mapx(this)
    }

    @Test
    fun stringMap() {
        assertThat(Objeto("xx")).isEqualTo("xx".map { Objeto(it) })
    }

    @Test
    fun printStringAsMap() {
        assertThat('x').isEqualTo("xx"[0])
    }

    fun html(init: HTML.() -> Unit): HTML {
        val html = HTML()
        html.init()
        return html
    }

    @Test
    fun x (){
        val html = html {

        }
        println(html)
    }
}
class HTML (){

}
data class Objeto(val nome: String)