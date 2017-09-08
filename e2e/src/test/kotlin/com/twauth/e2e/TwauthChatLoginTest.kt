package com.twauth.e2e

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.lift.Matchers.displayed

class TwauthChatLoginTest : BrowserTestBase() {

    private val TWITTER_USERNAME = System.getenv("TWITTER_USERNAME") ?: throw RuntimeException("set TWITTER_USERNAME environment variable to run tests")
    private val TWITTER_PASSWORD = System.getenv("TWITTER_PASSWORD") ?: throw RuntimeException("set TWITTER_PASSWORD environment variable to run tests")
    private val CHAT_URL = System.getenv("CHAT_URL") ?:  "http://localhost:3000"

    @Test
    fun `should authenticate users who tweet`() {

        driver.navigate().to(CHAT_URL)
        assertThat(driver.findElement(By.tagName("body")).text, containsString("Tweet to login"))

        val tweetWidgetFrame = driver.findElement(By.cssSelector("iframe[title='Twitter Tweet Button']"))
        driver.switchTo().frame(tweetWidgetFrame)
        driver.findElement(By.linkText("Tweet")).click()
        assertThat(driver.windowHandles, hasSize(2))

        driver.switchTo().window(driver.windowHandles.last())
        assertThat(driver.findElement(By.cssSelector("textarea#status")), displayed())

        driver.findElement(By.id("username_or_email")).sendKeys(TWITTER_USERNAME)
        driver.findElement(By.id("password")).sendKeys(TWITTER_PASSWORD)
        driver.findElement(By.cssSelector("input[type=submit]")).click()
        driver.switchTo().window(driver.windowHandles.first())
    }
}
