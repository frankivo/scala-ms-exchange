package com.github.frankivo

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
    val mail = List[Item](
      mockMail("Mail 1"),
      mockMail("Mail 2"),
      mockMail("Mail 3"),
      mockMail("Mail 4")
    ).subjectContains("Mail 3")

    mail.length should be(1)
    mail.head.getSubject should be("Mail 3")
  }

  def mockMail(subject: String): Item = {
    val mail = mock[Item]
    when(mail.getSubject).thenReturn(subject)
    mail
  }
}
