package com.nfrpaiva.taime.dominio.test

import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.File

class FileTest {

    @Test
    fun readFile() {
        val file = File(this.javaClass.getResource("/import.sql").path)
        Assertions.assertThat(file.exists()).isTrue()
    }
}