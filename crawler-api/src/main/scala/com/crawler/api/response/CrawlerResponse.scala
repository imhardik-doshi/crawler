package com.crawler.api.response

import play.api.libs.json.{Format, Json}

case class CrawlerResponse(result: List[CrawlerData], error: String)

object CrawlerResponse {
  implicit lazy val format: Format[CrawlerResponse] = Json.format
}

case class CrawlerData(url: String, data: String)

object CrawlerData {
  implicit lazy val format: Format[CrawlerData] = Json.format
}
