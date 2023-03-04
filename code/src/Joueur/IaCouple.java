//package be.dchtrnd.mesprojets.stratego;

import java.io.*;

/**
*Cette classe permet de créer un trio pour facilité l'IA dans ses choix lors des déplacements.
@param pièce qui est une pièce de l'IA.
@param nombre qui est le nombre de case possibles pour bouger.
@param position qui est un tableau de position où l'IA peut bouger ses pions.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class IaCouple implements Serializable
{
  public Piece piece;     //la piece même
  public int nombre;      //pourcentage de déplacement entre 0 et 3
  public Box[] position;  //les positions possibles avant-arrière-droite-gauche

  public IaCouple(Piece piece_, int nombre_,Box[] position_)
  {
    piece = piece_;
    nombre = nombre_;
    position = position_;
  }

  public String toString()
  {
    return piece.monNom + " avec " + nombre + " %";
  }
}
