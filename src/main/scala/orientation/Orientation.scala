package main.scala.orientation

/**
 * Les differentes orientations possibles
 * N pour le Nord
 * S pour les Sud
 * E pour l'Est
 * W pour l'Ouest
 */
object Orientation extends Enumeration {
  type Orientation = Value
  val N, S, E, W = Value
}