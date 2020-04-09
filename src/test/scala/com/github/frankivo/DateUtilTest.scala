package com.github.frankivo

import java.time.LocalDate

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
    d.getTime should be(1585951200000L)
    d.addSeconds(60)

    d.getTime should be(1585951200060L)
  }
}
