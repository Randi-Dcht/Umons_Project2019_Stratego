//package be.dchtrnd.mesprojets.stratego;

import java.io.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;

/**
*Cette classe permet de sauveguarder dans une fichier la partie actuelle lorque l'on quitte le plateau.
@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class SaveOldGame
{
/**
*Cette méthode sauveguarde dans un fichier binaire la partie.
@param nom du fichier qui est le nom de la partie.
@param joueurs qui sont les joueurs qui ont jouée la partie.
@param plateauJeu qui est le plateau de jeu avec les pièces.
@param paysage qui permet de sauveguarder le paysage choisie par l'utilisateur.
*/
  public static void saveGame(String fichier,Player[] joueurs, Box[][] plateauJeu, String[] paysage)
  {
    try
    {
      ObjectOutputStream sortie;
      sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("misc/FichierSauvegarde/" + fichier+".save"))));
      sortie.writeObject(joueurs);
      sortie.writeObject(plateauJeu);
      sortie.writeObject(paysage);
      sortie.close();
    }
    catch(IOException e)
    {
      Summary.ERROR(e,"saveGame");
    }
  }

/**
*Cette méthode permet d'ouvrir une partie déjà commencée.
@param nom du fichier de la partie.
*/
  public static void oldGame(String fichier)
  {
    try
    {
      ObjectInputStream entree;
      entree = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("misc/FichierSauvegarde/" + fichier +".save"))));
      Player[] liste = (Player[]) entree.readObject();
      Box[][] plateau = (Box[][]) entree.readObject();
      String[] paysage = (String[]) entree.readObject();
      /* -- permet de démarer la partie demandé (ancienne) -- */
      Stage stg = new Stage();
      Summary.REOPEN(fichier);
      BoardGui bg = new BoardGui(liste,plateau,paysage,fichier);
      Board.plateau = plateau;
      bg.start(stg);
    }
    catch(ClassNotFoundException  e)
    {
      Summary.ERROR(e,"oldGame");
    }
    catch (FileNotFoundException e)
    {
      Summary.ERROR(e,"oldGame");
    }
    catch (IOException e)
    {
      Summary.ERROR(e,"oldGame");
    }
  }

/**
*Cette méthode permet de supprimer le fichier binaire de la partie lorsque celle-ci est finie.
@param fichier (String)
*/
  public static void finishGame(String fichier)
  {
    try
    {
      File supprime = new File("misc/FichierSauvegarde/" + fichier +".save");
      supprime.delete();
    }
    catch(Exception e)
    {
      Summary.ERROR(e,"finishGame");
    }
  }

/**
*Cette méthode permet de nettoyer une chaine de caractère.
@param chaine qui est le String à nettoyer.
*/
  public static String analyser(String chaine)
  {
    String[] decoupe  = chaine.split("[/.]");
    return decoupe[2];
  }
}
