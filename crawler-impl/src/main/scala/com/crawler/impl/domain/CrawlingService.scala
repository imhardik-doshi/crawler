package com.crawler.impl.domain

import cats.implicits._
import com.crawler.api.response.CrawlerData
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class CrawlingService(implicit ec: ExecutionContext) {

  val log = LoggerFactory.getLogger(classOf[CrawlingService])

  def crawl(url: String): Future[Either[String, CrawlerData]] = Future {
    log.info(s"Class: CrawlingService, Method: crawl, Params: (url = $url)")
    Try(CrawlerData(url, Jsoup.connect(url).get().body().text())).toEither.left.map(err => err.getMessage)
  }

  def crawlUrls(urls: List[String]): Future[Either[String, List[CrawlerData]]] = {
    log.info(s"Class: CrawlingService, Method: crawlUrls, Params: (urls = $urls)")
    for {
      listEitherCrawlerData <- Future.traverse(urls)(crawl)
      listCrawlData = listEitherCrawlerData.traverse(identity)
    } yield listCrawlData
  }
}
