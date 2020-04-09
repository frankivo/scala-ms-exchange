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
      mockMail(subject = "Mail 1", ageInHours = Some(1)),
      mockMail(subject = "Mail 2", ageInHours = Some(3)),
      mockMail(subject = "Mail 3", ageInHours = Some(5)),
      mockMail(subject = "Mail 4", ageInHours = Some(7))
    ).maxAge(4)

    mails.length should be(2)
  }

  def mockMail(subject: String, ageInHours: Option[Int] = None): Item = {
    val mail = mock[Item]

    when(mail.getSubject).thenReturn(subject)
    ageInHours.foreach(hours => {
      val date = Date.from(Instant.now.minus(hours, ChronoUnit.HOURS))
      when(mail.getDateTimeReceived).thenReturn(date)
    })

    mail
  }
}
