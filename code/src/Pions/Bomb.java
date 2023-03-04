//package be.dchtrnd.mesprojets.stratego;

/**
*Cette classe permet de définir le comportement d'une bombe qui peut attaquer toutes les autres Pièces
*sauf le démineur .

@param joueur_ qui possède le pion

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/

public class Bomb extends Piece
{
  public Bomb(Player joueur)
  {
    super(joueur,NomPiece.bombe,IdPiece.B11);
  }

/**
*la bombe ne peut pas attaquer.
*/
  public void attack(Piece pion)
  {

  }

/**
*la bombe ne peut pas se déplacer donc return false.
@return false
*/

  public boolean possible(Box position)
  {
    return false;
  }
}
