package io.legopiraat.endpoint

import cats.effect.Effect
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._
import io.legopiraat.domain.{Down, Up}
import io.legopiraat.service.HealthCheckerService
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

import scala.language.higherKinds

object HealthCheckEndpoint {
  def endpoints[F[_] : Effect](healthCheckService: HealthCheckerService[F]): HttpRoutes[F] = {
    new HealthCheckEndpoint[F](healthCheckService: HealthCheckerService[F]).endpoints()
  }
}

class HealthCheckEndpoint[F[_] : Effect](healthCheckService: HealthCheckerService[F]) extends Http4sDsl[F] {

  def endpoints(): HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "liveness" =>
      Ok(Json.obj("health" -> Json.fromString("UP")))

    case GET -> Root / "readiness" =>
      val healthIndication = healthCheckService.health()

      healthIndication.status match {
        case Up => Ok(healthIndication.asJson)
        case Down => ServiceUnavailable(healthIndication.asJson)
      }
  }
}

