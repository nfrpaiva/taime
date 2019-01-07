package com.nfrpaiva.taime.dominio.test.lang

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LangTest {

    @Test
    fun forInRangeTest() {
        for (i in 0..100 step 2) {
            assertThat(i % 2).isEqualTo(0)
        }
        for (i in 1..100 step 2) {
            assertThat(i % 2).isNotEqualTo(0)
        }
    }

    @Test
    fun rangeInCollections() {
        0.rangeTo(100).step(2).forEach {
            assertThat(it % 2).isEqualTo(0)
        }
        1.rangeTo(100).step(2).forEach {
            assertThat(it % 2).isNotEqualTo(0)
        }
    }

}