package com.netsocks.simulations.config

object ConfigProvider {
  val CAMPAIGN_URI: String = getEnvOrDefault("CAMPAIGN_API_URI", "http://localhost:8080")
  val FAN_URI: String = getEnvOrDefault("FAN_API_URI", "http://localhost:8081")

  def getEnvOrDefault(key: String, default: String): String = {
    var value = System.getenv(key)

    if (value == null || value.isEmpty)
      return default

    value
  }
}
