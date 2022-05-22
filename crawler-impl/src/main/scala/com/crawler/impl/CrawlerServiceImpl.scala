package com.crawler.impl

import com.crawler.api.CrawlerService
import com.crawler.api.request.CrawlerRequest
import com.crawler.api.response.{CrawlerData, CrawlerResponse}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}

class CrawlerServiceImpl(implicit executionContext: ExecutionContext) extends CrawlerService {

  private val log = LoggerFactory.getLogger(classOf[CrawlerServiceImpl])

  override def crawl: ServiceCall[CrawlerRequest, CrawlerResponse] = ServiceCall{
    crawlerRequest => {
      log.info(s"In crawl $crawlerRequest")
      val d1 = CrawlerData("u1", "d1")
      val d2 = CrawlerData("u1", "d2")
      Future.successful(CrawlerResponse(List(d1, d2)))
    }
  }
}
