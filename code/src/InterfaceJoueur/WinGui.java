//package be.dchtrnd.mesprojets.stratego;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import java.io.File;
import javafx.animation.*;
import javafx.util.*;
/**
*Cette classe permet d'afficher le gagant de la partie durant 7 secondes.

@param joueur qui est le joueur qui a gagner la partie.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class WinGui extends Application
{
  private Player gagant = null;

  public WinGui(Player joueur)
  {
    gagant = joueur;
  }

  public void start(Stage primaryStage)
  {
    BorderPane root = new BorderPane();
    VBox vb = new VBox();
    Scene scene = new Scene(root,930,650);

    /*Media video = new Media(new File("../code/Illustration/gagne.mp4").toURI().toString());
    MediaPlayer lire = new MediaPlayer(video);
    lire.setAutoPlay(true);
    MediaView voirvoideo = new MediaView(lire);*/

    Text txt = new Text("Tu as gagné " + gagant.myName());
    txt.setFont(Font.font("Chilanka",FontWeight.BOLD,70));

    //vb.getChildren().add(voirvoideo);
    vb.getChildren().add(txt);

    root.setCenter(vb);

    primaryStage.setScene(scene);
    primaryStage.show();

    Timeline terminer = new Timeline(new KeyFrame(Duration.seconds(7),
    action ->
    {
      primaryStage.close();
      MainGUI gui = new MainGUI();
      gui.start(primaryStage);
      Summary.CLOSE();
    }));
    terminer.play();

  }
}
