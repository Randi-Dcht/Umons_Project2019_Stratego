//package be.dchtrnd.mesprojets.stratego;

import java.io.*;
import java.util.*;
import java.text.*;
import javafx.stage.Stage;

/**
*Cette classe permet d'écrire dans un fichier les informations sur la partie en cours.
@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class Summary
{
  private static PrintWriter ecrire;
  private static int nb = 1;

/**
*Cette méthode permet de voir le nom de tous les fichiers dans un répertoire.
*/
  public static File[] listItems()
  {
    File repertoire = new File("misc/FichierSauvegarde/");
    File[] fichiers =repertoire.listFiles();
    return fichiers;
  }

/**
*Cette méthode permet de continuer d'écrire le résumer dans un fichier qui existe déjà.
@param fichier qui est le nom du fichier
*/
  public static void REOPEN(String fichier)
  {
    try
    {
      ecrire = new PrintWriter(new BufferedWriter(new FileWriter("./misc/FichierResumer/"+ fichier +".rsm",true)));
      ecrire.println("_____________________________________________________________________________________");
      ecrire.println("La partie continue ...");
    }
    catch(Exception e)
    {
      ERROR(e,"reopen");
    }
  }

  /**
  *Cette méthode permet de créer un fichier et d'écrire dedans.
  @param fichier qui est le fichier où on veut ecrire.
  */
  public static void OPEN(String fichier)
  {
    try
    {
      ecrire = new PrintWriter(new BufferedWriter(new FileWriter("./misc/FichierResumer/"+ fichier +".rsm")));
    }
    catch(IOException e)
    {
      ERROR(e,"open");
    }
    ecrire.println("Une nouvelle partie démarre ...");
    ecrire.println("nom de la méthode   :      explication");
    ecrire.println("___________________________________________________");
  }

/**
*Cette méthode permet d'écrire dans un fichier déjà ouvert par les méthodes précédentes.
@param phrase qui est String que l'on veut écrire dans le fichier.
*/
  public static void WRITE(String phrase)
  {
    ecrire.println(phrase);
  }

/**
*Cette méthode permet de créer un fichier et d'écrire dedans l'erreur qui est passer en paramètre.
*Puis de fermer ce fichier et de prévenir les utilisateurs d'une éventuelle erreur.
@param erreur qui est l'erreur générer par java.
@param ou qui est la mèthode qui génère l'erreur.
*/
  public static void ERROR(Exception erreur, String ou)
  {
    try
    {
      PrintWriter fichierErreur = new PrintWriter(new BufferedWriter(new FileWriter("./misc/FichierErreur/erreur-"+ nb +".error")));
      fichierErreur.println("Rapport d'erreur du jeu Stratego 2019");
      fichierErreur.println("________________________________________________________");
      fichierErreur.println("Merci d'envoyer ce fichier via le lien à https://dochot.be/projects/stratego-umons/");
      fichierErreur.println("Veillez nous pour le dérangemet occasioné lors de cette erreur");
      fichierErreur.println("________________________________________________________");
      DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      Date date = new Date();
      fichierErreur.println(" * l'erreur s'est produite : " + format.format(date)); //<= changer ici
      fichierErreur.println(" * sous le système d'exploitation : " + System.getProperty("os.name")); //<= changer ici
      fichierErreur.println(" * Cette erreur est la " + nb + "ème erreur");
      fichierErreur.println(" * Localisation de l'erreur est :  " + ou);
      fichierErreur.println(" * Ce que java dit de l'erreur :  "  + erreur);
      fichierErreur.println("________________________________________________________");
      fichierErreur.println("@auteur: Dochot.be");
      fichierErreur.println("#projet 2019 Bachelier Informatique Umons sur Stratego");
      fichierErreur.close();
    }
    catch(IOException e)
    {
      WRITE("c'erreur du ERROR");
    }
    finally
    {
      System.out.println("Envoyer le fichier" + " erreur-"+nb+"-.error " +" se trouvant dans le dossier erreur à Merci d'envoyer ce fichier via le lien à https://dochot.be/projects/stratego-umons, Merci !");
      nb++;
      CLOSE();
      Stage stage = new Stage();
      PageWeb pw = new PageWeb("https://dochot.be/projects/stratego-umons/");
      pw.start(stage);
    }
  }

  /**
  *Cette méthode permet de fermer un fichier que l'on est entrain d'écrire.
  */
  public static void CLOSE()
  {
    ecrire.println("___________________________________________________");
    ecrire.println("@auteur: Dochot.be");
    ecrire.println("#projet Bachelier Informatique Umons 2019");
    ecrire.close();
  }
}
