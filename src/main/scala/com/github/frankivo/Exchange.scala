package com.github.frankivo

import java.net.URI

import microsoft.exchange.webservices.data.core.ExchangeService
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName
import microsoft.exchange.webservices.data.core.service.folder.Folder
import microsoft.exchange.webservices.data.core.service.item.{EmailMessage, Item}
import microsoft.exchange.webservices.data.credential.WebCredentials
import microsoft.exchange.webservices.data.property.complex.MessageBody
import microsoft.exchange.webservices.data.search.ItemView

/**
 * Sets up a connection to MS Exchange.
 *
 * @param user Username.
 * @param pass Password.
 * @param uri  Connection URL.
 */
class Exchange(user: String, pass: String, uri: URI) {

  /**
   * Sets up a connection to MS Exchange.
   *
   * @param user Username.
   * @param pass Password.
   * @param uri  Connection URL.
   */
  def this(user: String, pass: String, uri: String) = this(user, pass, new URI(uri))

  /**
   * Connects to the Exchange Server.
   *
   * @return Connection instance.
   */
  private def getService: ExchangeService = {
    val service = new ExchangeService()
    service.setCredentials(new WebCredentials(user, pass))
    service.setUrl(uri)
    service
  }

  /**
   * Gets the Inbox.
   *
   * @return Inbox Folder.
   */
  private def getInbox: Folder = Folder.bind(getService, WellKnownFolderName.Inbox)

  /**
   * Get all e-mails from the inbox.
   *
   * @return List of e-mails.
   */
  def getInboxItems: List[Item] = {
    val inbox = getInbox

    getService
      .findItems(inbox.getId, new ItemView(inbox.getTotalCount))
      .getItems
      .toArray
      .toList
      .asInstanceOf[List[Item]]
  }

  /**
   * Send an e-mail. The message will not be stored in Send folder.
   *
   * @param to         Recipient (e-mail address).
   * @param subject    E-mail subject.
   * @param body       E-mail content.
   * @param attachment Optional attachment path.
   */
  def sendMail(to: String, subject: String, body: String, attachment: Option[String] = None): Unit = {
    val msg = new EmailMessage(getService)

    msg.getToRecipients.add(to)
    msg.setSubject(subject)
    msg.setBody(MessageBody.getMessageBodyFromText(body))
    attachment.foreach(msg.getAttachments.addFileAttachment(_))

    msg.send()
  }

  /**
   * Send an e-mail. The message will not be stored in Send folder.
   *
   * @param to         Recipient (e-mail address).
   * @param subject    E-mail subject.
   * @param body       E-mail content.
   * @param attachment Attachment path.
   */
  def sendMail(to: String, subject: String, body: String, attachment: String): Unit = {
    sendMail(to, subject, body, Some(attachment))
  }
}
