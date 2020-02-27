package com.github.frankivo

import microsoft.exchange.webservices.data.core.enumeration.service.DeleteMode
import microsoft.exchange.webservices.data.core.service.item.Item

object Email {

  implicit class Actions(item: Item) {
    def delete(): Unit = item.delete(DeleteMode.HardDelete)
  }
}
