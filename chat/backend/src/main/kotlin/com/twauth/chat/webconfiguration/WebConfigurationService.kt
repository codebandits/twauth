package com.twauth.chat.webconfiguration

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils

import java.io.File
import java.io.IOException

@Service
class WebConfigurationService(@Value("\${spring.profiles.active}") springProfilesActive: String) {

    private val configuration: JsonNode

    init {
        val configurationFilePath = String.format("classpath:webconfiguration/%s.yml", springProfilesActive)
        val configurationFile = ResourceUtils.getFile(configurationFilePath)
        val objectMapper = ObjectMapper(YAMLFactory())
        configuration = objectMapper.readTree(configurationFile)
    }

    fun getConfiguration(): JsonNode = configuration
}
