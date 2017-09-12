package com.twauth.chat.webconfiguration

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils

@Service
class WebConfigurationService(environment: Environment) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val objectMapper = ObjectMapper(YAMLFactory())
    private val configuration: JsonNode

    init {
        configuration = when {
            environment.activeProfiles.contains("production") -> loadConfiguration("production")
            environment.activeProfiles.contains("ci") -> loadConfiguration("ci")
            environment.activeProfiles.contains("local") -> loadConfiguration("local")
            else -> emptyConfiguration
        }
    }

    private fun loadConfiguration(profile: String): JsonNode {
        logger.info("Loading web configuration: {}", profile)
        val configurationFilePath = String.format("classpath:webconfiguration/%s.yml", profile)
        val configurationFile = ResourceUtils.getFile(configurationFilePath)
        return objectMapper.readTree(configurationFile)
    }

    private val emptyConfiguration: JsonNode
        get() {
            logger.warn("No known profiles were active, creating empty web configuration")
            return objectMapper.createObjectNode()
        }

    fun getConfiguration(): JsonNode = configuration
}
