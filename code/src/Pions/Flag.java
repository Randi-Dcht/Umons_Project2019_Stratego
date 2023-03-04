//package be.dchtrnd.mesprojets.stratego;

/**
*Cette classe permet de définir ce qu'est un drapeau
@param joueur qui est le joueur qui possède la pièce.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class Flag extends Piece
{
  public Flag(Player joueur)
  {
    super(joueur,NomPiece.drapeau,IdPiece.D00);
  }

/**
*Le drapeau ne peut pas attaquer.
*/
  public void attack(Piece pion)
  {}

/**
*Le drapeau ne pas bouger
@ return false
*/
  public boolean possible(Box position)// throws ImpossibleToMove
  {
    return false;
  }
}
