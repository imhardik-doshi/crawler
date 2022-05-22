package com.crawler.api.request

import play.api.libs.json.{Format, Json}

case class CrawlerRequest(urls: List[String])

object CrawlerRequest {
  implicit lazy val format: Format[CrawlerRequest] = Json.format
}
