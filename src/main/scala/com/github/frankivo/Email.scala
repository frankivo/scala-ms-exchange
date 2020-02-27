package com.github.frankivo

import java.nio.file.Paths

import microsoft.exchange.webservices.data.core.PropertySet
import microsoft.exchange.webservices.data.core.enumeration.service.DeleteMode
import microsoft.exchange.webservices.data.core.service.item.Item
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema
import microsoft.exchange.webservices.data.property.complex.FileAttachment

import scala.collection.JavaConverters._

object Email {

  implicit class Actions(item: Item) {
    /**
     * Permanently deletes mail from server.
     */
    def delete(): Unit = item.delete(DeleteMode.HardDelete)

    /**
     * Stores all attachments to disk.
     * @param path File store-location.
     * @return List of stored files.
     */
    def storeAttachments(path: String): List[String] = {
      val extended = Item.bind(item.getService, item.getId, new PropertySet(ItemSchema.Attachments))
      extended
        .getAttachments
        .asScala
        .toList
        .map(a => a.asInstanceOf[FileAttachment])
        .map(a => {
          val path = Paths.get(path, a.getName)
          a.load(path.toString)
          path.toString
        })
    }
  }

}
