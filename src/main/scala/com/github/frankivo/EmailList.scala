package com.github.frankivo

import java.time.{Instant, Period}
import java.util.Date

import microsoft.exchange.webservices.data.core.service.item.Item

object EmailList {
  implicit class Filters(items: List[Item]) {

    def subjectContains(subject: String): List[Item] = items.filter(_.getSubject.contains(subject))

    def maxAge(hours: Int): List[Item] = {
      val maxAge = Date.from(Instant.now.minusSeconds(hours * 60))
      items.filter(_.getDateTimeReceived.after(maxAge))
    }

    def minAge(days: Int): List[Item] = {
      val minAge = Date.from(Instant.now.minus(Period.ofDays(days)))
      items.filter(_.getDateTimeReceived.before(minAge))
    }

    def hasAttachment: List[Item] = items.filter(_.getHasAttachments)
  }
}
