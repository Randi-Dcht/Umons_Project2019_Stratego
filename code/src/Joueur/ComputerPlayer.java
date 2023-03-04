//package be.dchtrnd.mesprojets.stratego;

import java.util.*;
import java.io.*;

/**
*Cette classe est une classe abstraite qui permet de définir un joueur éléctronique.

@param couleur qui est la couleur du joueur.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public abstract class ComputerPlayer implements Player, Serializable
{
  public Piece[] maliste;
  final int[] placermoi ={0,9,0,4};
  final Couleur couleur;
  private Player nextplayer;
  private int pieceVivant;
  protected Piece[] lautre ={};
  private Random rd = new Random();

  public ComputerPlayer(Couleur mycoleur)
  {
    couleur = mycoleur;
  }

/**
*Cette méthode permet de donner à ce joueur des informations supplémentaire.

@param joueur qui est le joueur qui suit celui-ci.
@param liste qui est sa liste de pièce à lui.
@param nombrePiece qui est le nombre de pièce vivants
*/
  public void assignment(Player joueur,Piece[] liste, int nombrePiece)
  {
    nextplayer = joueur;
    maliste = liste;
    pieceVivant = nombrePiece;
  }

/**
*Cette méthode permet de retouner le joueur qui suit.
@return nextplayer qui est le joueur suivant.
*/
  public Player next()
  {
    return nextplayer;
  }

/**
*Cette méthode permet de diminuer le nombre de pièce vivant disponible.
*/
  public void dead()
  {
    pieceVivant--;
  }

/**
*Cette méthode permet de retourner la couleur du joueur.
@return couleur qui est sa couleur
*/
  public Couleur myColor()
  {
    return couleur;
  }

/**
*Cette méthode permet de retouner le nombre de pièce vivant.
@return pieceVivant.
*/
  public int howMany()
  {
    return pieceVivant;
  }

/**
*Cette méthode permet de retourner la liste de pièce de ce joueur.
@return liste de pièce de ce joueur.
*/
  public Piece[] myList()
  {
    return maliste;
  }

/**
*Cette méthode permet de retouner la liste des pièces capturées.
@return liste de pièce des autres joueur capturé.
*/
  public Piece[] captureList()
  {
    return lautre;
  }

/**
*Cette méthode permet de retouner où le joueur est placé sur la grille.
@return tableau de int.
*/
  public int[] wherePlace()
  {
    return placermoi;
  }

/**
*Cette méthode permet de retouner le nom du joueur.
@return nom du joueur.
*/
  public String myName()
  {
    return "Ordinateur";
  }

  public void catchOther(Piece autre)
  {}

/**
*Cette méthode permet de créer une arraylist de positions possibles pour placer mes
*pièces.
@return liste qui est une arraylist de case.
*/
  public ArrayList<Box> creedPlace()
  {
    ArrayList<Box> liste = new ArrayList<Box>();
    for(int i = 0 ; i < 4 ; i++)
    {
      for(int j = 0 ; j < 10 ; j++)
      {
        liste.add(Board.plateau[j][i]);
      }
    }
    return liste;
  }

/**
*Cette méthode permet de rechercher une case dans une arraylist de cases.
@param liste qui est une arraylist de case possibles.
@param x qui est l'absice de la case.
@param y qui est l'ordoné de la case.

@return position de la case recherché dans la liste.
*/
  public int research(ArrayList<Box> liste,int x,int y)
  {
    if(0 <= x && x < 10 && 0 <= y && y< 10)
    {
      for(int i = 0 ; i < liste.size() ; i++)
      {
        if(liste.get(i).x == x && liste.get(i).y == y)
        {
          return i;
        }
      }
    }
    return rd.nextInt(liste.size());
  }


/**
*Cette méthode permet de placer les pions de l'ordinateur avec une certaines constante
*comme le drapeau entouré de bombe, etc.
*/
  public void placePawn()
  {
      ArrayList<Box> liste = creedPlace();
      int valeur = 0;
      int memoire = 0;
      Box place;

      for(int nb = 0 ; nb < maliste.length ; nb ++)
      {
        if(maliste[nb].monNom.equals(NomPiece.drapeau)) //placer sur la première ligne
        {
          valeur = research(liste,rd.nextInt(10),0);
        }
        else if(maliste[nb].monNom.equals(NomPiece.bombe) && memoire < 3) //placer autour drapeau
        {
          if(memoire == 0)
          {
            valeur = research(liste,maliste[0].position.x + 1,0);
          }
          if(memoire == 1)
          {
            valeur = research(liste,maliste[0].position.x - 1,0);
          }
          if(memoire == 2)
          {
            valeur = research(liste,maliste[0].position.x,maliste[0].position.y + 1);
          }
          memoire++;
        }
        else if(maliste[nb].monNom.equals(NomPiece.eclaireur)) //placer en première ligne
        {
          valeur = research(liste,rd.nextInt(10),3);
        }
        else
        {
          valeur = rd.nextInt(liste.size());
        }
          place = liste.get(valeur);
          liste.remove(valeur);
          maliste[nb].placeMe(place);

    }
  }

  public abstract void displacementPawn();
  public abstract void createListe();
}
