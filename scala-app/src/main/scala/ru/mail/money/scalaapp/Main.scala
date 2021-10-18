package ru.mail.money.scalaapp

import cats.effect.{ExitCode, IO, IOApp}

import java.util.UUID

object Main extends IOApp {
  def run(args: List[String]) = {
    val name = Option(System.getenv("APP_NAME")).getOrElse(UUID.randomUUID().toString)
    ScalaappServer.stream[IO](name).compile.drain.as(ExitCode.Success)
  }
}
