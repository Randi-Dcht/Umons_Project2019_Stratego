//package be.dchtrnd.mesprojets.stratego;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.scene.effect.BoxBlur;
import java.io.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.effect.*;
import javafx.util.*;
import javafx.animation.*;

/**
*Cette classe permet de jouer au jeu avec la grille au centre et sur le côté gauche afficher
*le déroulement de la partie et sur le côté droit les pions attrapé.

@param jr qui est une liste de joueur.
@param plateau qui est le plateau de jeu pour jouer.
@param image qui est l'image de fond de la fenetre.
@param nom qui est le nom de la partie en cours.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class BoardGui extends Application
{
  final String cheminAcces = "../code/Illustration/";
  final String nomPartie;

  public Player[] joueur;           //les joueurs de la partie (utile endGame)
  public Box[][] plateau;
  private Player actuelle = null;   //joueur qui joue le tours maintenant
  final String[] paysage;
  final int pieceDepart;            //permet de savoir combien de pièces avait les joueurs en commençant

  private GridPane plateauJeu    = new GridPane();       //pour le plateau de jeu principale
  private GridPane plateauPion;                          //pour mettre les pionts du joeurs (max 4 joueurs !!)
  private BorderPane gaucheInfo  = new BorderPane();     //pour afficher les information sur le jeu
  private BorderPane droitePion  = new BorderPane();
  private Button chg             = new Button();
  private Group texteGroup;
  private Group imageGroup;
  private Stage primaryStage;
  private ImageView[][] PieceImage = new ImageView[10][10];  //<= voir ici

  private int valeurTour    = 0;     //savoir si on attratpe une pièce ou place
  private Piece qui         = null;  //savoir qu'elle pièce on a attrapé
  private ImageView image   = null;  //image à bouger ou supprimer de la pièce
  private int[] posit       = new int[2];


  public BoardGui(Player[] jr,Box[][] plateau_,String[] image,String nom)
  {
    joueur = jr;
    plateau = plateau_;
    pieceDepart = joueur[0].howMany();
    Random rd = new Random();
    actuelle = joueur[rd.nextInt(jr.length)];
    paysage = image;
    nomPartie = nom;
  }

  public void start(Stage primaryStage)
  {
    try
    {
      BorderPane root = new BorderPane();//c'est le Group principale qui contient les trois autres
      root.setStyle("-fx-background-image: url("+cheminAcces + paysage[0] +");");
      BorderPane information = new BorderPane();
      Scene scene = new Scene(root,1500,2000);

      initializationBoard();
      addPawnBoard();
      informationPlayer();
      initializationMyPawn();
      RefreshBoard();
      plateauJeu.setHgap(5);
      plateauJeu.setVgap(5);
      droitePion.setCenter(plateauPion);

  /* -- Bouton pour quitter la partie et sauveguarder la partie si elle n'est pas terminer dans un fichier spécifique -- */
      Button partir = new Button("quitter la partie");
      partir.setLayoutX(0);
      partir.setStyle("-fx-text-fill: #FF4000; -fx-border-color: red; -fx-background-color: #F5D0A9; -fx-font-size:2em;");
      partir.setOnAction(e -> {
        primaryStage.close();
        SaveOldGame.saveGame(nomPartie,joueur,plateau,paysage);
        Summary.CLOSE();
        Stage stages = new Stage();
        MainGUI gui = new MainGUI();
        gui.start(stages);
      });
      droitePion.setBottom(partir);


  /* -- Bouton qui permet de connecter à la page internet pour les règles et  changer de pièce-- */
      HBox bts = new HBox();
      Button info = new Button("?"); //être rond
      info.setTooltip(new Tooltip("besoin d'aide"));
      info.setShape(new Circle(10));
      info.setStyle("-fx-text-fill: #FF4000; -fx-border-color: blue; -fx-font-size:5em;");
      info.setPrefWidth(90);
      info.setPrefHeight(90);
      info.setOnAction(e ->{
          Stage stage = new Stage();
          PageWeb pw = new PageWeb("http://mesprojets.dchtrnd.be/stratego.html#Jouer");
          pw.start(stage);
        });
      bts.getChildren().add(info);

      ImageView interro = new ImageView(new Image(getClass().getResourceAsStream(cheminAcces + "chg.png")));
      chg.setTooltip(new Tooltip("chnager de pion "));
      chg.setPrefWidth(90);
      chg.setPrefHeight(90);
      interro.setFitHeight(50);
      interro.setFitWidth(50);
      chg.setGraphic(interro);
      chg.setShape(new Circle(10));
      chg.setOnAction(e ->{
        valeurTour = 0;
        gaucheInfo.getChildren().remove(imageGroup);
        qui        = null;
        image      = null;
        });
      bts.getChildren().add(chg);
      bts.setSpacing(7);
      information.setBottom(bts);
      chg.setVisible(false);

  /* -- mettre les titres des colones */
      Text txt = new Text("Vos informations : ");
      txt.setFont(Font.font("Chilanka",FontWeight.BOLD,32));
      information.setTop(txt);
      Text txt2 = new Text("Pions capturés : ");
      txt2.setFont(Font.font("Chilanka",FontWeight.BOLD,32));
      droitePion.setTop(txt2);

  /* -- Affichage autres group -- */
      information.setCenter(gaucheInfo);
      plateauJeu.setPadding(new Insets(10,10,10,10));

  /* -- Affichage des tois Group dans le Group principale -- */
      root.setCenter(plateauJeu);
      root.setRight(droitePion);
      root.setLeft(information);
      root.setPadding(new Insets(20,10,10,10));

  /* -- Méthode pour le primaryStage de cette fenetre -- */
      primaryStage.setScene(scene);
      primaryStage.setFullScreen(true);
      primaryStage.setTitle("Plateau de Jeu du Stratego");
      primaryStage.setOnCloseRequest(fermer -> fermer.consume()); //éviter que l'utilisateur quitte sans sauvegarder
      primaryStage.show();
      this.primaryStage = primaryStage;

  /* -- Ici sont repris tous les écouteurs pour la souris  --*/
      plateauJeu.setOnMouseClicked(e ->
      {
        BoardClick(e.getX(),e.getY(),e.getTarget());
      });
    }
    catch(Exception e)
    {
      Summary.ERROR(e,"BoardGui");
    }
  }

/**
*Cette méthodes permet d'afficher lors du démarage de la fenetre le plateau de jeu avec
*les cases vides et les obstacles.
*/
  public void initializationBoard()
  {
    for(int i = 0 ; i < plateau.length ; i++)
    {
      for(int j = 0 ; j < plateau.length ; j++)
      {
        if(plateau[j][i].occupe && plateau[j][i].whoOccupant() == null)
        {
          Image ig = new Image(cheminAcces + paysage[1]);
          ImageView img = new ImageView();
          img.setImage(ig);
          img.setFitHeight(95);
          img.setFitWidth(95);
          plateauJeu.add(img,j,i);
        }
        else
        {
          Rectangle rectangle = new Rectangle();
          rectangle.setWidth(95);
          rectangle.setHeight(95);
          rectangle.setFill(Color.TRANSPARENT);
          rectangle.setStroke(Color.BLACK);
          plateauJeu.add(rectangle,j,i);
        }
      }
    }
  }


/**
*Cette méthode permet d'afficher sur le côté droit de la fentre les pions capturés par le joueur actuelle
*/
  public void initializationMyPawn()
  {
    GridPane grille = new GridPane();
    grille.setHgap(3);
    grille.setVgap(3);
      int colone = 0;
      int ligne  = 0;
      for(int cnt =0 ; cnt < actuelle.captureList().length ; cnt++)
      {
        if(ligne >= 10)
        {
          ligne = 0;
          colone++;
        }
        Image ig = new Image(cheminAcces + actuelle.captureList()[cnt].id + ".png");
        ImageView img = new ImageView();
        img.setImage(ig);
        img.setFitHeight(55);
        img.setFitWidth(55);
        Rectangle rct = new Rectangle();
        rct.setWidth(55);
        rct.setHeight(55);
        Color clr = Color.rgb(actuelle.captureList()[cnt].joueur.myColor().rouge, actuelle.captureList()[cnt].joueur.myColor().vert, actuelle.captureList()[cnt].joueur.myColor().bleu);
        rct.setFill(Color.TRANSPARENT);
        rct.setStroke(clr);
        rct.setStrokeWidth(4);
        grille.add(img,colone,ligne);
        grille.add(rct,colone,ligne);
        ligne++;
      }
      plateauPion = grille;
  }


  /**
  *Cette méthode permet de d'afficher les pièces sur la grille lors de la premières exécution.
  */
  public void addPawnBoard()
  {
    for(int i =0 ; i < plateau.length ; i++)
    {
      for(int j =0 ; j < plateau.length ; j++)
      {
        if(plateau[j][i].occupe && plateau[j][i].whoOccupant() != null)
        {
          Color clr = Color.rgb(plateau[j][i].player().myColor().rouge, plateau[j][i].player().myColor().vert, plateau[j][i].player().myColor().bleu);
          Lighting lighting = new Lighting();
          lighting.setLight(new Light.Distant(100, 100, clr));
          Image ig = new Image(cheminAcces + plateau[j][i].whoOccupant().id + ".png");
          ImageView img = new ImageView();
          img.setImage(ig);
          img.setFitHeight(93);
          img.setFitWidth(93);
          img.setEffect(lighting);
          PieceImage[j][i] = img;
          plateauJeu.add(img,j,i);
        }
      }
    }
  }

/**
*Cette méthode permet de retourner les images qui ne sont pas les images du joueur actuelle.
*/
  public void RefreshBoard()
  {
    for(int i = 0 ; i < plateau.length ; i++)
    {
      for(int j = 0 ; j < plateau.length ; j++)
      {
        if(plateau[j][i].occupe && plateau[j][i].whoOccupant() != null && plateau[j][i].player().equals(actuelle))
        {
          Color clr = Color.rgb(plateau[j][i].player().myColor().rouge, plateau[j][i].player().myColor().vert, plateau[j][i].player().myColor().bleu);
          Lighting lighting = new Lighting();
          lighting.setLight(new Light.Distant(100, 100, clr));
          PieceImage[j][i].setEffect(lighting);
        }
        if(plateau[j][i].occupe && plateau[j][i].whoOccupant() != null && !plateau[j][i].player().equals(actuelle))
        {
          Color clr = Color.BLACK;
          Lighting lighting = new Lighting();
          lighting.setLight(new Light.Distant(0,0, clr));
          PieceImage[j][i].setEffect(lighting);
        }
      }
    }
  }

/**
*Cette méthode permet de gérer le jeu à chaque fois que l'utilisateur va cliquer sur la fenetre central.
*Elle va bouger les pièces et appeler les autres méthodes pour afficher les pièces sur le côté ou le bon joueur.

@param x qui est la position en absice.
@param y qui est la position en ordonée.
@param img qui l'image de la pièce sélectionnée
*/
  public void BoardClick(double x, double y, EventTarget img)
  {
    int x1 = (int) x/100;
    int y1 = (int) y/100;
/* --  Pion que l'on attrape -- */
    if(valeurTour == 0 && Board.findCase(x1,y1) != null)
    {
      if(plateau[x1][y1].occupe && plateau[x1][y1].whoOccupant() != null && plateau[x1][y1].whoOccupant().joueur.equals(actuelle))
      {
        posit[0] = x1;
        posit[1] = y1;
        qui = plateau[x1][y1].whoOccupant();
        image = (ImageView) img;
        informationPawn();
        chg.setVisible(true);
        valeurTour = 1;
      }
    }
/* -- Ou doit aller le pion que l'on a attrapé -- */
    else if(valeurTour == 1 && Board.findCase(x1,y1) != null)
    {
      if(qui.displacement(plateau[x1][y1]))
      {
        plateauJeu.getChildren().remove(image);
    /* -- Il n'y a pas eu d'attaque -- */
        if(!qui.jattaque)
        {
          PieceImage[posit[0]][posit[1]] = null;
          PieceImage[x1][y1] = image;
          plateauJeu.add(image,x1,y1);
        }
    /* -- il y a eu une attaque -- */
        if(qui.jattaque)
        {
          image = (ImageView) img;
          plateauJeu.getChildren().remove(image);
          if(qui.jattaque && plateau[x1][y1].whoOccupant() != null)
          {
            Color clr = Color.rgb(plateau[x1][y1].player().myColor().rouge, plateau[x1][y1].player().myColor().vert, plateau[x1][y1].player().myColor().bleu);
            Lighting lighting = new Lighting();
            lighting.setLight(new Light.Distant(100, 100, clr));
            Image ig = new Image(cheminAcces + plateau[x1][y1].whoOccupant().id + ".png");
            ImageView imgv = new ImageView();
            imgv.setImage(ig);
            imgv.setFitHeight(93);
            imgv.setFitWidth(93);
            imgv.setEffect(lighting);
            PieceImage[posit[0]][posit[1]] = null;
            PieceImage[x1][y1] = imgv;
            plateauJeu.add(imgv,x1,y1);
          }
        }
        chg.setVisible(false);
        gaucheInfo.getChildren().remove(texteGroup);
        gaucheInfo.getChildren().remove(imageGroup);
        droitePion.getChildren().remove(plateauPion);
        hidden();
        exitGame(actuelle);
        valeurTour = 0;
        actuelle = actuelle.next();
        RefreshBoard();
        if(actuelle.myName().equals("Ordinateur"))
        {
          ComputerPlayer ordinateur = (ComputerPlayer) actuelle;
          ordinateur.displacementPawn();
          visible();
          actuelle = actuelle.next();
          RefreshBoard();
        }
        else
        {
          Timeline suivant = new Timeline(new KeyFrame(Duration.seconds(3), action ->
          {
            visible();
          }));
          suivant.play();
        }
        informationPlayer();
        initializationMyPawn();
        droitePion.setCenter(plateauPion);
      }
    }
  }

/**
*Cette méthode permet de cacher la fentre entre chaque de tour de joeurs humains
*/
  public void hidden()
  {
    plateauJeu.setVisible(false);
    droitePion.setVisible(false);
  }

/**
*Cette méthode permet d'aficher la fentre entre chaque tour de joeur humain.
*/
  public void visible()
  {
    plateauJeu.setVisible(true);
    droitePion.setVisible(true);
  }

/**
*Cette méthode permet de fermer cette fenetre et d'ouvrir la fenetre avec le gagnant de la partie.
*Elle va aussi effacer la suaveguarde la partie.

@param gagant qui est le joueur qui a gagné.
@see WinGui pour afficher le gagant.
*/
  public void exitGame(Player gagnant)
  {
    if(StateGame.endGame(joueur))
    {
      Stage stg = new Stage();
      SaveOldGame.finishGame(nomPartie);
      primaryStage.close();
      WinGui gui = new WinGui(gagnant);
      gui.start(stg);
    }
  }

/**
*Cette méthode permet d'afficher le pion sélectioner par l'utilisateur sur le côté gauche
*/
  public void informationPawn()
  {
    imageGroup = new Group();
    Image ig = new Image(cheminAcces + qui.id + ".png");
    ImageView img = new ImageView();
    img.setImage(ig);
    img.setFitHeight(200);
    img.setFitWidth(200);
    Text txt = new Text("("+qui.monNom+")");
    txt.setFont(Font.font("Chilanka",FontWeight.BOLD,20));
    txt.setLayoutY(210);
    txt.setLayoutX(40);
    imageGroup.getChildren().addAll(img,txt);
    gaucheInfo.setBottom(imageGroup);
  }


/**
*Cette méthode permet d'afficher les informations sur le joueur qui joue actuellement sur le côté gauche.
*/
  public void informationPlayer()
  {
    texteGroup = new Group();
    Color clr = Color.rgb(actuelle.myColor().rouge, actuelle.myColor().vert, actuelle.myColor().bleu);
    Text txt1 = new Text("C'est à votre tour : ");
    Text txt12 = new Text(actuelle.myName());
    txt12.setStyle("-fx-background-color:#F8FBEF;");
    Text txt2 = new Text("Pièces déplaçables : ");
    Text txt22 = new Text(" " + actuelle.howMany());
    Text txt3 = new Text("Pièces perdues : " );
    Text txt32 = new Text(" " + (pieceDepart - actuelle.howMany()));
    txt12.setLayoutX(25);
    txt22.setLayoutX(60);
    txt32.setLayoutX(60);
    txt1.setFont(Font.font("Chilanka",FontWeight.BOLD, 25));
    txt12.setFont(Font.font("Chilanka",FontWeight.BOLD, 25));
    txt12.setFill(clr);
    txt2.setFont(Font.font("Chilanka",FontWeight.BOLD, 25));
    txt22.setFont(Font.font("Chilanka",FontWeight.BOLD, 25));
    txt22.setFill(clr);
    txt3.setFont(Font.font("Chilanka",FontWeight.BOLD, 25));
    txt32.setFont(Font.font("Chilanka",FontWeight.BOLD, 25));
    txt32.setFill(clr);
    txt1.setLayoutY(50);
    txt12.setLayoutY(80);
    txt2.setLayoutY(150);
    txt22.setLayoutY(180);
    txt3.setLayoutY(250);
    txt32.setLayoutY(280);
    texteGroup.getChildren().addAll(txt1,txt12,txt2,txt22,txt3,txt32);
    gaucheInfo.setCenter(texteGroup);
  }
}
