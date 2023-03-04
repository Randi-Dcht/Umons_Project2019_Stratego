import java.util.*;

public class Automatic
{
  public static Random rd = new Random();

  public static ArrayList<Box> creedPlace(Player joueur)
  {
    ArrayList<Box> liste = new ArrayList<Box>();
    for(int i = joueur.wherePlace()[2] ; i <= joueur.wherePlace()[3] ; i++)
    {
      for(int j = joueur.wherePlace()[0] ; j <= joueur.wherePlace()[1] ; j++)
      {
        liste.add(Board.plateau[j][i]);
      }
    }
    return liste;
  }

  public static int research(ArrayList<Box> liste,int x,int y)
  {
    if(0 <= x && x < 10 && 0 <= y && y< 10)
    {
      for(int i = 0 ; i < liste.size() ; i++)
      {
        if(liste.get(i).x == x && liste.get(i).y == y)
        {
          return i;
        }
      }
    }
    return rd.nextInt(liste.size());
  }


/**
*Cette méthode permet de placer les pions de l'ordinateur avec une certaines constante
*comme le drapeau entouré de bombe, etc.
*/
  public static void place(Piece piece,ArrayList<Box>liste)
  {
      int valeur = 0;
      Box place;
        if(piece.monNom.equals(NomPiece.drapeau)) //placer sur la première ligne
        {
          valeur = research(liste,rd.nextInt(10),0);
        }
        else if(piece.monNom.equals(NomPiece.eclaireur)) //placer en première ligne
        {
          valeur = research(liste,rd.nextInt(10),3);
        }
        else
        {
          valeur = rd.nextInt(liste.size());
        }
          place = liste.get(valeur);
          liste.remove(valeur);
          piece.placeMe(place);
  }
}
