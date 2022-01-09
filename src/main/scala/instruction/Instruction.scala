package main.scala.instruction

/**
 * Les differentes instructions possibles.
 * A pour Avancer d'une case dans la direction actuelle.
 * D pour une rotation de 90° vers la Droite.
 * G pour une rotation de 90° vers la Gauche.
 */
object Instruction extends Enumeration {
  type Instruction = Value
  val A, G, D = Value
}