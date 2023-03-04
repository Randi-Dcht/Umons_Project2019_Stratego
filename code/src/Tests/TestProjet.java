import org.junit.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestProjet
{
  private Random rd = new Random();
  private Piece[] listeA = null;
  private Piece[] listeB = null;
  private Player A = null;
  private Player B = null;

  public void creedTest()
  {
    System.out.println("ok");
    Summary.OPEN("Test.test");
    Board.createTray(10,10);
    int[] lA  = {0,9,0,3};
    int[] lB = {0,9,6,9};
    A = new HumanPlayer("A",new Couleur(0,0,0,"noir"),lA);
    B = new HumanPlayer("B",new Couleur(0,0,0,"noir"),lB);
    listeA = listPawn(A);
    listeB = listPawn(B);
  }

  public Piece[] listPawn(Player joueur)
  {
    System.out.println("avant test ");
    Piece[] liste = new Piece[10];
    /*pièce qui ne peuvent pas se déplacer*/
    liste[0]  =  new Bomb(joueur);
    liste[1]  =  new Flag(joueur);
    /*pièce qui peuvent se déplacent d'une seule case à la fois*/
    liste[2]  = new Captain(joueur);
    liste[3]  = new Colonel(joueur);
    liste[4]  = new Colonel(joueur);
    liste[5]  = new Marshal(joueur);
    liste[6]  = new Minesweeper(joueur);
    liste[7]  = new Scout(joueur);
    liste[8]  = new Sergeant(joueur);
    /*pièce qui peut se déplacer de plusieurs cases à la fois*/
    liste[9]  = new Spy(joueur);
    return liste;
  }

  @Test
  public void placePawn()
  {
    creedTest();
      assertTrue(listeA[0].placeMe(Board.plateau[0][0]));
      assertTrue(listeA[1].placeMe(Board.plateau[0][2]));
      assertTrue(listeA[2].placeMe(Board.plateau[2][0]));
      assertTrue(listeA[3].placeMe(Board.plateau[4][0]));
      assertTrue(listeA[4].placeMe(Board.plateau[8][0]));
      assertTrue(listeA[5].placeMe(Board.plateau[3][0]));
      assertTrue(listeA[6].placeMe(Board.plateau[3][2]));
      assertTrue(listeA[7].placeMe(Board.plateau[5][2]));
      assertTrue(listeA[8].placeMe(Board.plateau[1][1]));
      /*test les mauvaises positions : */
      assertFalse(listeA[9].placeMe(Board.plateau[5][2]));
      assertFalse(listeA[9].placeMe(Board.plateau[2][4]));
      listeA[9].placeMe(Board.plateau[2][2]);
  }

  @Before
  public void beforeTest()
  {
    Summary.OPEN("Test");
    Board.createTray(10,10);
    int[] lA  = {0,9,0,3};
    int[] lB = {0,9,6,9};
    A = new HumanPlayer("A",new Couleur(0,0,0,"noir"),lA);
    B = new HumanPlayer("B",new Couleur(0,0,0,"noir"),lB);
    Spy espionA = new Spy(A);
    Spy espionB = new Spy(B);
    espionA.placeMe(Board.plateau[2][0]);
    espionB.placeMe(Board.plateau[2][8]);
    Bomb bombeA = new Bomb(A);
    Bomb bombeB = new Bomb(B);
    bombeA.placeMe(Board.plateau[1][0]);
    bombeB.placeMe(Board.plateau[1][8]);
    Flag drapA = new Flag(A);
    Flag drapB = new Flag(B);
    drapA.placeMe(Board.plateau[0][0]);
    drapB.placeMe(Board.plateau[0][8]);
    Scout eclA = new Scout(A);
    Scout eclB = new Scout(B);
    eclA.placeMe(Board.plateau[3][0]);
    eclB.placeMe(Board.plateau[3][8]);
    Marshal marA = new Marshal(A);
    Marshal marB = new Marshal(B);
    marA.placeMe(Board.plateau[4][0]);
    marB.placeMe(Board.plateau[4][8]);
    Piece[] lsA = {drapA,bombeA,espionA,eclA,marA};
    Piece[] lsB = {drapB,bombeB,espionB,eclB,marB};
    listeA = lsA;
    listeB = lsB;
  }

  @Test
  public void displacementPawnDiago()
  {
    System.out.println(listeA.length);
    for(int i = 0 ; i < listeA.length ; i++ )
    {
      int x = listeA[i].position.x + 1;
      int y = listeA[i].position.y + 1;
      assertFalse(listeA[i].displacement(Board.plateau[x][y]));
    }
  }


  @Test
  public void attackPawnDead1()
  {
    listeA[3].attack(listeB[3]);
    assertFalse("vie", listeA[3].vivant);
    assertFalse("vie", listeB[3].vivant);
    listeA[2].attack(listeB[4]);
    /*!!!!*/
    assertFalse("vie",listeA[2].vivant);
    assertTrue("vie",listeB[4].vivant);
  }

  @Test
  public void attackPawnDead2()
  {
    listeA[4].attack(listeB[1]);
    assertFalse(listeA[4].vivant);
    assertTrue(listeB[1].vivant);
  }

  @Test
  public void endGameFlag()
  {}

  /*@Test
  public void endGamePawn()
  {}
*/
}
