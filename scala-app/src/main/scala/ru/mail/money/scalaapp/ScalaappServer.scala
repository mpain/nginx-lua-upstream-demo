package ru.mail.money.scalaapp

import cats.effect.{Async, Resource, Sync}
import cats.syntax.all._
import com.comcast.ip4s._
import fs2.Stream
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.implicits._
import org.http4s.server.middleware.Logger

object ScalaappServer {

  def stream[F[_]: Async](name: String): Stream[F, Nothing] = {
    for {
      helloWorldAlg <- Stream.eval(Sync[F].pure(HelloWorld.impl[F]))

      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract a segments not checked
      // in the underlying routes.
      httpApp = (
        ScalaappRoutes.helloWorldRoutes[F](name, helloWorldAlg) <+>
        ScalaappRoutes.actionRoutes[F](name)
      ).orNotFound

      // With Middlewares in place
      finalHttpApp = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

      exitCode <- Stream.resource(
        EmberServerBuilder.default[F]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(finalHttpApp)
          .build >>
        Resource.eval(Async[F].never)
      )
    } yield exitCode
  }.drain
}
