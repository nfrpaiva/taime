package com.nfrpaiva.taime.dominio

import java.awt.datatransfer.ClipboardOwner
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class OwnerAtributesEntity (open var owner: String ="") {

}
