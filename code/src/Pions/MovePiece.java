//package be.dchtrnd.mesprojets.stratego;

/**
*Cette classe abstraite est une sous classe de Piece qui permet de définir la méthode bouger
d'un pion pour qu'il puisse se déplacer sur la grille.

@param joueur qui possède la pièce.
@param nom comment s'appelle la pièce (doit être dans la liste NomPiece).
@param id appelation de la Piece en abréger de IdPiece

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/

public abstract class MovePiece extends Piece
{
  public MovePiece(Player joueur, NomPiece nom,IdPiece id)
  {
    super(joueur,nom,id);

  }



/**
*cette méthode permet de calculer une différence entre deux points

@param position c'est la position où le pion doit aller après son déplacement.
@return un tableau de int avec l'absice en position 0 et l'ordonnée en position 1.
*/
  public int[] delta (Box position)
  {
    int[] tab = new int[2];
    tab[0] = Math.abs(position.x - this.position.x);
    tab[1] = Math.abs(position.y - this.position.y);
    return tab;
  }


/**
*Cette méthode regarde si le pion se déplace bien en ligne droite sur le plateau.
@param tableau qui contient la différence des absice et des ordonnées.
@return true si le pion va tout droit et false si le pion se déplace en diagonal.
*/
  public boolean goright(int[] tableau) //bonne utilisation: utiliser la méthode delta pour le tableau en paramètre
  {
    if(tableau[0] == 0 || tableau[1] == 0)
    {
      return true;
    }
    return false;
  }



/**
*Cette méthode permet de déplacer le pion sur le grille en <b>modifiant</b> la varaible position.

@param position elle prend uniquement la case où le pion sera après son déplacement.
@throws InvalidBoxExecption si le pion veut aller sur une case déjà occupé ou inexistante.
@see attack elle regarde si la case d'arrivé comporte un pion adverse pour pouvoir l'attaquer.
*/
  public boolean possible(Box position) //throws InvalidBoxExecption
  {
    jattaque = false;
    if(position.player() != this.joueur && goright(delta(position)))
    {
      Summary.WRITE("displacement : déplacement de "+ this + " à " + position);
      if (position.occupe && position.whoOccupant() != null) //regarde s'il est possible d'attaquer un piont adverse et appelle la méthode t'attaque.
      {
        jattaque = true;
        attack(position.whoOccupant());
      }
      if (position.occupe && position.whoOccupant() == null) //si c'est un obstacle alors pas déplacement
      {
        return false;
      }
      if (this.position != null) //si le pion est mort il ne doit plus être déplacé
      {
        return true;
      }
      //return true;
    }
    Summary.WRITE("displacement :"+this.monNom+" déplecement impossible en " + position);
    return false;
  }


  public abstract void attack(Piece piece);
}
