package com.nfrpaiva.taime.dominio.test.byte

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ByteTest {
    @Test
    fun isByte() {
        assertThat(Byte(1.0).value).isEqualTo(1.0)
    }

    @Test
    fun isOneMegaByte() {
        assertThat(Byte(1024.0).megaByte()).isEqualTo(MegaByte(1.0))
    }

    @Test
    fun isOneGigaByte() {
        assertThat(Byte(Math.pow(2.0, 32.0)).gigaByte()).isEqualTo(4.0)
    }
}

data class GigaByte(val value: Double)

data class MegaByte(val value: Double)

data class Byte(val value: Double) {
    fun megaByte(): MegaByte {
        return MegaByte(value / 1024)
    }

    fun gigaByte(): GigaByte {
        return GigaByte(megaByte().value / 1024)
    }
}