package com.fdn.opensn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OpensnApplication

fun main(args: Array<String>) {
  runApplication<OpensnApplication>(*args)
}
