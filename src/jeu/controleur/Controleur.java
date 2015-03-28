package jeu.controleur;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import jeu.modele.*;
import jeu.vue.*;

/**
 * La classe Controleur correspond au contr�leur principal du logiciel de 1000 bornes.
 * 
 * @author Florent LUCET et Julie ROMERO
 * @version 1.0
 */
public class Controleur implements Observer {
	
	private Partie partie;
	private FenetreJeu jeu;
	
	
	
	// ********************
	// *** CONSTRUCTEUR ***
	// ********************
	
	/**
	 * Constructeur avec attributs largeur et hauteur de la classe Controleur.
	 * 
	 * @param largeur Largeur de la fen�tre de jeu.
	 * @param hauteur Hauteur de la fen�tre de jeu.
	 */
	public Controleur(int largeur, int hauteur) {
		// Cr�ation de la fen�tre de jeu.
		this.jeu = new FenetreJeu("Le Mille Bornes express.", largeur, hauteur);
		this.jeu.addObserver(this);
		
		partie = null;
	}
	
	
	
	// ****************************
	// *** M�THODE DE LANCEMENT ***
	// ****************************
	
	/**
	 * M�thode permettant de lancer la fen�tre de la partie de jeu.
	 */
	public void lancer() {
		this.jeu.lancer();
	}
	
	
	// ******************************
	// *** M�THODE DE MISE � JOUR ***
	// ******************************
	
	/**
	 * M�thode permettant de mettre � jour l'affichage en fonction des donn�es
	 * du mod�le.
	 * M�thode du pattern Observateur.
	 */
	@SuppressWarnings("unchecked")
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof FenetreJeu) {
			if(arg1 instanceof String) {
				if(arg1.equals("annuler")) {
					this.jeu.lancerMenu();
				}
			}
			
			if(arg1 instanceof ArrayList) {
				this.partie = new Partie((ArrayList<Joueur>) arg1);
				System.out.println(this.partie.getJoueurs());
				this.partie.initialiser();
				this.partie.lancer();
				//this.jeu.lancerJeu(this.partie);
				
			}
		}
		
		if(arg0 instanceof Partie) {
			if(arg1 instanceof Partie)
				this.jeu.lancerJeu((Partie)arg1);
			if(arg1 instanceof Joueur)
				this.jeu.terminerPartie((Joueur)arg1);
		}
	}
	
	
	
	// *****************
	// *** ACCESSEUR ***
	// *****************
	
	/**
	 * M�thode permettant de r�cup�rer la partie de jeu du contr�leur.
	 * @return Partie de jeu du contr�leur.
	 */
	public Partie getPartie() {
		return partie;
	}
	
	
	// ****************
	// *** MUTATEUR ***
	// ****************
	
	/**
	 * M�thode permettant de changer la partie de jeu du contr�leur.
	 * @param partie Nouvelle partie de jeu du contr�leur.
	 */
	public void setPartie(Partie partie) {
		this.partie = partie;
	}
	
}