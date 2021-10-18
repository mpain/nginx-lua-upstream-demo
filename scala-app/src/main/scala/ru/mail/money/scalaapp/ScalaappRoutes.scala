package ru.mail.money.scalaapp

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object ScalaappRoutes {

  def actionRoutes[F[_]: Sync](name: String): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case req @ POST -> Root / "action" =>
        for {
          stringRequest <- req.bodyText.compile.lastOrError
          resp <- Ok(s"$name: $stringRequest")
        } yield resp
    }
  }

  def helloWorldRoutes[F[_]: Sync](instanceName: String, H: HelloWorld[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F]{}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "hello" / name =>
        for {
          greeting <- H.hello(HelloWorld.Name(name))
          resp <- Ok(greeting.copy(greeting = s"$instanceName: ${greeting.greeting}"))
        } yield resp
    }
  }
}