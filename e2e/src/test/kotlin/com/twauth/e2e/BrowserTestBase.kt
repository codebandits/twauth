package com.twauth.e2e

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import java.util.concurrent.TimeUnit

abstract class BrowserTestBase {

    lateinit var driver: WebDriver

    @BeforeEach
    fun setUp() {
        driver = ChromeDriver()
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
    }

    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}
