//package be.dchtrnd.mesprojets.stratego;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.media.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.effect.InnerShadow;
import javafx.collections.*;
import javafx.geometry.*;
import java.io.File;
import java.text.*;
import java.util.*;

/**
*Cette classe permet d'afficher le menu du jeux où les utilisateurs peuvent choisir leur
*adversaire, couleur, nom ou choisir de continuer une anacienne partie déjà initialiser.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/

public class MainGUI extends Application
{
  private BorderPane root    = new BorderPane();
  private Stage primaryStage = new Stage();
  private VBox root2;
  private VBox vb            = new VBox();
  private TextField[] liste  = new TextField[4];
  private ChoiceBox[] liste2 = new ChoiceBox[4];
  private String[] paysage   = {"foret.PNG","roche.png"};
  private int joueur;
  protected String nomPartie;
  final String cheminAcces = "../code/Illustration/"; //permet de dire pour l'ensemble des fichier image et vidéo, où il se trouvent dans le dossier.

  public void start(Stage primaryStage)
  {
    root.setStyle("-fx-background-color:#ECF6CE; -fx-background-image: url('../code/Illustration/imagefond2.JPG');-fx-background-repeat: no-repeat; -fx-background-position: center;");
    Stage stagesecondaire = new Stage();
    Scene scene = new Scene(root,1850,910);
    try
    {

      /* -- Les sous titre des posibilité de jeu -- */
          Text seultxt = new Text("Le mode solo :");
          seultxt.setFill(Color.RED);
          seultxt.setStroke(Color.GOLDENROD);
          seultxt.setFont(Font.font("Chilanka",FontWeight.BOLD, 35));
          Text multitxt = new Text("Le mode multijoueurs :");
          multitxt.setFill(Color.RED);
          multitxt.setStroke(Color.GOLDENROD);
          multitxt.setFont(Font.font("Chilanka",FontWeight.BOLD, 35));
          Text anciennetxt = new Text("Vos ancienne(s) partie(s) :");
          anciennetxt.setFill(Color.RED);
          anciennetxt.setStroke(Color.GOLDENROD);
          anciennetxt.setFont(Font.font("Chilanka",FontWeight.BOLD, 35));

      /* -- les boutons de choix de partie de jeu -- */
          Button seul   = new Button("classique seul");
          Button deux   = new Button("classique à deux");
          Button quatre = new Button("classique à quatre");
          Button dc1    = new Button("forêt");
          Button dc2    = new Button("desert");
          Button dc3    = new Button("champs");
          Button ok     = new Button("OK");
          ComboBox<String> ancienne = new ComboBox();
          File[] listefichier = Summary.listItems();
          ancienne.setPrefWidth(300);
          for(int nb = 0 ; nb < listefichier.length ; nb++)
          {
            ancienne.getItems().add(SaveOldGame.analyser(listefichier[nb].toString()));
          }
          Slider ia = new Slider();
          ia.setMin(1);
          ia.setMax(2);
          ia.setShowTickLabels(true);
          ia.setBlockIncrement(1);
      /* -- Placement des composants pour les choix --*/
          root2 = new VBox();
          HBox lg1     = new HBox();
          HBox lg2     = new HBox();
          root2.setSpacing(30);
          lg1.setSpacing(25);
          lg2.setSpacing(25);
          lg1.getChildren().add(seul);
          lg1.getChildren().add(ia);
          lg2.getChildren().add(deux);
          lg2.getChildren().add(quatre);
          root2.getChildren().add(seultxt);
          root2.getChildren().add(lg1);
          root2.getChildren().add(multitxt);
          root2.getChildren().add(lg2);
          root2.getChildren().add(anciennetxt);
          root2.getChildren().add(ancienne);

      /* -- Ecouteur pour les boutons du Main --*/
          seul.setOnAction(actionBouton ->
          {
            yourChoices(1);
          });
          deux.setOnAction(actionBouton ->
          {
            yourChoices(2);
          });
          quatre.setOnAction(actionBouton ->
          {
            yourChoices(4);
          });
          ok.setOnAction(placerpion ->
          {
            goToPlace();
          });
          ancienne.valueProperty().addListener(lancerpartie->
          {
            //System.out.println(ancienne.getValue());
            SaveOldGame.oldGame(ancienne.getValue());
            primaryStage.close();
          });
          dc1.setOnAction(decor ->
          {
            String[] image = {"foret.PNG","roche.png"};
            paysage = image;
          });
          dc2.setOnAction(decor ->
          {
            String[] image = {"desert.PNG","pyram.PNG"};
            paysage = image;
          });
          dc3.setOnAction(decor ->
          {
            String[] image = {"champs.PNG","trou.PNG"};
            paysage = image;
          });

      /* -- tout ce qui est là est pour l'image de fond -- */
          Image ig = new Image(cheminAcces + "imagefond2.JPG");
          ImageView img = new ImageView();
          img.setImage(ig);
          img.setFitHeight(scene.getHeight()-30);
          img.setFitWidth(scene.getWidth());

      /* -- choix des joueurs pour leur couleur et leur pseudo -- */
          HBox hb1 = new HBox();
          TextField spd1 = new TextField();
          ChoiceBox<Couleur> cb1 = new ChoiceBox<Couleur>(FXCollections.observableArrayList(new Couleur(247,35,12,"rouge"),new Couleur(128,208,208,"bleu")));
          spd1.setText("joueur1-Speudo");
          spd1.setStyle("-fx-font-size:4em; -fx-border-color: #9AFE2E; -fx-text-fill:#0000FF; -fx-background-color:#F8FBEF; ");
          cb1.setStyle("-fx-font-size:4em; -fx-border-color: #9AFE2E; -fx-text-fill:#0000FF; -fx-background-color:#F6CECE; ");
          cb1.setPrefWidth(300);
          spd1.setVisible(false);
          cb1.setVisible(false);
          liste[0] = spd1;
          liste2[0] = cb1;
          hb1.getChildren().addAll(spd1,cb1);

          HBox hb2 = new HBox();
          TextField spd2 = new TextField();
          ChoiceBox<Couleur> cb2 = new ChoiceBox<Couleur>(FXCollections.observableArrayList(new Couleur(203,201,184,"orange"),new Couleur(159,232,85,"vert")));
          spd2.setText("joueur2-Speudo");
          spd2.setStyle("-fx-font-size:4em; -fx-border-color: #9AFE2E; -fx-text-fill:#0000FF; -fx-background-color:#F8FBEF;");
          cb2.setStyle("-fx-font-size:4em; -fx-border-color: #9AFE2E; -fx-text-fill:#0000FF; -fx-background-color:#F6CECE; ");
          cb2.setPrefWidth(300);
          spd2.setVisible(false);
          cb2.setVisible(false);
          liste[1] = spd2;
          liste2[1] = cb2;
          hb2.getChildren().addAll(spd2,cb2);

          HBox hb3 = new HBox();
          TextField spd3 = new TextField();
          ChoiceBox<Couleur> cb3 = new ChoiceBox<Couleur>(FXCollections.observableArrayList(new Couleur(224,205,169,"gris"),new Couleur(205,205,107,"jaune")));
          spd3.setText("joueur3-Speudo");
          spd3.setStyle("-fx-font-size:4em; -fx-border-color: #9AFE2E; -fx-text-fill:#0000FF; -fx-background-color:#F8FBEF; ");
          cb3.setStyle("-fx-font-size:4em; -fx-border-color: #9AFE2E; -fx-text-fill:#0000FF; -fx-background-color:#F6CECE; ");
          cb3.setPrefWidth(300);
          spd3.setVisible(false);
          cb3.setVisible(false);
          liste[2] = spd3;
          liste2[2] = cb3;
          hb3.getChildren().addAll(spd3,cb3);

          HBox hb4 = new HBox();
          TextField spd4 = new TextField();
          ChoiceBox<Couleur> cb4 = new ChoiceBox<Couleur>(FXCollections.observableArrayList(new Couleur(214,111,210,"rose"),new Couleur(120,230,210,"bleu")));
          spd4.setText("joueur4-Speudo");
          spd4.setStyle("-fx-font-size:4em; -fx-border-color: #9AFE2E; -fx-text-fill:#0000FF; -fx-background-color:#F8FBEF; ");
          cb4.setStyle("-fx-font-size:4em; -fx-border-color: #9AFE2E; -fx-text-fill:#0000FF; -fx-background-color:#F6CECE; ");
          cb4.setPrefWidth(300);
          spd4.setVisible(false);
          cb4.setVisible(false);
          liste[3] = spd4;
          liste2[3] = cb4;
          ok.setStyle("-fx-font-size:4em; -fx-border-color:red; -fx-text-fill:red");
          hb4.getChildren().addAll(spd4,cb4);

          HBox hb5 = new HBox();
          hb5.setSpacing(10);
          Text message = new Text("Choisissez votre décors:");
          message.setStyle("-fx-font-size:3em; -fx-text-fill: #0404B4;");
          dc1.setStyle("-fx-font-size:3em; -fx-border-radius:30; -fx-background-color: #A5DF00; -fx-border-color: #04B404; -fx-background-radius: 30");
          dc2.setStyle("-fx-font-size:3em; -fx-border-radius:30; -fx-background-color: #F5F6CE; -fx-border-color: #FFFF00; -fx-background-radius: 30");
          dc3.setStyle("-fx-font-size:3em; -fx-border-radius:30; -fx-background-color: #FACC2E; -fx-border-color: #04B404; -fx-background-radius: 30");
          hb5.getChildren().addAll(message,dc1,dc2,dc3);

          vb.getChildren().addAll(hb1,hb2,hb3,hb4,hb5,ok);
          vb.setSpacing(50);

      /* -- tout ce qui est pour le menu --*/
          MenuBar menuprincipal = new MenuBar();
          Menu aide = new Menu("aide");
          Menu inf  = new Menu("Information");
          Menu opt  = new Menu("option");
          MenuItem erreur  = new MenuItem("fichier d'erreur");
          MenuItem erreur2 = new MenuItem("Erreur du jeu") ;
          MenuItem bck     = new MenuItem("Fichier résumer");
          MenuItem src     = new MenuItem("Source du projet");
          MenuItem quit    = new MenuItem("quitter le jeu");
          MenuItem rgl     = new MenuItem("comment utiliser application");
          MenuItem solo    = new MenuItem("mode seul ou deux joueur");
          MenuItem multi   = new MenuItem("mode quatre joueur");
          inf.getItems().addAll(erreur,erreur2,bck,src);
          aide.getItems().addAll(rgl,solo,multi);
          opt.getItems().add(quit);
          menuprincipal.getMenus().addAll(inf,aide,opt);

          multi.setOnAction(e ->
          {
            Stage stage = new Stage();
            PageWeb pw = new PageWeb("http://mesprojets.dchtrnd.be/stratego.html#Multijoueur");
            pw.start(stage);
          });
          solo.setOnAction(e ->
          {
            Stage stage = new Stage();
            PageWeb pw = new PageWeb("http://mesprojets.dchtrnd.be/stratego.html#Simple");
            pw.start(stage);
          });
          quit.setOnAction(e ->
          {
            primaryStage.close();
          });
          rgl.setOnAction(e ->
          {
            Stage stage = new Stage();
            PageWeb pw = new PageWeb("http://mesprojets.dchtrnd.be/stratego.html#Jouer");
            pw.start(stage);
          });

          root2.setPadding(new Insets(300,150,0,0));
          root.setRight(root2);
          root.setTop(menuprincipal);

          primaryStage.setTitle("Menu Stratego");
          primaryStage.setFullScreen(true);
          primaryStage.setScene(scene);
          primaryStage.show();
          this.primaryStage = primaryStage;
    }
    catch(Exception e)
    {
      Summary.ERROR(e,"MainGui");
      primaryStage.show();
      primaryStage.close();
    }
  }

/**
*Cette méthode permet de demander les noms des joeurs ainsi que la couleur voulu.
*Et elle initialise le nom de la partie qui est unique.

@param nombre qui est le nombre de joueur humain.
*/
  public void yourChoices(int nombre)
  {
    root.setStyle("-fx-background-color: #F1F8E0;");
    root2.setVisible(false);
    DateFormat format1 = new SimpleDateFormat("dd_MM_yy_HH_mm");
    Date date = new Date();
    this.nomPartie = "partie-J"+nombre+"-Du"+ format1.format(date) + "-stratego";

    BorderPane bpn = new BorderPane();
    bpn.setLeft(vb);
    bpn.setPadding(new Insets(20,20,20,20));
    root.setCenter(bpn);
    for(int nb = 0 ; nb < nombre ; nb++)
    {
      liste[nb].setVisible(true);
      liste2[nb].setVisible(true);
    }
    joueur = nombre;
  }

/**
*Cette méthode permet d'ouvir la fenetre pour placer les pièces et initialiser les
*joueur et les pièces

@see MethodsGui pour initialiser.
*/
  public void goToPlace()
  {
    Summary.OPEN(nomPartie); //<= ici ligne 236
    String[] nom = new String[joueur];
    Summary.WRITE("MainGui : choix de partie à " + joueur);
    Couleur clr = (Couleur)liste2[0].getValue();

    Board.createTray(10,10);
    Couleur[] listeClr = {new Couleur(156,25,10,"xx"),new Couleur(156,25,10,"xx"),new Couleur(156,25,10,"xx"),new Couleur(156,25,10,"xx")};
    PlacePawnGui pg = new PlacePawnGui(MethodsGui.initialisation(joueur,namePlayer(),colorPlayer(),2),joueur,paysage,nomPartie);
    primaryStage.close();
    pg.start(primaryStage);
  }

/**
*Cette méthode permet de récupérer les noms des joeurs et si besoin les raccoucir pour
*éviter tout problème d'affichage.

@return liste de nom de joueurs(String)
*/
  public String[] namePlayer()
  {
    String[] listeNom = new String[4];
    for(int i = 0 ; i < joueur ; i++)
    {
      if(liste[i].equals("Ordinateur"))
      {
        listeNom[i] = "joueur"+i;
      }
      if(liste[i].getText().length() < 15)
      {
        listeNom[i] = liste[i].getText();
      }
      else
      {
        listeNom[i] = liste[i].getText().substring(0, 15);
      }
    }
    return listeNom;
  }


/**
*Cette méthode permet de voir les couleurs choisies par les joueurs et au besoin initialiser une
*couleur par défaut.

@return liste de couleur (Couleur).
*/
  public Couleur[] colorPlayer()
  {
    Couleur[] listeCouleur = {new Couleur(247,35,12,"rouge"),new Couleur(253,241,184,"orange"),new Couleur(224,205,169,"gris"),new Couleur(254,191,210,"rose")};
    for(int i = 0 ; i < liste2.length ; i++)
    {
      if(liste2[i].getValue() != null)
      {
        listeCouleur[i] = (Couleur)liste2[i].getValue();
      }
    }
    return listeCouleur;
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}
