package com.github.frankivo

import java.sql.{Date => SqlDate}
import java.time.LocalDate
import java.util.Date

/**
 * Helper methods for LocalDate.
 */
object DateUtil {

  /**
   * Adds methods to LocalDate.
   *
   * @param date LocalDate to work with.
   */
  implicit class LocalDateUtil(date: LocalDate) {
    /**
     * Converts java.time.LocalDate to java.util.Date (via java.sql.Date).
     *
     * @return Instance of java.util.Date
     */
    def toDate: Date = SqlDate.valueOf(date)
  }

  /**
   * Adds methods to java.util.Date
   *
   * @param date Date to work with.
   */
  implicit class DateUtil(date: Date) {
    /**
     * Adds seconds to java.util.Date
     *
     * @param seconds Amount of seconds to add.
     */
    def addSeconds(seconds: Long): Date = {
      date.setTime(date.getTime + seconds)
      date
    }
  }

}
