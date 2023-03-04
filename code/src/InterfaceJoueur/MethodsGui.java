//package be.dchtrnd.mesprojets.stratego;

/**
*Cette classe permet d'initialiser les joeurs (Humain ou Ordinateur) avec leur nom,Couleur,position ainsi que
*les pièces des joeurs au nombre voulu.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class MethodsGui
{
  public static Player[] initialisation(int nombre,String[] nom, Couleur[] couleur, int typeIa)
  {
    StarPiece sp;
    Player[] liste;
    if(nombre == 2)
    {
      liste = new Player[2];
      int[][] tabl = {{2,4},{3,4},{2,5},{3,5},{6,4},{7,4},{6,5},{7,5}};
      Board.createBarriers(tabl);
      sp = new StarPiece(1,0,0,0,0,3,0,2,0,2,0);
      //sp = new StarPiece(6,1,1,2,3,4,4,4,5,8,1);
      int[] myliste1  = {0,9,0,3};
      int[] myliste2 = {0,9,6,9};
      HumanPlayer joueur1 = new HumanPlayer(nom[0],couleur[0],myliste1);
      HumanPlayer joueur2 = new HumanPlayer(nom[1],couleur[1],myliste2);
      joueur1.assignment(joueur2,sp.createPiece(joueur1),sp.howDisplacement());
      joueur2.assignment(joueur1,sp.createPiece(joueur2),sp.howDisplacement());
      liste[0] = joueur1;
      liste[1] = joueur2;
      return liste;
    }
    if(nombre == 4)
    {
      liste = new Player[4];
      int[][] tabl ={{0,4},{0,5},{4,5},{4,4},{5,4},{5,5},{4,9},{5,9},{9,5},{9,4},{4,0},{5,0}};
      Board.createBarriers(tabl);
      sp = new StarPiece(0,0,0,0,3,0,0,0,0,0,0);
      int[] myliste1 = {0,3,0,3};
      int[] myliste2 = {6,9,0,3};
      int[] myliste3 = {0,3,6,9};
      int[] myliste4 = {6,9,6,9};
      HumanPlayer joueur1 = new HumanPlayer(nom[0],couleur[0],myliste1);
      HumanPlayer joueur2 = new HumanPlayer(nom[1],couleur[1],myliste2);
      HumanPlayer joueur3 = new HumanPlayer(nom[2],couleur[2],myliste3);
      HumanPlayer joueur4 = new HumanPlayer(nom[3],couleur[3],myliste4);
      joueur1.assignment(joueur2,sp.createPiece(joueur1),sp.howDisplacement());
      joueur2.assignment(joueur3,sp.createPiece(joueur2),sp.howDisplacement());
      joueur3.assignment(joueur4,sp.createPiece(joueur3),sp.howDisplacement());
      joueur4.assignment(joueur1,sp.createPiece(joueur4),sp.howDisplacement());
      liste[0] = joueur1;
      liste[1] = joueur2;
      liste[2] = joueur3;
      liste[3] = joueur4;
      return liste;
    }
    else
    {
      liste = new Player[2];
      int[][] tabl = {{2,4},{3,4},{2,5},{3,5},{6,4},{7,4},{6,5},{7,5}};
      Board.createBarriers(tabl);
      sp = new StarPiece(6,1,1,2,3,4,4,4,5,8,1);
      //sp = new StarPiece(1,0,0,0,1,0,0,0,0,2,0);
      int[] myliste1  = {0,9,6,9};
      HumanPlayer joueur1 = new HumanPlayer(nom[0],couleur[0],myliste1);
      ComputerPlayer ia;
      if(typeIa == 1)
      {
        ia = new IA0(new Couleur(150,140,0,"brunClaire"));
      }
      else
      {
        ia = new IA1(new Couleur(150,140,0,"brunClaire"));
      }
      joueur1.assignment(ia,sp.createPiece(joueur1),sp.howDisplacement());
      ia.assignment(joueur1,sp.createPiece(ia),sp.howDisplacement());
      ia.placePawn();
      ia.createListe();
      liste[0] = joueur1;
      liste[1] = ia;
      return liste;
    }
  }


}
