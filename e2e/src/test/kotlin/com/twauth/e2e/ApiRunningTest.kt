package com.twauth.e2e

import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Test
import org.openqa.selenium.By

class ApiRunningTest : BrowserTestBase() {

    private val API_URL = System.getenv("API_URL") ?: "http://localhost:8080"

    @Test
    fun `should be running`() {

        driver.navigate().to(API_URL + "/application/health")
        MatcherAssert.assertThat(driver.findElement(By.tagName("body")).text, org.hamcrest.Matchers.containsString("UP"))
    }
}
