//package be.dchtrnd.mesprojets.stratego;

/**
*Cette classe permet de créer une liste de pièces avec un nombres demandées de pièces de chaque sorte.
@param NombreMarechal (int)
@param Nombregeneral (int)
@param Nombrecolonel (int)
@param Nombrecommandant (int)
@param Nombrecapitaine (int)
@param NombreLieutenant (int)
@param Nombresergent (int)
@param Nombredemineur (int)
@param Nombreeclaireur (int)
@param Nombreespion (int)
@param Nombrebombe (int)

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class StarPiece
{
  private int nbMarechal;
  private int nbgeneral;
  private int nbcolonel;
  private int nbcommandant;
  private int nbcapitaine;
  private int nbLieutenant;
  private int nbsergent;
  private int nbdemineur;
  private int nbeclaireur;
  private int nbespion;
  private int nbbombe;
  private int total;

  public StarPiece(int nbbombe_, int nbMarechal_, int nbgeneral_, int nbcolonel_, int nbcommandant_, int nbcapitaine_, int nbLieutenant_, int nbsergent_, int nbdemineur_, int nbeclaireur_, int nbespion_)
  {
    nbMarechal  = nbMarechal_;
    nbgeneral = nbgeneral_;
    nbcolonel =nbcolonel_;
    nbcommandant = nbcommandant_;
    nbcapitaine = nbcapitaine_;
    nbLieutenant = nbLieutenant_;
    nbsergent = nbsergent_;
    nbdemineur = nbdemineur_;
    nbeclaireur = nbeclaireur_;
    nbespion = nbespion_;
    nbbombe = nbbombe_;
    total = 1 + nbMarechal + nbgeneral + nbcolonel + nbcommandant + nbcapitaine + nbLieutenant + nbsergent + nbdemineur + nbeclaireur + nbespion + nbbombe;
  }

/**
*Cette méthode permet de retourner le nombre de pièces totales qui a été crées.
@return total qui est un int
*/
  public int howMany()
  {
    return total;
  }

/**
*Cette méthodes permet de donner le nombre de pièces crées mais qui sont déplaçables.
@return nombre de pièce.
*/
  public int howDisplacement()
  {
    return total - 1 - nbbombe;
  }

/**
*Cette méthode permet de créer la liste de pièces.
@param joueur qui possède les pièces.
@return liste de pièces.
*/
  public Piece[] createPiece(Player joueur)
  {
    Summary.WRITE("createPiece : création des pieces du joueur : " + joueur + " avec " + total + " pièces");
    //on crée une liste de la taille du nombre de piece:
    Piece[] liste = new Piece[total];

    //la première piece est obligatoirement le drapeau:
    Piece drapeau = new Flag(joueur);
    liste[0] = drapeau;

    //on rajoute les autres pièces:
    int nb=1;
    for(int i=0; i < nbbombe ;i++)
    {
      Piece bombe = new Bomb(joueur);
      liste[nb] =bombe;
      nb++;
    }

    for(int i=0; i < nbeclaireur ;i++)
    {
      Piece eclaireur = new Scout(joueur);
      liste[nb] = eclaireur;
      nb++;
    }

    for(int i=0; i < nbMarechal ;i++)
    {
      Piece marechal = new Marshal(joueur);
      liste[nb] = marechal;
      nb++;
    }

    for(int i=0; i < nbgeneral ;i++)
    {
      Piece general = new General(joueur);
      liste[nb] = general;
      nb++;
    }

    for(int i=0; i < nbcolonel ;i++)
    {
      Piece colonel = new Colonel(joueur);
      liste[nb] = colonel;
      nb++;
    }

    for(int i=0; i < nbcommandant ;i++)
    {
      Piece commandant = new Commander(joueur);
      liste[nb] = commandant;
      nb++;
    }

    for(int i=0; i < nbcapitaine ;i++)
    {
      Piece capitaine = new Captain(joueur);
      liste[nb] =capitaine;
      nb++;
    }

    for(int i=0; i < nbLieutenant;i++)
    {
      Piece lieutenant = new Lieutenant(joueur);
      liste[nb] =lieutenant;
      nb++;
    }

    for(int i=0; i < nbsergent ;i++)
    {
      Piece sergent = new Sergeant(joueur);
      liste[nb] =sergent;
      nb++;
    }

    for(int i=0; i < nbdemineur ;i++)
    {
      Piece demineur = new Minesweeper(joueur);
      liste[nb] = demineur;
      nb++;
    }

    for(int i=0; i < nbespion ;i++)
    {
      Piece espion = new Spy(joueur);
      liste[nb] = espion;
      nb++;
    }

    return liste;
  }
}
