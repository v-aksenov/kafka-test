package com.example.kafkatest.service

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaListener(private val kafkaTester: KafkaTester) {

    @KafkaListener(topics = ["\${kafka.topic}"])
    fun listen(message: String) {
        println("<- [$message]")
        kafkaTester.catchResult(message)
    }
}