/*
 * Copyright 2016-2020 Ivan Krizsan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mak.springbootasynchronousapi.gatling.simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

/**
 * Example Gatling load test simulating a number of users that rises up to 10 users over
 * a period of 20 seconds.
 * Run this simulation with:
 * mvn -Dgatling.simulation.name=HttpSimulationDemo gatling:test
 *
 * @author Raouf Makhlouf
 */
class HttpSimulationDemo extends Simulation {

    val theHttpProtocolBuilder: HttpProtocolBuilder = http

    val theScenarioBuilder: ScenarioBuilder = scenario("Scenario1")
      .exec(http("aot - Reactive").get("http://localhost:8082/product/all"))
      .exec(http("jit - Reactive").get("http://localhost:8083/product/all"))
      .exec(http("aot - Non Reactive").get("http://localhost:8084/product/all"))
      .exec(http("jit - Non Reactive").get("http://localhost:8085/product/all"))
      .exec(http("aot - Non Reactive VT").get("http://localhost:8086/product/all"))
      .exec(http("jit - Non Reactive VT").get("http://localhost:8087/product/all"))
      .exec(http("aot - Reactive VT").get("http://localhost:8088/product/all"))
      .exec(http("jit - Reactive VT").get("http://localhost:8089/product/all"))

    setUp(
        theScenarioBuilder.inject(
            rampUsers(5000).during(1 minutes)
        )
    ).protocols(theHttpProtocolBuilder)
}
