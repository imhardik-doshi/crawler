package com.crawler.api

import com.crawler.api.request.CrawlerRequest
import com.crawler.api.response.CrawlerResponse
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceAcl, ServiceCall}

trait CrawlerService extends Service {

  def crawl: ServiceCall[CrawlerRequest, CrawlerResponse]

  override def descriptor: Descriptor = {
    import Service._
    named("crawl-data")
      .withCalls(restCall(Method.POST, "/api/crawl", crawl))
      .withAcls(ServiceAcl(pathRegex = Some("/api/crawl")))
  }
}
