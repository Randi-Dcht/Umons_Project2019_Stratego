//package be.dchtrnd.mesprojets.stratego;

import java.io.Serializable;

/**
*Cette classe  permet de définir un joueur humain.
@param nom qui est le nom du joueur humain.
@param couleur qui est la couleur du joueur.
@param liste qui est une liste de int où le joueur se trouve sur la grille.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/

public class HumanPlayer implements Player, Serializable
{
  final String nom;
  public Piece[] maliste;
  public Piece[] capture = {};
  final int[] placermoi; //[min absice,max absice,min ordonnée,max ordonnée]
  final Couleur couleur;
  private Player nextplayer;
  private int Piecevivant;

  public HumanPlayer(String tonnom, Couleur couleur_,int[] liste)
  {
    nom = tonnom;
    couleur = couleur_;
    if(liste.length != 4)
    {
      new IllegalArgumentException();
    }
    placermoi = liste;
    Summary.WRITE("Player : le joueur est " + this);
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
    Piecevivant = nombrePiece;
    Summary.WRITE("HumanPlayer-assignment : le joueur qui suit " + this + " est " + joueur);
  }
  /**
  *Cette méthode permet de retouner le nom du joueur.
  @return nom du joueur.
  */
  public String myName()
  {
    return nom;
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
  *Cette méthode permet de retourner la couleur du joueur.
  @return couleur qui est sa couleur
  */
  public Couleur myColor()
  {
    return couleur;
  }
  /**
  *Cette méthode permet de retouner la liste des pièces capturées.
  @return liste de pièce des autres joueur capturé.
  */
  public Piece[] captureList()
  {
    return capture;
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
*Cette méthode permet d'agrandir le tableau actuelle de pièce attrapé et d'ajouter une pièce.
@param pièce qui est la pièce attrapée.
*/
  public void catchOther(Piece piece)
  {
    Piece[] liste = new Piece[capture.length + 1];
    for(int i = 0 ; i < capture.length ; i++)
    {
      liste[i] = capture[i];
    }
    liste[capture.length] = piece;
    capture = liste;
  }

  /**
  *Cette méthode permet de retouner le joueur qui suit.
  @return nextplayer qui est le joueur suivant.
  */
  public Player next()
  {
    return nextplayer;
  }

  public String toString()
  {
    return nom + " de couleur " + couleur ;
  }
  /**
  *Cette méthode permet de diminuer le nombre de pièce vivant disponible.
  */
  public void dead()
  {
    Piecevivant = Piecevivant -1;
    Summary.WRITE("dead : il y a un mort chez " + this.nom);
  }
  /**
  *Cette méthode permet de retouner le nombre de pièce vivant.
  @return pieceVivant.
  */
  public int howMany()
  {
    return Piecevivant;
  }
}
