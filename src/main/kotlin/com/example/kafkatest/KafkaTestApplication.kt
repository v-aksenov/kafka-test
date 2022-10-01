package com.example.kafkatest

import com.example.kafkatest.service.KafkaTester
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaTestApplication

fun main(args: Array<String>) {
    val runApplication = runApplication<KafkaTestApplication>(*args)
    val bean = runApplication.getBean("kafkaTester", KafkaTester::class.java)
    bean.start()
}
