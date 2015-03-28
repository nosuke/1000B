package jeu.modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;


/**
 * La classe Partie correspond a une partie de jeu comportant une liste de
 * joueurs, une pioche, une défausse et une valeur indiquant si celle-ci est
 * terminée.
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
	// *** MÉ‰THODE D'INITIALISATION ***
	// ********************************

	/**
	 * Méthode permettant de créer l'ensemble des cartes du jeu dans la pioche,
	 * de mélanger la pioche et de distribuer 4 cartes à tous les joueurs.
	 */
	public void initialiser() {
		int nb = 0;
		int effet = 0;
		int type = 0;

		// Création des cartes attaques.
		for (nb = 0; nb < 2; nb++) {
			for (effet = 1; effet < Carte.getEffets()[type].length; effet++) {
				this.pioche.add(new Carte(type, effet));
			}
		}

		// Changement de type : cartes parades.
		type++;
		// Création des 5 cartes "feu vert".
		for (nb = 0; nb < 5; nb++) {
			this.pioche.add(new Carte(type, 1));
		}
		// Création des autres cartes parades.
		for (nb = 0; nb < 4; nb++) {
			for (effet = 2; effet < Carte.getEffets()[type].length; effet++) {
				this.pioche.add(new Carte(type, effet));
			}
		}

		// Changement de type : cartes bottes.
		type++;
		// Création des cartes bottes.
		for (effet = 2; effet < Carte.getEffets()[type].length; effet++) {
			this.pioche.add(new Carte(2, effet));
		}

		// Changement de type : cartes etapes.
		type++;
		// Création des cartes etapes 25, 50 et 75.
		for (nb = 0; nb < 6; nb++) {
			for (effet = 1; effet < 4; effet++) {
				this.pioche.add(new Carte(type, effet));
			}
		}
		// Création des cartes etapes 100.
		for (nb = 0; nb < 9; nb++) {
			this.pioche.add(new Carte(type, 4));
		}
		// Création des cartes etapes 200.
		for (nb = 0; nb < 3; nb++) {
			this.pioche.add(new Carte(type, 5));
		}

		// Mélange de la pioche.
		Collections.shuffle(this.pioche);

		// Distribution des cartes aux joueurs.
		for (nb = 0; nb < 4; nb++) {
			for (int j = 0; j < this.joueurs.size(); j++) {
				this.joueurs.get(j).piocherCarte(this.pioche);
			}
		}
	}
	
	
	
	// ******************************************
	// *** MÉTHODES DE LANCEMENT DE LA PARTIE ***
	// ******************************************
	
	/**
	 * Méthode permettant de lancer le jeu et de gérer le déroulement de chaque
	 * tour. Quand le jeu est terminé, elle renvoie le joueur gagnant.
	 * 
	 * @return Gagnant de la partie.
	 */
	public Joueur lancer() {

		while (!terminee) {
			for (int jCourant = 0; jCourant < this.joueurs.size(); jCourant++) {
				this.setChanged();
				this.notifyObservers(this);
				// Initialisation nécessaire pour le tour de chaque joueur.
				joueurCourant = this.joueurs.get(jCourant);
				System.out.println("C'est au tour de " + joueurCourant.getNom()
						+ " !!");
				this.joueurCourant.deroulementTour(this.joueurs, this.defausse, this.pioche);
				
				effacerEcranConsole();
				
				// Si le joueur a atteint mille bornes, la partie est terminée.
				if(this.joueurCourant.getScore() == 1000)
					terminee = true;

			}
		}

		return joueurCourant;
	}
	
	/**
	 * Méthode permettant de balayer l'écran de la console.
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