package com.twauth.chat.webconfiguration

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class WebConfigurationController(webConfigurationService: WebConfigurationService) {

    private val configuration: JsonNode = webConfigurationService.getConfiguration()

    @RequestMapping(value = "/configuration", method = arrayOf(RequestMethod.GET))
    fun configuration(): ResponseEntity<JsonNode> = ResponseEntity.ok(configuration)
}
