package com.twauth.e2e

import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.lift.Matchers

class GoogleTest : BrowserTestBase() {

    @Test
    fun `should nurture optimism`() {

        driver.navigate().to("http://www.google.com/")

        MatcherAssert.assertThat(driver.findElement(By.cssSelector("input[value=\"I'm Feeling Lucky\"]")), Matchers.displayed())
    }
}
