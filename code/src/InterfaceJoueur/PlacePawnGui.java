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

public class PlacePawnGui extends Application
{
  final String cheminAcces = "../code/Illustration/";

  private Player[] listeJoueur;
  private int nombreJoueur;
  private String[] imagefond;
  private String nomPartie;

  private Player actuelle;                              //joueur qui joue maintenant
  private ImageCouple[][] image;                        //plateau de jeu avec image
  private ImageCouple[] choisir;                        //image que possède le joueur
  private int quiTours = 1;                             //quantième joueur qui joue
  private Rectangle[][] cases = new Rectangle[10][10];  //case du joueur
  private ImageCouple imgActu;
  private int cb;

  private GridPane joueurPiece;
  private Button seduc;
  private Button suivant;
  private BorderPane fond;
  private VBox dessous;
  private Stage stages;
  private GridPane grilleMilieu;
  private BorderPane information;

  public PlacePawnGui(Player[] liste, int cb,String[] image,String nom)
  {
    listeJoueur  = liste;
    nombreJoueur = cb;
    imagefond    = image;
    nomPartie    = nom;
    actuelle     = liste[0];
  }

  public void start(Stage stage)
  {
    fond        = new BorderPane();
    fond.setStyle("-fx-background-image: url('../code/Illustration/imagefond2.JPG');");
    Scene scene = new Scene(fond,1200,1200);

    Button aide    = new Button("je vous aide");
    dessous        = new VBox();
    seduc          = new Button("automatique");
    suivant        = new Button("Ok,satisfait");
    dessous.setSpacing(5);
    aide.setStyle("-fx-text-fill: #FF4000; -fx-border-color: red; -fx-background-color: #F5D0A9; -fx-font-size:2em;");
    seduc.setStyle("-fx-text-fill: #FF4000; -fx-border-color: red; -fx-background-color: #F5D0A9; -fx-font-size:2em;");
    suivant.setStyle("-fx-text-fill: #FF4000; -fx-border-color: red; -fx-background-color: #F5D0A9; -fx-font-size:2em;");
    dessous.getChildren().addAll(seduc,aide,suivant);

    aide.setOnAction(e ->
    {
      Stage stg = new Stage();
      PageWeb pw = new PageWeb("http://mesprojets.dchtrnd.be/stratego.html#Jouer");
      pw.start(stg);
    } );
    seduc.setOnAction(e ->
    {
      automatic();
    });

    image = new ImageCouple[10][10];
    suivant.setVisible(false);
    createGrille();
    createPlayer();

    fond.setCenter(grilleMilieu);

    stage.setTitle("Placer vos pionts sur les cases vertes ");
    stage.setScene(scene);
    stage.setOnCloseRequest(fermer -> fermer.consume());
    stage.show();
    stages = stage;

    grilleMilieu.setOnMouseClicked(e -> wherePiece(e.getX(),e.getY()));
  }

  public void endPlace()
  {
    Stage stg = new Stage();
    BoardGui bg = new BoardGui(listeJoueur,Board.plateau,imagefond,nomPartie);
    bg.start(stg);
    stages.close();
  }

  public void createPlayer()
  {
    choisir = new ImageCouple[actuelle.myList().length];
    joueurPiece = new GridPane();
    information = new BorderPane();
    VBox v    = new VBox();
    Text nom0 = new Text("ton nom est");
    Text nom1 = new Text(actuelle.myName());
    nom0.setFont(Font.font("Chilanka",FontWeight.BOLD,25));
    nom1.setFont(Font.font("Chilanka",FontWeight.BOLD,25));
    v.getChildren().addAll(nom0,nom1);
    information.setTop(v);
    information.setBottom(dessous);
    piecePlayer();
    cb = 0;
    for(int i = 0; i < 10 ; i++)
    {
      for(int j = 0; j < 10 ; j++)
      {
        if(i <= actuelle.wherePlace()[3]  && i >= actuelle.wherePlace()[2] && j <= actuelle.wherePlace()[1] && j>= actuelle.wherePlace()[0])
        {
          cases[j][i].setFill(Color.GREEN);
        }
        else
        {
          cases[j][i].setFill(Color.TRANSPARENT);
          if(image[j][i] != null)
          {
            image[j][i].image.setVisible(false);
          }
        }
      }
    }
    fond.setRight(joueurPiece);
    fond.setLeft(information);
    joueurPiece.setOnMouseClicked(e -> whatPiece(e.getX(),e.getY()));
  }

  public void getInfo()
  {
    if(imgActu != null)
    {
      ImageView img = imgActu.info;
      img.setFitWidth(150);
      img.setFitHeight(150);
      information.setCenter(img);
    }
  }

  public void removeInfo()
  {
    if(imgActu != null)
    {
      information.getChildren().remove(imgActu.info);
    }
  }

  public void piecePlayer()
  {
    int a = 0;
    int b = 0;
    for(int i = 0 ; i < actuelle.myList().length ; i++)
    {
      if(a == 10)
      {
        a = 0;
        b++;
      }
      Image ig = new Image(cheminAcces + actuelle.myList()[i].id+".png");
      ImageView img= new ImageView();
      img.setFitWidth(98);
      img.setFitHeight(98);
      img.setImage(ig);
      choisir[i] = new ImageCouple(actuelle.myList()[i],img);
      joueurPiece.add(img,b,a);
      a++;
    }
  }

  public void changedPlayer()
  {
    if(nombreJoueur == quiTours)
    {
      endPlace();
    }
    else
    {
      choisir = null;
      cb = 0;
      actuelle = actuelle.next();
      fond.getChildren().remove(joueurPiece);
      fond.getChildren().remove(information);
      createPlayer();
      suivant.setVisible(false);
      seduc.setVisible(true);
      quiTours++;
    }
  }

  public void createGrille()
  {
    grilleMilieu = new GridPane();
    grilleMilieu.setHgap(3);
    grilleMilieu.setVgap(3);
    for(int i = 0 ; i < 10 ; i++)
    {
      for(int j = 0 ; j < 10 ; j++)
      {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(97);
        rectangle.setWidth(97);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        cases[j][i] = rectangle;
        grilleMilieu.add(rectangle,j,i);
      }
    }
  }

  public void whatPiece(double a, double b)
  {
    int[] couple = transformed(a,b);
    int ou = (couple[0]*10)+couple[1];
    if(!choisir[ou].placer)
    {
      imgActu = choisir[ou];
      getInfo();
    }
  }

  public void wherePiece(double a, double b)
  {
    int[] couple = transformed(a,b);
    if(imgActu != null && borne(couple))
    {
      if(image[couple[0]][couple[1]] != null) //s'il y a déjà quelqu'un !
      {
        grilleMilieu.getChildren().remove(image[couple[0]][couple[1]].image);
        image[couple[0]][couple[1]].piece.removeMe();
        image[couple[0]][couple[1]].ancien.setVisible(true);
        image[couple[0]][couple[1]].placer = false;
        cb--;
      }
      if(imgActu.piece.placeMe(Board.plateau[couple[0]][couple[1]]))
      {
        cb++;
        image[couple[0]][couple[1]]= imgActu;
        grilleMilieu.add(imgActu.image,couple[0],couple[1]);
        imgActu.ancien.setVisible(false);
        imgActu.placer = true;
        removeInfo();
        imgActu = null;
      }
    }
    else if(imgActu == null && image[couple[0]][couple[1]] != null)
    {
      grilleMilieu.getChildren().remove(image[couple[0]][couple[1]].image);
      image[couple[0]][couple[1]].piece.removeMe();
      image[couple[0]][couple[1]].ancien.setVisible(true);
      image[couple[0]][couple[1]].placer = false;
      cb--;
    }
    check();
  }

  public boolean borne(int[] couple)
  {
    if(couple[0] <= 9 && couple[1] >= 0 && couple[1] <= 9 && couple[1] >= 0)
    {
      return true;
    }
    return false;
  }

  public int[] transformed(double a, double b)
  {
    int x = (int)(a/100);
    int y = (int)(b/100);
    int[] tab = {x,y};
    return tab;
  }

  public void check()
  {
    if(cb == actuelle.myList().length)
    {
      suivant.setVisible(true);
      suivant.setOnAction(e -> changedPlayer());
    }
    else
    {
      suivant.setVisible(false);
    }
  }

  public void automatic()
  {
    ArrayList<Box> positions = Automatic.creedPlace(actuelle);
    imgActu = null;
    for(int i = 0; i < choisir.length ; i++)
    {
      if(choisir[i].piece.position == null)
      {
        Automatic.place(choisir[i].piece,positions);
        choisir[i].ancien.setVisible(false);
        if(choisir[i].piece.position != null)
        {
          cb++;
          image[choisir[i].piece.position.x][choisir[i].piece.position.y] = choisir[i];
          grilleMilieu.add(choisir[i].image,choisir[i].piece.position.x,choisir[i].piece.position.y);
        }
      }
    }
    check();
    seduc.setVisible(false);
  }
}
