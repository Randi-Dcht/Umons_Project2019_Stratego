//package be.dchtrnd.mesprojets.stratego;

/**
*Cette classe permet de définir ce qu'est un marechal
@param joueur qui est le joueur qui possède la pièce.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class Marshal extends OneCase
{
  /**
  *Liste des pièces qui peuvent le tuer.
  */
  final NomPiece[] attaque={NomPiece.espion,NomPiece.bombe};//qui peut me tuer

  public Marshal(Player joueur)
  {
    super(joueur,NomPiece.marechal,IdPiece.M10);
  }

  /**
  *Cette méthode permet de voir si le pion que l'on attaque est dans la liste si on est mort.
  @param piece que l'on veut attaquer.
  @return true si la pièce se trouve dans la liste sinon false.
  */
  public boolean research(Piece piece)//à modifier pour être plus symple O(n)
  {
    for(int i =0; i < attaque.length ; i++)
    {
      if ((piece.monNom).equals(attaque[i]))
      {
        return true;
      }
    }
    return false;
  }
}
