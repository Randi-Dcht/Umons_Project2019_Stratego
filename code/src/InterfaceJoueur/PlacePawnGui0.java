//package be.dchtrnd.mesprojets.stratego;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import java.util.*;

/**
*Classe héritant de JavaFx pour l'affichage d'une grille où chaque joueur peut placer ses pions.
*Chaque joueur peut placer ses pions dans l'espace vert.

@param liste qui sont les joueurs qui vont jouer la partie
@param cb le nombre de joueur humain qui joue.
@param image image de fond que l'on peut choisir
@param nom le nom de la partie en cours

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/

public class PlacePawnGui0 extends Application
{
    final String cheminAcces = "../code/Illustration/";
    final String nomPartie;

    public Group plateau = new Group(); //plateau pour placer
    public Group grille = new Group(); //permet de mettre le pion qui doit être placer
    public BorderPane info = new BorderPane(); //info sur la pièce courante

    public Player actuelle;  //joueur qui place le pion maintenant
    public int nbJoueur =0;  //quelle joueur place ses pions
    public Piece pieceActu = null;
    public Player[] joueur;
    public Stage stage;
    public int joueurEnCours=0;
    public boolean terminer = false; //savoir si le joueur
    public String[] paysage;

  public PlacePawnGui0(Player[] liste, int cb,String[] image,String nom)
  {
    joueurEnCours = cb;
    joueur = liste;
    paysage = image;
    nomPartie = nom;
  }

  public void start(Stage primaryStage)
  {
    try
    {
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root,1000,1000);

      actuelle = joueur[0];
      boardPlacePawn(Board.plateau,actuelle);
      whatPawn(actuelle.myList());

      Text txt = new Text("C'est facile ! , placer vos pionts sur les cases vertes");
      txt.setFont(Font.font("Chilanka",FontWeight.BOLD, 25));
      whoPawn();
      root.setTop(txt);
      root.setCenter(plateau);
      root.setRight(grille);
      root.setLeft(info);

      primaryStage.setScene(scene);
      primaryStage.setTitle("Placez vos pions");
      primaryStage.setFullScreen(true);
      primaryStage.setOnCloseRequest(fermer -> fermer.consume());
      primaryStage.show();
      stage = primaryStage;
      grille.setOnMouseClicked(e -> {wherePawn(e.getX(),e.getY());});
      plateau.setOnMouseClicked(e -> {wherePlace(e.getX(),e.getY());});
    }
    catch(Exception e)
    {
      Summary.ERROR(e,"PlacePawnGui");
      primaryStage.show();
      primaryStage.close();
    }
  }

/**
*Cette méthode permet de placer les pions sur le côté droit de l'écran
*si le pion n'est pas encore placé alors l'image de celui-ci s'afficher
*sinon une case noire apparait.
*/
  public void whatPawn(Piece[] liste)
  {
    int y = 0;
    int x = 0;
    terminer = true;
    for(int i =0 ; i < liste.length ; i++)
    {
      if(liste[i].position == null)
      {
        Image ig = new Image(cheminAcces + liste[i].id + ".png");
        ImageView img = new ImageView();
        img.setImage(ig);
        img.setFitWidth(96);
        img.setFitHeight(96);
        img.setLayoutX(x*100);
        img.setLayoutY(y*100);
        grille.getChildren().add(img);
        y++;
        terminer = false;
      }
      else
      {
        Rectangle rct = new Rectangle(x*100,y*100,96,96);
        grille.getChildren().add(rct);
        y++;
      }
      if(y == 10)
      {
        y = 0;
        x++;
      }
    }
  }



/**
*Cette méthode permet d'afficher sur le côté gauche le pion sélectionner lorsque l'on clique
*sur une pièce et lorsque toutes les pièces sont placées alors elle affiche le bouton "suivant"
*pour passer au joueur qui suit.
*/
  public void whoPawn()
  {
    Button ok = new Button("j'ai terminé, suivant");
    ok.setStyle("-fx-text-fill: red; -fx-border-color:yellow; -fx-font-size:3em;");
    info.setBottom(ok);

    if(terminer)
    {
      ok.setVisible(true);
      ok.setOnAction(e ->{
        nbJoueur++;
        endPlace();
        actuelle = actuelle.next();
        whatPawn(actuelle.myList());
        boardPlacePawn(Board.plateau,actuelle);
        whoPawn();
          });
    }
    else
    {
      ok.setVisible(false);
    }
    if(pieceActu != null)
    {
      Group grp = new Group();
      Text txt = new Text("(" + pieceActu.monNom+")");
      txt.setFont(Font.font("Chilanka",FontWeight.BOLD, 20));
      Image ig = new Image(cheminAcces + pieceActu.id +".png");
      ImageView img = new ImageView();
      img.setImage(ig);
      img.setFitWidth(240);
      img.setFitHeight(240);
      txt.setLayoutY(180);
      grp.getChildren().addAll(img,txt);
      info.setCenter(grp);
    }
    else
    {
      Image ig = new Image(cheminAcces + "inco.JPG");
      ImageView img = new ImageView();
      img.setImage(ig);
      img.setFitWidth(250);
      img.setFitHeight(250);
      info.setCenter(img);
    }

  }


/**
*Cette méthode permet d'afficher le plateau de jeu avec les cases vertes où placer ses pions
*et mettre les cases en rouge où le joueur ne peut pas placer ses pions.

@param table qui est le plateau de jeu.
@param joueur qui est le joueur qui place actuellement.
*/
  public void boardPlacePawn(Box[][] table, Player joueur)
  {
    for(int i=0; i < table.length ; i++)
    {
      for(int j =0; j < table.length ; j++)
      {
        if(table[j][i].occupe && table[j][i].whoOccupant() != null && table[j][i].player().equals(joueur))
        {
          Image ig = new Image(cheminAcces + table[j][i].whoOccupant().id + ".png");
          ImageView img = new ImageView();
          img.setImage(ig);
          img.setLayoutX(j*100);
          img.setLayoutY(i*100);
          img.setFitWidth(98);
          img.setFitHeight(98);
          plateau.getChildren().add(img);
        }
        else
        {
          if(j >=joueur.wherePlace()[0] && j<= joueur.wherePlace()[1] && i >= joueur.wherePlace()[2] && i <= joueur.wherePlace()[3])
          {
            Rectangle rct = new Rectangle(j*100,i*100,98,98);
            rct.setFill(Color.GREEN);
            plateau.getChildren().add(rct);
          }
          else
          {
            Rectangle rct = new Rectangle(j*100,i*100,98,98);
            rct.setFill(Color.RED);
            plateau.getChildren().add(rct);
          }
        }
      }
    }
  }

/**
*Lorsque tous les joeurs ont fini de placer leur pièces alors cette méthode va fermer Cette
*fenetre et ouvrir la fenetre de jeux.
*/
  public void endPlace()
  {
    if(nbJoueur == joueurEnCours)
    {
      Stage stg = new Stage();
  /*    for(int  i = 0 ; i < joueur.length ; i++)
      {
        System.out.println(joueur[i].myColor());
        System.out.println(joueur[i].myName());
      }*/
      BoardGui bg = new BoardGui(joueur,Board.plateau,paysage,nomPartie); //<= ici
      bg.start(stg);
      stage.close();
    }
  }


/**
*Cette méthode permet de voir qu'elle pion l'utilisateur veut placer.

@see whoPawn va être appeler pour montrer à l'utilisateur le pion sélectioner.

@param x qui est un double (souris abscices)
@param y qui est un double (souris ordonée)
*/
  public void wherePawn(double x, double y)
  {
    int[] liste = calcul(x,y);
    int valeur = liste[1] + (10*liste[0]);
    pieceActu = actuelle.myList()[valeur];
    if(pieceActu.position != null)
    {
      pieceActu = null;
    }
    whoPawn();
  }

/**
*Cette méthode est appelée lorque l'utilisateur clique sur la grille du milieu pour placer
*ses pions sur les cases vertes.

@param x qui est un double (souris abscices)
@param y qui est un double (souris ordonée)
*/
  public void wherePlace(double x, double y)
  {
    int[] liste = calcul(x,y);
    Box position = Board.findCase(liste[0],liste[1]);
    if(position.occupe)
    {
      position.whoOccupant().removeMe();
      boardPlacePawn(Board.plateau,actuelle);
      whatPawn(actuelle.myList());
      whoPawn();
    }

    if(pieceActu != null)
    {
        if(pieceActu.placeMe(position))
        {
          boardPlacePawn(Board.plateau,actuelle);
          whatPawn(actuelle.myList());
          pieceActu = null;
          whoPawn();
        }
    }
  }
/**
*Cette méthode permet de calculer la position en x et y venat de la position de la souris.
@param x qui est la position de la souris en absices.
@param y qui est la position de la souris en ordonnée.

@return tab qui est un tableau de 2 cases (x et y)
*/
  public int[] calcul(double x, double y)
  {
    int[] tab = new int[2];
    tab[0] = (int)x/100;
    tab[1] = (int)y/100;
    return tab;
  }


}
