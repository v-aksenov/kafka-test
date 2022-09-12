package com.example.kafkatest.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaSender(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    @Value("\${kafka.topic}") private val topic: String
) {

    fun send(message: String) {
        kafkaTemplate.send(topic, message)
    }
}