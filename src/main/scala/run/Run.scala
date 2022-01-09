package main.scala.run

import main.scala.fichier._

object Run {
  def main(args: Array[String]): Unit = {
    val fichier = new Fichier(args(0))
    val tondeuses = fichier.tondeuses
    val pelouse = fichier.pelouse.get
    val instructions = fichier.instructions
    val tondeusesInstructions = (tondeuses zip instructions).zipWithIndex
    for (((tondeuse, instructions), index) <- tondeusesInstructions) {
      instructions.foreach(instruction => tondeuse.lectureInstruction(instruction, pelouse.coinSuperieurDroit))
      println("Tondeuse" + " " + index.toString + " : " + tondeuse.positionActuelle)
    }
  }
}
