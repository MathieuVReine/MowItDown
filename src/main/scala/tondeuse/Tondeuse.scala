package main.scala.tondeuse

import main.scala.coordonnees._
import main.scala.orientation._
import main.scala.instruction._

/**
 * La classe des Tondeuses contenant les informations sur les tondeuses.
 * @param coordonnees Les coordonnees initiales de la tondeuse.
 * @param orientation L'orientation initiale de la tondeuse.
 */
class Tondeuse(coordonnees: Coordonnees, var orientation : Orientation.Value) {

  /**
   *
   * @return String contenant les coordonnees et l'orientation de la tondeuse.
   */
  def positionActuelle: String = this.coordonnees.x.toString + " " + this.coordonnees.y.toString + " " + this.orientation.toString

  /**
   *
   * @return Tuple contenant les orientations possibles pour la tondeuse suivant son orientation actuelle.
   */
  def rotation(): (Orientation.Value, Orientation.Value) = {
    this.orientation match {
      case Orientation.N => Tuple2(Orientation.W, Orientation.E)
      case Orientation.S => Tuple2(Orientation.E, Orientation.W)
      case Orientation.E => Tuple2(Orientation.N, Orientation.S)
      case Orientation.W => Tuple2(Orientation.S, Orientation.N)
    }
  }

  /**
   *
   * @param coordonneesPelouse : Les coordonnees de l'angle supérieur droit de la pelouse.
   */
  def deplacement(coordonneesPelouse: Coordonnees): Unit = {
    this.orientation match {
      case Orientation.N => if(this.coordonnees.y < coordonneesPelouse.y) this.coordonnees.y += 1
      case Orientation.S => if(this.coordonnees.y > 0) this.coordonnees.y -= 1
      case Orientation.E => if(this.coordonnees.x < coordonneesPelouse.x) this.coordonnees.x += 1
      case Orientation.W => if(this.coordonnees.x > 0) this.coordonnees.x -= 1
    }
  }

  /**
   * Effectue les opérations correspondant à l'instruction lue.
   * @param instruction : l'instruction devant être interprétée par la tondeuse.
   * @param coordonneesPelouse : Les coordonnees de l'angle supérieur droit de la pelouse.
   */
  def lectureInstruction(instruction: Instruction.Value, coordonneesPelouse: Coordonnees): Unit = {
    instruction match {
      case Instruction.A => this.deplacement(coordonneesPelouse)
      case Instruction.G => this.orientation = this.rotation()._1
      case Instruction.D => this.orientation = this.rotation()._2
    }
  }
}