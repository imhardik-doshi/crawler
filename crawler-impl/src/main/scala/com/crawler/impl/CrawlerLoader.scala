package com.crawler.impl


import com.crawler.api.CrawlerService
import com.lightbend.lagom.scaladsl.akka.discovery.AkkaDiscoveryComponents
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

class CrawlerLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): LagomApplication = new CrawlerApplication(context)
    with AkkaDiscoveryComponents

  override def loadDevMode(context: LagomApplicationContext): LagomApplication = new CipherDevApplication(context)
    with LagomDevModeComponents

}

abstract class CipherDevApplication(context: LagomApplicationContext)
    extends LagomApplication(context)
    with AhcWSComponents {
  override lazy val lagomServer: LagomServer                      = serverFor[CrawlerService](wire[CrawlerServiceImpl])
  lazy val jsonSerializerRegistry: CrawlerSerializerRegistry.type = CrawlerSerializerRegistry
}

abstract class CrawlerApplication(context: LagomApplicationContext)
    extends LagomApplication(context)
    with AhcWSComponents {
  override lazy val lagomServer: LagomServer                      = serverFor[CrawlerService](wire[CrawlerServiceImpl])
  lazy val jsonSerializerRegistry: CrawlerSerializerRegistry.type = CrawlerSerializerRegistry
}
