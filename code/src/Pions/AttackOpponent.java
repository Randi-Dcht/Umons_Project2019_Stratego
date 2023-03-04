//package be.dchtrnd.mesprojets.stratego;

/**
*Cette classe est une sous classe de MovePiece qui permet de définir qu'un piont peut attaquer un autre.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/

public abstract class AttackOpponent extends MovePiece
{
  final NomPiece[] attaque={}; /*liste des pièces par qui il peut se faire attaquer*/

  public AttackOpponent(Player joueur, NomPiece nom,IdPiece id)
  {
    super(joueur,nom,id);
  }


/**
*Cette méthode permet d'attaquer un pion de l'aversaire en regardant dans la liste s'il peut le tuer.
*elle <b>modifie</b> la variable vivant (en false) de la pièce s'il est mort.
@param piece qui est une pièce de l'adversaire.
@see stateLife (Piece) qui vérifie si le pion est mort ou vivant.
*/
  public void attack(Piece piece)
  {
    if ((this.monNom).equals(piece.monNom)) /*même grade alors tous les deux meurt*/
    {
      this.vivant = false;
      piece.vivant = false;
      piece.joueur.catchOther(this);
      this.joueur.catchOther(piece);
    }
    else if(research(piece)) /*regarde s'il peut tuer cette pièce*/
    {
      this.vivant = false;
      piece.joueur.catchOther(this);
    }
    else
    {
      this.joueur.catchOther(piece);
      piece.vivant = false;
    }
    this.stateLife();
    piece.stateLife();
    Summary.WRITE("attack : piece attaquant: " + this.monNom + "("+ this.vivant + ")" + " piece attaquer "+ piece.monNom + "(" + piece.vivant + ")");
  }


  public abstract boolean research(Piece piece);//à modifier pour être plus simple O(n)
}
