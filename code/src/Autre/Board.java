//package be.dchtrnd.mesprojets.stratego;

import java.io.Serializable;

/**
*Cette classe statique permet de créer un plateau et de l'utilise avec ses méthodes..

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class Board implements Serializable
{
  public static Box[][] plateau;

/**
*Cette méthode crée un plateau de cases sur dimensions demandée.
@param x qui est le nombre de colones.
@param y qui est le nombre de lignes.
*/
  public static void createTray(int x, int y)
  {
    Summary.WRITE("createTray : création d'un tableau de " + x + " sur " + y);
    Box[][] tableau = new Box[x][y];
    for(int i =0; i < x ; i++)
    {
      for(int j = 0 ; j < y ; j++)
      {
        tableau[j][i] = new Box(j,i);
      }
    }
    plateau = tableau;
  }

/**
*Cette méthode permet de créer des obstacles sur le plateau de case.
@param liste qui est une liste de couple d'absice et d'ordonnée.
*/
  public static void createBarriers(int[][] liste)
  {
    int cnt=0;
    Summary.WRITE("createBarriers : création de " + liste.length + " obstacles.");
    while(cnt < liste.length)
    {
      findCase(liste[cnt][0],liste[cnt][1]).occupe = true;
      cnt++;
    }
  }

/**
*Cette méthode permet de trouver une case par une absice et une ordonée.
@param x qui est l'absice.
@param y qui est l'ordoné.

@return case qui se trouve à la position demandée.
*/
  public static Box findCase(int x, int y)
  {
    if(0 <= x && x < plateau.length && 0 <= y && y < plateau.length)
    {
      Summary.WRITE("findCase : recherche de la case ["+ x + "];[" + y +"]");
      return plateau[x][y];
    }
    return null;
  }

/**
*Cette méthode permet d'afficher le plateau dans la console.
*/
  public static void print()
  {
    for(int i =0; i < plateau.length ; i++)
    {
      for(int j = 0 ; j < (plateau.length-1) ; j++)
      {
        System.out.print(plateau[j][i]);
      }
      System.out.println(plateau[plateau.length-1][i]);
    }
  }

}
