package io.legopiraat

import cats.effect._
import cats.implicits._
import io.legopiraat.endpoint.HealthCheckEndpoint
import io.legopiraat.healthindicator.OkHealthIndicator
import io.legopiraat.service.HealthCheckerService
import org.http4s.implicits._
import org.http4s.server.blaze._
import org.http4s.server.{Router, Server}

import scala.language.higherKinds

object Application extends IOApp {

  private[this] val root = "/api"

  def run(args: List[String]): IO[ExitCode] = createServer().use(_ => IO.never).as(ExitCode.Success)

  def createServer[F[_] : ContextShift : ConcurrentEffect : Timer](): Resource[F, Server[F]] = {
    val okHealthIndicator = OkHealthIndicator[F]
    val healthCheckService = HealthCheckerService[F](List(okHealthIndicator))

    val services = HealthCheckEndpoint.endpoints(healthCheckService)
    val httpApp = Router(root -> services).orNotFound

    BlazeServerBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(httpApp)
      .resource
  }
}
