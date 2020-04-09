package com.github.frankivo

import java.time.LocalDate
import java.util.Date

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DateUtilTest extends AnyFlatSpec with Matchers {

  import DateUtil.LocalDateUtil

  "LocalDate" should "convert to Date" in {
    val str = "2020-04-01"

    val ld = LocalDate.parse(str)
    val d = ld.toDate

    d.toString should be(str)
  }

  "Date" should "add 60 seconds" in {
    import DateUtil.{DateUtil, LocalDateUtil}
    val d = LocalDate.parse("2020-04-04").toDate
    val t1 = d.getTime
    d.addSeconds(60)
    val t2 = d.getTime

    t2 - t1 should be(60L)
  }

  "Date" should "convert to LocalDate" in {
    import DateUtil.DateUtil

    val d = new Date(2020 - 1900, 3, 14)
    val ld = LocalDate.parse("2020-04-14")

    d.toLocalDate.equals(ld) should be(true)
  }
}
