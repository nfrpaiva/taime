package com.nfrpaiva.taime.dominio.test.mtv

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RangeTest {

    @Test
    fun stepTest() {
        var range = 2.rangeTo(20).step(2)
        var lastNumber = 0
        range.forEach {
            assertThat(it % 2).isEqualTo(0)
            lastNumber = it
        }
        assertThat(lastNumber).isEqualTo(20)
    }

    @Test
    fun lastOfStep() {
        val range = 1.rangeTo(10)
        assertThat(range.last).isEqualTo(10)
        assertThat(range.first).isEqualTo(1)
    }

}