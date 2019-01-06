package com.nfrpaiva.taime.dominio.test.mtv

import org.junit.Test
import java.io.File

class FileTest {

    @Test
    fun openFileTest() {
        File("c:\\tmp\\teste.txt").forEachLine { println(it) }
    }
}