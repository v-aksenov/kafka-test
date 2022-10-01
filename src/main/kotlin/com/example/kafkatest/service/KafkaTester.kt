package com.example.kafkatest.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.Instant

@Service
class KafkaTester(
    private val kafkaSender: KafkaSender,
    @Value("\${test.counter}") private val counter: Long
) {

    private lateinit var from: Timestamp
    private lateinit var toSend: Timestamp
    private var fromListen: Timestamp? = null
    private lateinit var to: Timestamp

    fun start() {
        println("Starting test")
        from = Timestamp.from(Instant.now())
        for (i in 1 until counter) {
            println("-> [GO:$i]")
            Thread.sleep(100)
            kafkaSender.send("GO:$i")
        }
        kafkaSender.send("END")
        println("END was sent.")
        toSend = Timestamp.from(Instant.now())
    }

    fun catchResult(message: String) {
        if(fromListen == null) {
            fromListen = Timestamp.from(Instant.now())
        }
        if (message == "END") {
            to = Timestamp.from(Instant.now())
            val resultSeconds: Long = (to.time - from.time) / MILLISECONDS_IN_SECOND
            val secondsForSend: Long = (toSend.time - from.time) / MILLISECONDS_IN_SECOND
            val secondsForListen: Long = (to.time - fromListen!!.time) / MILLISECONDS_IN_SECOND
            val waiting: Long = (fromListen!!.time - toSend.time) / MILLISECONDS_IN_SECOND
            println("Test ended")
            println("Result time is $resultSeconds seconds")
            println("$secondsForSend seconds to send")
            println("$secondsForListen seconds to listen")
            println("$waiting seconds to wait")
            println("${counter / resultSeconds} messages per second")
            println("${counter / secondsForSend} messages per second to send")
            println("${counter / secondsForListen} messages per second to listen")
        }
    }
}

private const val MILLISECONDS_IN_SECOND = 1000