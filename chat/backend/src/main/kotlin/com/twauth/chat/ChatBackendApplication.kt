package com.twauth.chat

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class ChatBackendApplication

fun main(args: Array<String>) {
    SpringApplication.run(ChatBackendApplication::class.java, *args)
}
