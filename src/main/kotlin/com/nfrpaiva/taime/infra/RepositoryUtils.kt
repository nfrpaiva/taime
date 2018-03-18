package com.nfrpaiva.taime.infra

import com.nfrpaiva.taime.exception.TaimeException
import java.util.*

fun <T> Optional<T>.returnOrThrow(message: String): T {
    return if (this.isPresent) this.get() else  throw TaimeException (message)
}