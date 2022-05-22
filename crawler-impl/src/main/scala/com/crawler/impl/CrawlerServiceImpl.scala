package com.crawler.impl

import com.crawler.api.CrawlerService
import com.crawler.api.request.CrawlerRequest
import com.crawler.api.response.CrawlerData
import com.crawler.api.response.CrawlerResponse
import com.crawler.impl.domain.CrawlingService
import com.lightbend.lagom.scaladsl.api.ServiceCall
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class CrawlerServiceImpl(crawlingService: CrawlingService)(implicit executionContext: ExecutionContext)
    extends CrawlerService {

  private val log = LoggerFactory.getLogger(classOf[CrawlerServiceImpl])

  override def crawl: ServiceCall[CrawlerRequest, CrawlerResponse] = ServiceCall { crawlerRequest =>
    {
      log.info(s"Class: CrawlerServiceImpl, Method: crawl, Request: (crawlerRequest = $crawlerRequest)")
      log.info(s"crawl: crawlerRequest($crawlerRequest)")
      val result = crawlingService.crawlUrls(crawlerRequest.urls)
      result.map {
        case Left(err) => {
          println(s"err ====== $err")
          CrawlerResponse(Nil, err)
        }
        case Right(value) => CrawlerResponse(value, null)
      }
    }
  }
}
