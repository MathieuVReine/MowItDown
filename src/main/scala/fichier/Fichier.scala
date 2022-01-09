package main.scala.fichier

import scala.io.Source
import main.scala.pelouse._
import main.scala.coordonnees._
import main.scala.instruction._
import main.scala.orientation._
import main.scala.tondeuse._

//

class Fichier(path : String) {
  
  val lignes: List[String] = lectureFichier()
  val pelouse: Option[Pelouse] = getPelouse
  val tondeuses: List[Tondeuse] = getTondeuses
  val instructions: List[List[Instruction.Value]] = getInstructions


  def lectureFichier() : List[String] = {
    val bufferedSource = Source.fromFile(this.path)
    val lines = bufferedSource.getLines().toList
    bufferedSource.close()
    lines
  }

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