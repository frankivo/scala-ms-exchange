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
   * @param date LocalDate to convert.
   */
  implicit class Convert(date: LocalDate) {
    /**
     * Converts LocalDate to Date (via java.sql.Date).
     *
     * @return Instance of java.util.Date
     */
    def toDate: Date = SqlDate.valueOf(date)
  }

}
