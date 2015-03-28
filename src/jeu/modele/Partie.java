package jeu.modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;


/**
 * La classe Partie correspond a une partie de jeu comportant une liste de
 * joueurs, une pioche, une d�fausse et une valeur indiquant si celle-ci est
 * termin�e.
 * 
 * @author Florent LUCET et Julie ROMERO
 * @version 1.0
 */
public class Partie extends Observable{

	private ArrayList<Joueur> joueurs;
	private TasDeCartes pioche;
	private TasDeCartes defausse;
	private boolean terminee;
	private Joueur joueurCourant;
	
	
	
	// ********************
	// *** CONSTRUCTEUR ***
	// ********************

	/**
	 * Constructeur avec l'attribut "joueurs" de la classe Partie.
	 * 
	 * @param joueurs
	 *            Joueurs de la partie.
	 */
	public Partie(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
		this.pioche = new TasDeCartes();
		this.defausse = new TasDeCartes();
		this.terminee = false;

		this.joueurCourant = joueurs.get(0);
	}
	
	
	
	// ********************************
	// *** MɉTHODE D'INITIALISATION ***
	// ********************************

	/**
	 * M�thode permettant de cr�er l'ensemble des cartes du jeu dans la pioche,
	 * de m�langer la pioche et de distribuer 4 cartes � tous les joueurs.
	 */
	public void initialiser() {
		int nb = 0;
		int effet = 0;
		int type = 0;

		// Cr�ation des cartes attaques.
		for (nb = 0; nb < 2; nb++) {
			for (effet = 1; effet < Carte.getEffets()[type].length; effet++) {
				this.pioche.add(new Carte(type, effet));
			}
		}

		// Changement de type : cartes parades.
		type++;
		// Cr�ation des 5 cartes "feu vert".
		for (nb = 0; nb < 5; nb++) {
			this.pioche.add(new Carte(type, 1));
		}
		// Cr�ation des autres cartes parades.
		for (nb = 0; nb < 4; nb++) {
			for (effet = 2; effet < Carte.getEffets()[type].length; effet++) {
				this.pioche.add(new Carte(type, effet));
			}
		}

		// Changement de type : cartes bottes.
		type++;
		// Cr�ation des cartes bottes.
		for (effet = 2; effet < Carte.getEffets()[type].length; effet++) {
			this.pioche.add(new Carte(2, effet));
		}

		// Changement de type : cartes etapes.
		type++;
		// Cr�ation des cartes etapes 25, 50 et 75.
		for (nb = 0; nb < 6; nb++) {
			for (effet = 1; effet < 4; effet++) {
				this.pioche.add(new Carte(type, effet));
			}
		}
		// Cr�ation des cartes etapes 100.
		for (nb = 0; nb < 9; nb++) {
			this.pioche.add(new Carte(type, 4));
		}
		// Cr�ation des cartes etapes 200.
		for (nb = 0; nb < 3; nb++) {
			this.pioche.add(new Carte(type, 5));
		}

		// M�lange de la pioche.
		Collections.shuffle(this.pioche);

		// Distribution des cartes aux joueurs.
		for (nb = 0; nb < 4; nb++) {
			for (int j = 0; j < this.joueurs.size(); j++) {
				this.joueurs.get(j).piocherCarte(this.pioche);
			}
		}
	}
	
	
	
	// ******************************************
	// *** M�THODES DE LANCEMENT DE LA PARTIE ***
	// ******************************************
	
	/**
	 * M�thode permettant de lancer le jeu et de g�rer le d�roulement de chaque
	 * tour. Quand le jeu est termin�, elle renvoie le joueur gagnant.
	 * 
	 * @return Gagnant de la partie.
	 */
	public Joueur lancer() {

		while (!terminee) {
			for (int jCourant = 0; jCourant < this.joueurs.size(); jCourant++) {
				this.setChanged();
				this.notifyObservers(this);
				// Initialisation n�cessaire pour le tour de chaque joueur.
				joueurCourant = this.joueurs.get(jCourant);
				System.out.println("C'est au tour de " + joueurCourant.getNom()
						+ " !!");
				this.joueurCourant.deroulementTour(this.joueurs, this.defausse, this.pioche);
				
				effacerEcranConsole();
				
				// Si le joueur a atteint mille bornes, la partie est termin�e.
				if(this.joueurCourant.getScore() == 1000)
					terminee = true;

			}
		}

		return joueurCourant;
	}
	
	/**
	 * M�thode permettant de balayer l'�cran de la console.
	 */
	public void effacerEcranConsole() {
		for (int i=0; i<20; i++)
			System.out.println();
	}
	
	
	
	// ******************
	// *** ACCESSEURS ***
	// ******************

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public TasDeCartes getPioche() {
		return pioche;
	}

	public TasDeCartes getDefausse() {
		return defausse;
	}

	public boolean getTerminee() {
		return terminee;
	}
	
	
	// *****************
	// *** MUTATEURS ***
	// *****************

	public void setJoueurs(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public void setPioche(TasDeCartes pioche) {
		this.pioche = pioche;
	}

	public void setDefausse(TasDeCartes defausse) {
		this.defausse = defausse;
	}

	public void setTerminee(boolean terminee) {
		this.terminee = terminee;
	}

	public Joueur getJoueurCourant() {
		return joueurCourant;
	}

	public void setJoueurCourant(Joueur joueurCourant) {
		this.joueurCourant = joueurCourant;
	}
	
}