package io.legopiraat.domain

case class HealthIndication(status: Health)

sealed trait Health {
  def status: String
}

case object Up extends Health {
  val status = "UP"
}

case object Down extends Health {
  val status = "DOWN"
}

case object Unknown extends Health {
  val status = "UNKNOWN"
}
