package com.twauth.api

import com.twauth.api.login.LoginClientManager
import com.twauth.api.login.LoginClientManagerInMemory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class BeanConfiguration {

    @Bean
    open fun loginClientManager(@Value("\${hashtag}") hashtag: String): LoginClientManager =
            LoginClientManagerInMemory(hashtag)
}
