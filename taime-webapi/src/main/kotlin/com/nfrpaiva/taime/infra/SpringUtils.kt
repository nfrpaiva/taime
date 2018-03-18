package com.nfrpaiva.taime.infra

import org.springframework.http.ResponseEntity
import java.util.*

fun <T> Optional<T>.defaultOrNotFound(): ResponseEntity<T> {
    return if (this.isPresent) ResponseEntity.ok(this.get()) else ResponseEntity.notFound().build<T>()
}
fun <T> List<T>.responseOK(): ResponseEntity<List<T>> {
    return ResponseEntity.ok(this);
}