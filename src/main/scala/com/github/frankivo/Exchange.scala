package com.github.frankivo

import java.net.URI

import microsoft.exchange.webservices.data.core.ExchangeService
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName
import microsoft.exchange.webservices.data.core.service.folder.Folder
import microsoft.exchange.webservices.data.core.service.item.Item
import microsoft.exchange.webservices.data.credential.WebCredentials
import microsoft.exchange.webservices.data.search.ItemView

import scala.collection.JavaConverters._

class Exchange(user: String, pass: String, uri: URI) {

  private def getService: ExchangeService = {
    val service = new ExchangeService()
    service.setCredentials(new WebCredentials(user, pass))
    service.setUrl(uri)
    service
  }

  private def getInbox: Folder = Folder.bind(getService, WellKnownFolderName.Inbox)

  def getInboxItems: List[Item] = {
    val inbox = getInbox

    getService
      .findItems(inbox.getId, new ItemView(inbox.getTotalCount))
      .getItems
      .asScala
      .toList
  }
}
