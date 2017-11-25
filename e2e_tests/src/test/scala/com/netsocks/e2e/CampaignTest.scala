package com.netsocks.e2e

import com.netsocks.e2e.config.ConfigProvider
import io.restassured.RestAssured
import io.restassured.RestAssured._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matchers.equalTo
import org.junit.{Before, Test}

class CampaignTest {
  @Before
  def setUp() {
    RestAssured.baseURI = ConfigProvider.CAMPAIGN_URI
  }

  @Test
  def `it should increment the validity of saved campaigns by one`() {
    // Campaign 1
    val id1 =
      given()
        .body("""{ "name": "Campaign 1", "favoriteTeamId": "SEP", "validityStart": "2017-12-01", "validityEnd": "2017-12-03" }""")
        .contentType("application/json")
        .accept("application/json")
        .when()
        .post("/campaigns")
        .Then()
        .statusCode(201)
        .header("location", notNullValue())
        .body("id", notNullValue())
        .extract().path[String]("id")

    given()
      .contentType("application/json")
      .accept("application/json")
      .when()
      .get("/campaigns/{id}", id1)
      .Then()
      .body("name", equalTo("Campaign 1"))


    // Campaign 2
    val id2 =
      given()
        .contentType("application/json")
        .accept("application/json")
        .body("""{ "name": "Campaign 2", "favoriteTeamId": "SEP", "validityStart": "2017-12-01", "validityEnd": "2017-12-02" }""")
        .when()
        .post("/campaigns")
        .Then()
        .statusCode(201)
        .header("location", notNullValue())
        .body("id", notNullValue())
        .extract().path[String]("id")

    given()
      .contentType("application/json")
      .accept("application/json")
      .when()
      .get("/campaigns/{id}", id2)
      .Then()
      .body("name", equalTo("Campaign 2"))


    // Campaign 3
    val id3 =
      given()
        .contentType("application/json")
        .accept("application/json")
        .body("""{ "name": "Campaign 3", "favoriteTeamId": "SEP", "validityStart": "2017-12-01", "validityEnd": "2017-12-03" }""")
        .when()
        .post("/campaigns")
        .Then()
        .statusCode(201)
        .header("location", notNullValue())
        .body("id", notNullValue())
        .extract().path[String]("id")

    given()
      .contentType("application/json")
      .accept("application/json")
      .when()
      .get("/campaigns/{id}", id1)
      .Then()
      .body("name", equalTo("Campaign 1"))



    // Ensuring other campaigns validity has been correctly increased
    given()
      .contentType("application/json")
      .accept("application/json")
      .when()
      .get("/campaigns/{id}", id1)
      .Then()
      .body("name", equalTo("Campaign 1"))
      .body("validityStart", equalTo("2017-12-01"))
      .body("validityEnd", equalTo("2017-12-05"))

    given()
      .contentType("application/json")
      .accept("application/json")
      .when()
      .get("/campaigns/{id}", id2)
      .Then()
      .body("name", equalTo("Campaign 2"))
      .body("validityStart", equalTo("2017-12-01"))
      .body("validityEnd", equalTo("2017-12-04"))

    
    // Cleanup
    given()
      .contentType("application/json")
      .accept("application/json")
      .when()
      .delete("/campaigns/{id}", id1)
      .Then()
      .statusCode(204)

    given()
      .contentType("application/json")
      .accept("application/json")
      .when()
      .delete("/campaigns/{id}", id2)
      .Then()
      .statusCode(204)

    given()
      .contentType("application/json")
      .accept("application/json")
      .when()
      .delete("/campaigns/{id}", id2)
      .Then()
      .statusCode(204)
  }
}
