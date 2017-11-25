package com.netsocks.e2e.config

object ConfigProvider {
  val CAMPAIGN_URI: String = getEnvOrDefault("CAMPAIGN_API_URI", "http://localhost:8083")
  val FAN_URI: String = getEnvOrDefault("FAN_API_URI", "http://localhost:8081")

  def getEnvOrDefault(key: String, default: String): String = {
    var value = System.getenv(key)

    if (value == null || value.isEmpty)
      return default

    value
  }
}
