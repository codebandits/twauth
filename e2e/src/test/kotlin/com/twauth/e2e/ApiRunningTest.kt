package com.twauth.e2e

import com.google.gson.Gson
import org.apache.http.HttpStatus
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.HttpClientBuilder
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class ApiRunningTest {

    private val API_URL = System.getenv("API_URL") ?: "http://localhost:9001"

    @Test
    fun `should be running`() {

        val client = HttpClientBuilder.create().build()
        val response = client.execute(HttpGet(API_URL + "/application/health"))
        assertThat(response.statusLine.statusCode, equalTo(HttpStatus.SC_OK))

        val json = BasicResponseHandler().handleResponse(response)
        val health = Gson().fromJson(json, ActuatorHealthStatus::class.java)
        assertThat(health.status, equalTo("UP"))
    }

    private data class ActuatorHealthStatus(
            val status: String
    )
}
