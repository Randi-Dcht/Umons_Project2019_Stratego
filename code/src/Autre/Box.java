//package be.dchtrnd.mesprojets.stratego;

import java.io.Serializable;

/**
*Cette classe permet de définir ce qu'est une case du jeu avec ses méthodes.
@param x qui est l'absice.
@param y qui est l'ordonée.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class Box implements Serializable
{
  final int x; //on peut pas modifier x car la case est fixe
  final int y; //on peut pas modifier y car la case est fixe
  public boolean occupe = false;
  private Piece occupant = null;

  public Box(int x_, int y_)
  {
    x = x_;
    y = y_;
  }

  public String toString()
  {
    return "(" + x + "," + y + ")";
  }

/**
*Cette méthodes permet de voir quelle joueur se trouve sur la case demandé.
@return joueur
*/
  public Player player() //return le nom joueur (encapuslation)
  {
    if (occupant != null) //permet d'éviter le problème de la case vide (nullpointeurecexpion)
    {
      return occupant.joueur;
    }
    return null;
  }

/**
*Cette méthodes permet de retourner la pièce qui se trouve sur la case demandée.
@return occupant de la case qui est une pièce.
*/
  public Piece whoOccupant()//return l'occupant (encapuslation)
  {
    return occupant;
  }

/**
*Cette méthode permet de dire qu'une pièce arrive sur la case.
@param piont qui est la pièce qui arrive sur la case.
*/
  public void arrived(Piece piont) //on prévient qu'une pièce est sur la case
  {
      occupant = piont;
      occupe = true;
  }

/**
*Cette méthode permet de dire que la pièce quitte la case et que celle-ci est libre.
*/
  public void from() //quand une pièce part, on lui dit
  {
    occupant = null;
    occupe = false;
  }
}
