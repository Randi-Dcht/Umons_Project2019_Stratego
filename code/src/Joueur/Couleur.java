//package be.dchtrnd.mesprojets.stratego;

import java.io.Serializable;

/**
*Cette classe permet de créer des couleurs pour les joueur.
@param rouge qui est la quantité de rouge (int).
@param vert qui est la quantité de vert (int).
@param bleu qui est la quantité de bleu (int).
@param nom qui est le nom de la couleur.

@author Dochot.be(étudiant Umons Informatique)
@version 3.5 (version GUI+)
*/
public class Couleur implements Serializable
{
	final int rouge;
	final int vert;
	final int bleu;
	final String nom;

	public Couleur(int rg , int vt , int bl , String nom_)
	{
		rouge =  rg;
		vert  =  vt;
		bleu  =  bl;
		nom   =  nom_;
	}

	public String toString()
	{
		return nom;
	}
}
