package com.github.frankivo

import java.nio.file.Paths

import microsoft.exchange.webservices.data.core.PropertySet
import microsoft.exchange.webservices.data.core.enumeration.service.DeleteMode
import microsoft.exchange.webservices.data.core.service.item.Item
import microsoft.exchange.webservices.data.core.service.schema.ItemSchema
import microsoft.exchange.webservices.data.property.complex.FileAttachment

/**
 * Helper methods for Item.
 */
object Email {

  /**
   * Adds methods to Item.
   *
   * @param item An e-mail.
   */
  implicit class Actions(item: Item) {
    /**
     * Permanently deletes mail from server.
     */
    def delete(): Unit = item.delete(DeleteMode.HardDelete)

    /**
     * Stores all attachments to disk.
     *
     * @param path File store-location.
     * @return List of stored files.
     */
    def storeAttachments(path: String): List[String] = {
      val extended = Item.bind(item.getService, item.getId, new PropertySet(ItemSchema.Attachments))

      extended
        .getAttachments
        .getItems
        .toArray
        .toList
        .asInstanceOf[List[FileAttachment]]
        .map(a => {
          val fullFilename = Paths.get(path, a.getName)
          a.load(fullFilename.toString)
          fullFilename.toString
        })
    }
  }

}
