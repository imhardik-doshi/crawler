package com.crawler.impl

import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry

object CrawlerSerializerRegistry extends JsonSerializerRegistry {
  override val serializers = Vector()
}
