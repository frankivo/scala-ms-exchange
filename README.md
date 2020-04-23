# scala-ms-exchange
Scala wrapper for microsoft.exchange.webservices

Easy to use.

For example, this is how you delete all mails with subject "test" and older than 30 days:
```scala
import com.github.frankivo.Email.Actions
import com.github.frankivo.EmailList.Filters
import com.github.frankivo.Exchange

new Exchange("user@github.com", "pass", "https://server/EWS/Exchange.asmx")
      .getInboxItems
      .withSubject("test")
      .minAge(30)
      .foreach(_.delete())
```