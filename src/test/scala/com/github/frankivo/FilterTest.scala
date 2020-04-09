package com.github.frankivo

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

import com.github.frankivo.EmailList.Filters
import microsoft.exchange.webservices.data.core.service.item.Item
import org.mockito.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FilterTest extends AnyFlatSpec with MockitoSugar with Matchers {

  "subject filter" should "find an existing subject" in {
    val mails = List[Item](
      mockMail("Mail 1"),
      mockMail("Mail 2"),
      mockMail("Mail 3"),
      mockMail("Mail 4")
    ).subjectContains("Mail 3")

    mails.length should be(1)
    mails.head.getSubject should be("Mail 3")
  }

  "subject filter" should "not find an non-existing subject" in {
    val mails = List[Item](
      mockMail("Mail 1"),
      mockMail("Mail 2"),
      mockMail("Mail 3"),
      mockMail("Mail 4")
    ).subjectContains("Fake mail")

    mails.length should be(0)
  }

  "maxAge filter" should "find mails within range" in {
    val mails = List[Item](
      mockMail("Mail 1", Date.from(Instant.now.minus(1, ChronoUnit.HOURS))),
      mockMail("Mail 2", Date.from(Instant.now.minus(3, ChronoUnit.HOURS))),
      mockMail("Mail 3", Date.from(Instant.now.minus(5, ChronoUnit.HOURS))),
      mockMail("Mail 4", Date.from(Instant.now.minus(7, ChronoUnit.HOURS)))
    ).maxAge(4)

    mails.length should be(2)
  }

  "minAge filter" should "find mails within range" in {
    val mails = List[Item](
      mockMail("Mail 1", Date.from(Instant.now.minus(1, ChronoUnit.DAYS))),
      mockMail("Mail 2", Date.from(Instant.now.minus(10, ChronoUnit.DAYS))),
      mockMail("Mail 3", Date.from(Instant.now.minus(15, ChronoUnit.DAYS))),
      mockMail("Mail 4", Date.from(Instant.now.minus(30, ChronoUnit.DAYS)))
    ).minAge(14)

    mails.length should be(2)
  }

  def mockMail(subject: String, date: Date = new Date): Item = {
    val mail = mock[Item]

    when(mail.getSubject).thenReturn(subject)
    when(mail.getDateTimeReceived).thenReturn(date)

    mail
  }

}
