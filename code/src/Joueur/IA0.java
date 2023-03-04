//package be.dchtrnd.mesprojets.stratego;
import java.util.*;

/**
*Cette classe permet de définir le comportement d'un joueur électronique aléatoirement.
@param Couleur qui est la couleur de ce joueur durant le jeu.
*/
public class IA0 extends ComputerPlayer
{
  private ArrayList<IaCouple> listePion;
  private int compteur = 0;
  private int[] xx = {1,-1,0,0};
  private int[] yy = {0,0,1,-1};

  public IA0(Couleur clr)
  {
    super(clr);
  }

  public void createListe()
  {
    listePion = new ArrayList<IaCouple>();
    for(int i = 0 ; i < maliste.length ; i++)
    {
      listePion.add(createCouple(maliste[i]));
    }
  }

  public void dead()
  {
    for(int a = 0 ; a < listePion.size() ; a++)
    {
      if(listePion.get(a).piece.position == null)
      {
        listePion.remove(a);
      }
    }
    super.dead();
  }

  public IaCouple createCouple(Piece piece)
  {
    int x = piece.position.x;
    int y = piece.position.y;
    int cnt = 0;
    Box[] liste = new Box[4];
    for(int i = 0 ; i <= 4 ; i++)
    {
      Box endroit = Board.plateau[x + xx[i]][y + yy[i]];
      if(endroit != null && piece.possible(endroit))
      {
        liste[cnt] = endroit;
        cnt++;
      }
    }
    return new IaCouple(piece,cnt,liste);
  }

  public void displacementPawn()
  {
    //rien
  }
}
