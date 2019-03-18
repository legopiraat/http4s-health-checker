package io.legopiraat.domain

trait HealthIndicator {
  def health(): Health
}
