//package be.dchtrnd.mesprojets.stratego;

/**
*Cette interfaces permet de définir les méthodes possibles pour un joueur Humain ou ordinateur
@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public interface Player
{
  void assignment(Player joueur,Piece[] liste, int nombrePiece);
  Player next();
  void dead();
  Couleur myColor();
  int howMany();
  Piece[] myList();
  int[] wherePlace();
  String myName();
  void catchOther(Piece piece);
  Piece[] captureList();
}
