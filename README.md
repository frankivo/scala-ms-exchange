# scala-ms-exchange
Scala wrapper for microsoft.exchange.webservices

Easy to use.

For example, this is how you delete all mails with subject "test" and older than 30 days:
```scala

import java.net.URI

import com.github.frankivo.Email.Actions
import com.github.frankivo.EmailList.Filters
import com.github.frankivo.Exchange

val server = new Exchange("user@github.com", "pass", new URI("https://server/EWS/Exchange.asmx"))
server
  .getInboxItems
  .minAge(30)
  .foreach(_.delete())
```