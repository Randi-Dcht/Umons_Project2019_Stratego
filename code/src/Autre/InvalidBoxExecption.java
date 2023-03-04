//package be.dchtrnd.mesprojets.stratego;

/**
*Cette Exception est générée lorqu'une pièce est mal placée.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/

public class InvalidBoxExecption extends Exception
{
  public InvalidBoxExecption()
  {
    System.out.println("la pièce ne peut pas aller sur cette case ");
  }
}
