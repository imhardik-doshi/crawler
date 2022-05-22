import akka.actor.ActorSystem
import cats.data.EitherT
import cats.implicits.catsStdInstancesForFuture
import com.crawler.api.response.CrawlerData
import com.crawler.impl.domain.CrawlingService
import com.softwaremill.macwire.wire
import org.scalatest.funsuite.AsyncFunSuite

class CrawlingServiceTest extends AsyncFunSuite {

  val crawlingService      = wire[CrawlingService]
  implicit val actorSystem = ActorSystem("CrawlerServiceIntegrationTest")
  implicit val ec          = actorSystem.dispatcher

  test("Correct urls list should return data") {
    val urls = List("https://www.google.com/", "https://www.yahoo.com/")
    val expectedOutput = List(
      CrawlerData(
        "https://www.google.com/",
        "Gmail Images Sign in Remove Report inappropriate predictions Google offered in: हिन्दी বাংলা తెలుగు मराठी தமிழ் ગુજરાતી ಕನ್ನಡ മലയാളം ਪੰਜਾਬੀ India AboutAdvertisingBusiness How Search works PrivacyTerms Settings Search settings Advanced search Your data in Search Search history Search help Send feedback Dark theme: Off Google apps"
      ),
      CrawlerData(
        "https://www.yahoo.com/",
        "Sign In Search query Search Waiting for permission Allow microphone access to enable voice search COVID-19 Updates IPL News Finance Weather As of August 26th, 2021 Yahoo India will no longer be publishing content. Your Yahoo Account, Mail and Search experiences will not be affected in any way and will operate as usual. We thank you for your support and readership. For more information on Yahoo India, please visit the FAQ. Trending Now 1 COVID-19 in India 2 Nirmala Sitharaman 3 Om Prakash Chautala 4 Navjot Singh Sidhu 5 Rabri Devi 6 Jobs Near Me 7 Imran Khan 8 Cannes 2022 9 Priyanka Chopra Jonas 10 IPL 2022 Yahoo Help Privacy(Updated) Terms(Updated) Advertise"
      )
    )
    val resultET = EitherT(crawlingService.crawlUrls(urls))

    val result = resultET.fold(err => err, (value: List[CrawlerData]) => value)

    result.map { s => assert(s === expectedOutput) }

  }

  test("Error should generated for wrong url") {

    val urls = List("wrong url")
    val expectedOutput = "Malformed URL: wrong url"

    val resultET = EitherT(crawlingService.crawlUrls(urls))

    val result = resultET.fold(err => err, (value: List[CrawlerData]) => value)

    result.map { s => assert(s === expectedOutput) }

  }

}
