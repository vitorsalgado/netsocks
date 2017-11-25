package com.netsocks.simulations

import java.util.UUID

import com.netsocks.simulations.config.ConfigProvider
import io.gatling.core.Predef._
import io.gatling.core.body.StringBody
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class FanApiSimulation extends Simulation {
  val httpConf: HttpProtocolBuilder = http
    .baseURL(ConfigProvider.FAN_URI)
    .acceptHeader(MediaTypes.APPLICATION_JSON)
    .contentTypeHeader(MediaTypes.APPLICATION_JSON)

  val fanInteraction: ScenarioBuilder =
    scenario("Fan Registration")
      .exec(
        http("Register")
          .post("/customers")
          .body(StringBody(session =>
            """{ "fullName": """" + UUID.randomUUID().toString + """", "email": """" + UUID.randomUUID().toString + """@teste.com.br", "favoriteTeam": "SEP", "birthday": "1988-05-09" }""")
          ).asJSON
      )
      .pause(2)

  setUp(
    fanInteraction.inject(
      rampUsers(100) over 30,
      atOnceUsers(100)
    ))
    .protocols(httpConf)
}
