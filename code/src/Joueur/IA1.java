//package be.dchtrnd.mesprojets.stratego;

import java.io.*;

/**
*Cette classe est un joueur non humain qui peut bouger seule ses pions et les placer.
@param Couleur qui est sa couleur lors de la partie.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class IA1 extends ComputerPlayer implements Serializable
{
  private int Piecevivant;
  private int actuelle;
  private IaCouple[] deplacement;

  public IA1(Couleur clr)
  {
    super(clr);
  }

/**
*Cette méthode permet de regarder les positions de déplacements possibles autour de la pièce.
@param piece qui est la pièce que l'on regarde.
@return liste de couple(IaCouple)
*/
  public IaCouple see(Piece piece)
  {
    Box position = piece.position;
    if(position == null)
    {
      return new IaCouple(piece, 0,new Box[0]);
    }
    Box[] posible = new Box[3]; //liste des suppositions
    Box[] liste = new Box[3];   //liste des vrai positions

    // on crée un tableau de position possible mais pas certains
        /*!! Attention l'ordre à de l'importance !!*/
    posible[0] = Board.findCase(position.x,(position.y)+1);
    posible[1] = Board.findCase((position.x)-1,position.y);
    posible[2] = Board.findCase((position.x)+1,position.y);


    //on vérifie si c'est possible d'aller là;
    int cnt =0;
    for(int j =0; j < 3 ; j++)
    {
        if(posible[j] != null)
        {
          if(!(posible[j].occupe) || posible[j].player() != this)
          {
            liste[cnt] = posible[j];
            cnt++;
          }
        }
    }
    return new IaCouple(piece, cnt, liste);
  }

/**
*Cette méthode permet de créer une liste de déplacement où sont placer les IaCouple.
*/
  public void createListe() //juste de temps en temps
  { //Recomandation : il faudrait garder en mémoire les position des déplacements posible
    //Recomandation : il faudrait éviter de tout recalculer à chaque déplacement ou attaque
    //Recomandation : il faudrait eviter null pointer quand un piont meurt
    int res =0;
    IaCouple[] liste = new IaCouple[maliste.length-1];
    for(int i=1; i<maliste.length ; i++)
    {
      IaCouple cpl = see(maliste[i]);
      liste[res] = cpl;
      res++;
    }
    deplacement = liste;
  }

/**
*Cette méthode permet de regarder les autres cases autour de la case demandée.
*Pour voir si elle existe bien.
@param position que l'on veut regarder.
*/
  public void neighbour(Box position)
  {
    Box[] liste = new Box[4];
    liste[0] = Board.findCase(position.x-1,position.y);
    liste[1] = Board.findCase(position.x+1,position.y);
    liste[2] = Board.findCase(position.x,position.y-1);
    liste[3] = Board.findCase(position.x,position.y+1);
    for(int i=0 ; i<4;i++)
    {
      if (liste[i] != null && liste[i].whoOccupant() != null)
      {
        IaCouple chg = see(liste[i].whoOccupant());
        IaCouple lapiece = research(liste[i].whoOccupant());
        lapiece = chg;
      }
    }
  }

/**
*Trie du tableau de IaCouple pour mettre les pièces qui ont le plus déplacement devant.
* !! méthode inspirée du Cours d'algo Bac1 de Mr Quoitin (Umons) !!
*/
  public void sortingList() //permet de trier le tableau croissantde déplacement
  {
    int elem;
    int j;

    for(int i=1; i <deplacement.length ; i++)
    {
      elem = deplacement[i].nombre;
      IaCouple elem1 = deplacement[i];
      j = i;
      while(j > 0 && deplacement[j-1].nombre > elem)
      {
        deplacement[j] = deplacement[j-1];
        j = j-1;
      }
      deplacement[j] = elem1;
    }
  }

/**
*Cette méthode permet de rechercher une pièce dans la liste des IaCouple et retourner le couple
@param pièce que l'on recherche.
@return iacouple si elle existe sinon null.
*/
  public IaCouple research(Piece piece)
  {
    for(int i =0; i < deplacement.length ; i++)
    {
      if((deplacement[i].piece).equals(piece))
      {
        return deplacement[i];
      }
    }
    return null;
  }

/**
*Cette méthode permet de retourner aléatoirement un couple de pièce.
@return iacouple
*/
  public IaCouple research()
  {/*à refaire pour qu'il donne plus aléatoire*/
    int res = 0;
    for(int i=0; i < (this.deplacement.length-1); i++)
    {
      if(deplacement[i].nombre < deplacement[i+1].nombre)
      {
        res = i+1;
      }
    }
    actuelle = res;
    return deplacement[res];
  }

/**
*Cette méthode permet d'actualisé iaCouple du pion qui a été bouger en dernier pour
*voir les cases disponible autour de lui.
*/
  public void replace() //permet de voir ce qui ce passe après le déplacement
  {
    IaCouple ia = deplacement[actuelle];
    deplacement[actuelle] = see(ia.piece);
  }

/**
*Méthode de déplacement général qui appelle toutes les autres.
*/
  public void displacementPawn()
  {
    IaCouple qui = research();
    Box avant = qui.piece.position;
    qui.piece.displacement(qui.position[0]);
    replace();
    neighbour(avant);
  }
}
