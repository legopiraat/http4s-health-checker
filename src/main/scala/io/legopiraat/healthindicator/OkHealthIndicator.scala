package io.legopiraat.healthindicator

import cats.effect.Effect
import io.legopiraat.domain.{Health, HealthIndicator, Up}

import scala.language.higherKinds

object OkHealthIndicator {
  def apply[F[_] : Effect]: OkHealthIndicator[F] = {
    new OkHealthIndicator[F]()
  }
}

class OkHealthIndicator[F[_] : Effect] extends HealthIndicator {
  override def health(): Health = Up
}