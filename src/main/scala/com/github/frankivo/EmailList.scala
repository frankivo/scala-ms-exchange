package com.github.frankivo

import java.time.temporal.ChronoUnit
import java.time.{Instant, LocalDate}
import java.util.Date

import microsoft.exchange.webservices.data.core.service.item.Item

/**
 * Helper methods for List[Item].
 */
object EmailList {

  /**
   * Adds methods to List[Item].
   *
   * @param items List of e-mails.
   */
  implicit class Filters(items: List[Item]) {

    /**
     * Find e-mails by partial subject.
     *
     * @param subject Subject to search for.
     * @return Filtered list of e-mails.
     */
    def subjectContains(subject: String): List[Item] = items.filter(_.getSubject.contains(subject))

    /**
     * Find e-mails by maximum received date.
     *
     * @param hours Maximum age of mails in hours.
     * @return Filtered list of e-mails.
     */
    def maxAge(hours: Int): List[Item] = {
      val maxAge = Date.from(Instant.now.minus(hours, ChronoUnit.HOURS))
      items.filter(_.getDateTimeReceived.after(maxAge))
    }

    /**
     * Find e-mails by minimum received date.
     *
     * @param days Minimum age of mails in days.
     * @return Filtered list of e-mails.
     */
    def minAge(days: Int): List[Item] = {
      val minAge = Date.from(Instant.now.minus(days, ChronoUnit.DAYS))
      items.filter(_.getDateTimeReceived.before(minAge))
    }

    /**
     * Find e-mails that have at least one attachment.
     *
     * @return Filtered list of e-mails.
     */
    def hasAttachment: List[Item] = items.filter(_.getHasAttachments)

    /**
     * Find e-mails that are on a specific date.
     * @param date Date to filter on.
     * @return Filtered list of e-mails.
     */
    def onDate(date: LocalDate): List[Item] = ???
  }

}
