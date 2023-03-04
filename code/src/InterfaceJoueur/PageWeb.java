//package be.dchtrnd.mesprojets.stratego;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.*;

/**
*Cette classe permet d'afficher une fenetre contenant la page web du jeu avec toutes
*les informations importantes.

@param lien qui est le lien du site web que l'on veut afficher.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class PageWeb extends Application
{
  private String lien;

  public PageWeb(String lien_)
  {
    lien = lien_;
  }

  public void start(Stage primaryStage)
  {
    Group root = new Group();
    Scene scene = new Scene(root,1000,1000);
    WebView web = new WebView();
    WebEngine we = web.getEngine();
    web.setPrefSize(1000, 1000);
    we.load(lien);

    root.getChildren().addAll(web);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Page web Stratego 2019 :");
    primaryStage.show();
  }
  public static void main(String[] args) {
    launch(args);
  }
}
