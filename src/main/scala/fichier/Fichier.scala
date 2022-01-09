package main.scala.fichier

import scala.io.Source
import main.scala.pelouse._
import main.scala.coordonnees._
import main.scala.instruction._
import main.scala.orientation._
import main.scala.tondeuse._

/**
 * La classe fichier contenant les lignes correspondant à la pelouse, les tonedeuses et les instructions.
 * @param path le chemin vers le fichier à lire.
 */

class Fichier(path : String) {
  
  val lignes: List[String] = lectureFichier()
  val pelouse: Option[Pelouse] = getPelouse
  val tondeuses: List[Tondeuse] = getTondeuses
  val instructions: List[List[Instruction.Value]] = getInstructions

  /**
   * Lit le fichier correspondant au path et renvoie les lignes sous forme de liste
   * @return Liste des lignes du fichier correspondant au path
   */
  def lectureFichier() : List[String] = {
    val bufferedSource = Source.fromFile(this.path)
    val lines = bufferedSource.getLines().toList
    bufferedSource.close()
    lines
  }

  /**
   * Récupère les coordonnées de la pelouse et renvoie une instance de la classe Pelouse.
   * Si le fichier d'entrée est vide, renvoie None
   * @return Option[Pelouse], Contenant l'objet correspondant à la pelouse.
   */
  def getPelouse: Option[Pelouse] = {
    if (this.lignes.nonEmpty) {
      val lignePelouse = this.lignes.head.split(" ")
      if (lignePelouse.length == 2) {
        val abscisse = lignePelouse(0).toInt
        val ordonnees = lignePelouse(1).toInt
        Some(Pelouse(Coordonnees(abscisse, ordonnees)))
      }
      else {
        None
      }
    }
    else {
      None
    }
  }

  /**
   * Renvoie la liste des tondeuses contenues dans le fichier d'entrée.
   * Si aucune tondeuse n'est contenue dans le fichier, renvoie une liste vide.
   * @return Liste des tondeuses.
   */
  def getTondeuses: List[Tondeuse] = {
    if(this.lignes.nonEmpty && this.lignes.length > 1) {
      var listeTondeuses = this.lignes
      listeTondeuses = listeTondeuses.indices.collect{case i if (i%2 == 1) => listeTondeuses(i)}.toList
      listeTondeuses.map(element => {
        val infoTondeuse = element.split(" ")
        val abscisse = infoTondeuse(0).toInt
        val ordonnee = infoTondeuse(1).toInt
        val coordonnees = Coordonnees(abscisse, ordonnee)
        val orientation = Orientation.withName(infoTondeuse(2))
        new Tondeuse(coordonnees, orientation)
      })
    }
    else List.empty[Tondeuse]
  }

  /**
   * Renvoie la liste des instructions contenues dans le fichier.
   * Si aucune instruction n'est dans le fichier, renvoie une liste vide.
   * @return Liste des instructions
   */
  def getInstructions: List[List[Instruction.Value]] = {
    if(this.lignes.nonEmpty && this.lignes.length > 2) {
      var listeInstructions = this.lignes
      listeInstructions = listeInstructions.indices.collect{case i if (i%2 == 0 && i!=0) => listeInstructions(i)}.toList
      listeInstructions.map(element => {
        val instructionTondeuse = element.toList.map(instruction => Instruction.withName(instruction.toString))
        instructionTondeuse
      })
    }
    else {
      List.empty[List[Instruction.Value]]
    }
  }
}