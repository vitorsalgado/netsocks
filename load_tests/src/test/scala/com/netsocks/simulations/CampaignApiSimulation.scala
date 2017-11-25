package com.netsocks.simulations

import java.util.UUID

import com.netsocks.simulations.config.ConfigProvider
import io.gatling.core.Predef._
import io.gatling.core.body.StringBody
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class CampaignApiSimulation extends Simulation {
  val httpConf: HttpProtocolBuilder = http
    .baseURL(ConfigProvider.CAMPAIGN_URI)
    .acceptHeader(MediaTypes.APPLICATION_JSON)
    .contentTypeHeader(MediaTypes.APPLICATION_JSON)

  val campaignsInteraction: ScenarioBuilder =
    scenario("Campaign Add + Fetch")
      .exec(
        http("Add Campaigns")
          .post("/campaigns")
          .body(StringBody(session =>
            """{ "name": """" + UUID.randomUUID().toString + """", "favoriteTeamId": "SEP", "validityStart": "2017-12-01", "validityEnd": "2017-12-03" }""")
          ).asJSON
      )
      .pause(2)

      .exec(
        http("Get Campaigns")
          .get("/campaigns")
      )
      .pause(2)

  setUp(
    campaignsInteraction.inject(
      rampUsers(100) over 30,
      atOnceUsers(100)
    ))
    .protocols(httpConf)
}
