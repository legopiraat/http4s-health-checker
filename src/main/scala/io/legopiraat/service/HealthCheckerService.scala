package io.legopiraat.service

import cats.Monad
import io.legopiraat.domain.{Health, HealthIndication, HealthIndicator, Up}

import scala.language.higherKinds

object HealthCheckerService {
  def apply[F[_] : Monad](healthIndicators: List[HealthIndicator]): HealthCheckerService[F] = {
    new HealthCheckerService[F](healthIndicators: List[HealthIndicator])
  }
}

class HealthCheckerService[F[_] : Monad](healthIndicators: List[HealthIndicator]) {

  def health(): HealthIndication = {
    val allIndicators: Seq[Health] = healthIndicators.map(indicator => indicator.health())

    println(allIndicators.toString())

    HealthIndication(Up)
  }

}

