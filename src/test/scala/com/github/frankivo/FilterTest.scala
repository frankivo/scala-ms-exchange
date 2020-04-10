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

  "subject filter" should "find an existing exact subject" in {
    val mails = List[Item](
      mockMail(subject = "Lorem ipsum dolor sit amet"),
      mockMail(subject = "Quisque dapibus commodo velit"),
      mockMail(subject = "Integer molestie justo sed libero"),
      mockMail(subject = "Cras tristique sagittis nunc ac"),
      mockMail(subject = "Quisque dapibus molestie velit")
    ).subjectContains("Integer molestie justo sed libero")

    mails.length should be(1)
    mails.head.getSubject should be("Integer molestie justo sed libero")
  }

  "subject filter" should "find an existing partial subject" in {
    val mails = List[Item](
      mockMail(subject = "Lorem ipsum dolor sit amet"),
      mockMail(subject = "Quisque dapibus commodo velit"),
      mockMail(subject = "Integer molestie justo sed libero"),
      mockMail(subject = "Cras tristique sagittis nunc ac"),
      mockMail(subject = "Quisque dapibus molestie velit")
    ).subjectContains("molestie")

    mails.length should be(2)
    mails(n = 0).getSubject should be("Integer molestie justo sed libero")
    mails(n = 1).getSubject should be("Quisque dapibus molestie velit")
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
    import DateUtil.{LocalDateUtil, DateUtil}

    val mails = List[Item](
      mockMail(subject = "Mail 1", date = LocalDate.parse("2019-04-09").toDate.addSeconds(60)),
      mockMail(subject = "Mail 2", date = LocalDate.parse("2020-04-08").toDate.addSeconds(60)),
      mockMail(subject = "Mail 3", date = LocalDate.parse("2020-04-09").toDate.addSeconds(60)),
      mockMail(subject = "Mail 4", date = LocalDate.parse("2020-04-10").toDate.addSeconds(60)),
      mockMail(subject = "Mail 5", date = LocalDate.parse("2020-04-09").toDate.addSeconds(60))
    ).onDate(LocalDate.parse("2020-04-09"))

    mails.length should be(2)
  }

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
