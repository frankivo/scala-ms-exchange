package com.github.frankivo

import java.time.{Instant, LocalDate}
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
      mockMail(subject = "Mail 1"),
      mockMail(subject = "Mail 2"),
      mockMail(subject = "Mail 3"),
      mockMail(subject = "Mail 4")
    ).subjectContains("Mail 3")

    mails.length should be(1)
    mails.head.getSubject should be("Mail 3")
  }

  "subject filter" should "not find an non-existing subject" in {
    val mails = List[Item](
      mockMail(subject = "Mail 1"),
      mockMail(subject = "Mail 2"),
      mockMail(subject = "Mail 3"),
      mockMail(subject = "Mail 4")
    ).subjectContains("Fake mail")

    mails.length should be(0)
  }

  "maxAge filter" should "find mails within range" in {
    val mails = List[Item](
      mockMail(subject = "Mail 1", date = Date.from(Instant.now.minus(1, ChronoUnit.HOURS))),
      mockMail(subject = "Mail 2", date = Date.from(Instant.now.minus(3, ChronoUnit.HOURS))),
      mockMail(subject = "Mail 3", date = Date.from(Instant.now.minus(5, ChronoUnit.HOURS))),
      mockMail(subject = "Mail 4", date = Date.from(Instant.now.minus(7, ChronoUnit.HOURS)))
    ).maxAge(4)

    mails.length should be(2)
  }

  "minAge filter" should "find mails within range" in {
    val mails = List[Item](
      mockMail(subject = "Mail 1", date = Date.from(Instant.now.minus(1, ChronoUnit.DAYS))),
      mockMail(subject = "Mail 2", date = Date.from(Instant.now.minus(10, ChronoUnit.DAYS))),
      mockMail(subject = "Mail 3", date = Date.from(Instant.now.minus(15, ChronoUnit.DAYS))),
      mockMail(subject = "Mail 4", date = Date.from(Instant.now.minus(30, ChronoUnit.DAYS)))
    ).minAge(14)

    mails.length should be(2)
  }

  "date filter" should "find mails on specific date" in {
    val mails = List[Item](
      mockMail(subject = "Mail 1", date = fromLocalDate(LocalDate.parse("2019-04-09"))),
      mockMail(subject = "Mail 2", date = fromLocalDate(LocalDate.parse("2020-04-08"))),
      mockMail(subject = "Mail 3", date = fromLocalDate(LocalDate.parse("2020-04-09"))),
      mockMail(subject = "Mail 4", date = fromLocalDate(LocalDate.parse("2020-04-10"))),
      mockMail(subject = "Mail 5", date = fromLocalDate(LocalDate.parse("2020-04-09")))
    ).onDate(LocalDate.parse("2020-04-09"))

    mails.length should be(2)
  }

  def fromLocalDate(dateToConvert: LocalDate): Date = java.sql.Date.valueOf(dateToConvert)

  "attachment filter" should "find mails with attachments" in {
    val mails = List[Item](
      mockMail(subject = "Mail 1", hasAttachment = true),
      mockMail(subject = "Mail 2", hasAttachment = true),
      mockMail(subject = "Mail 3", hasAttachment = false),
      mockMail(subject = "Mail 4", hasAttachment = true)
    ).hasAttachment

    mails.length should be(3)
  }

  def mockMail(subject: String, date: Date = new Date, hasAttachment: Boolean = false): Item = {
    val mail = mock[Item]

    when(mail.getSubject).thenReturn(subject)
    when(mail.getDateTimeReceived).thenReturn(date)
    when(mail.getHasAttachments).thenReturn(hasAttachment)

    mail
  }

}
