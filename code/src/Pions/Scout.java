//package be.dchtrnd.mesprojets.stratego;

/**
*Cette classe permet de définir le comportement d'un eclaireur qui peut se déplacer de plusieurs cases
*et attaquer les personnes inférieur à lui.

@param joueur_ qui possède le pion
@param nom comment s'appelle la pièce
@param id_ pour dire comment s'appelle le pion en abréger

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/

public class Scout extends ManyCase
{
  /**
  *Liste des pièces qui peuvent le tuer.
  */
  final NomPiece[] attaque={NomPiece.marechal,NomPiece.general,NomPiece.colonel,NomPiece.commandant,NomPiece.capitaine,NomPiece.lieutenant,NomPiece.sergent,NomPiece.demineur,NomPiece.bombe};

  public Scout(Player joueur)
  {
    super(joueur,NomPiece.eclaireur,IdPiece.E02,10);
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
