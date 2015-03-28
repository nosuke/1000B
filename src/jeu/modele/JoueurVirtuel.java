package jeu.modele;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;

/**
 * La classe JoueurVirtuel correspond � un joueur virtuel du jeu comportant un nom, un
 * score, une main de cartes, une possibilite de d�marrer, la carte attaque active
 * sur lui (s'il y en a une), la carte vitesse active sur lui (s'il y en a une) et ses
 * immunit�s.
 * 
 * @author Florent LUCET et Julie ROMERO
 * @version 1.0
 */
public class JoueurVirtuel extends Joueur {
	
	// ********************
	// *** CONSTRUCTEUR ***
	// ********************
	
	/**
	 * Constructeur avec attribut nom de la classe JoueurVirtuel.
	 * 
	 * @param nom Nom du joueur virtuel;
	 */
	public JoueurVirtuel(String nom) {
		super(nom);
	}
	
	
	
	// ********************************************
	// *** M�THODES DE D�ROULEMENT DE LA PARTIE ***
	// ********************************************
	
	/**
	 * M�thode permettant de d�finir le d�roulement du tour d'un joueur.
	 * 
	 * @param joueurs Liste des joueurs de la partie.
	 * @param defausse D�fausse de la partie.
	 * @param pioche Pioche de la partie.
	 */
	public void deroulementTour(ArrayList<Joueur> joueurs, TasDeCartes defausse, TasDeCartes pioche) {

		// Fait le choix du tas o� le joueur veut piocher.
		this.choixPioche(defausse, pioche);
		int numSaisie = -1;

		// Fait le choix de la carte que le joueur veut jouer.
		numSaisie = this.choixNumeroCarte();
		
		while (this.getMain().size() > 4) {
			numSaisie = this.choixCarteADefausser();
			this.defausserCarte(this.getMain().get(numSaisie), defausse);
		}
	}
	
	
	/**
	 * M�thode qui va faire le choix du tas o� le joueur veut piocher.
	 * 
	 * @param defausse D�fausse de la partie.
	 * @param pioche Pioche de la partie.
	 */
	private void choixPioche(TasDeCartes defausse, TasDeCartes pioche) {

		// Si la d�fausse est vide, une exception est lev�e sur le peek().
		try {

			// Si la d�fausse n'est pas vide, le choix de la pioche est laiss� au joueur.
			System.out.println("Derni�re carte de la d�fausse : "
					+ defausse.peek());
			
			if (!this.isDemarre() && defausse.peek().getType() == 1 && defausse.peek().getEffet() == 1) {
				this.piocherCarte(defausse);
				System.out.println("Vous avez pioch� dans la d�fausse.");
			} else if (defausse.peek().getType() == 3) {
				this.piocherCarte(defausse);
				System.out.println("Vous avez pioch� dans la d�fausse.");
			} else {
				Random rand = new Random();
				if (rand.nextInt(2) == 0) {
					this.piocherCarte(defausse);
					System.out.println("Vous avez pioch� dans la d�fausse.");
				} else {
					this.piocherCarte(pioche);
					System.out.println("Vous avez pioch� dans la pioche.");
				}
			}

		} catch (EmptyStackException e) {

			/* Si la d�fausse est vide, le joueur n'a pas le choix et il tire
			obligatoirement dans la pioche. */
			System.out.println("Derni�re carte de la d�fausse : rien.");
			this.piocherCarte(pioche);
			System.out.println("Vous avez pioch� dans la pioche.");
		}

	}

	/**
	 * M�thode permettant de r�cup�rer un num�ro de carte valide correspondant
	 * � la carte que souhaite jouer le joueur.
	 * 
	 * @return Num�ro de carte choisi.
	 */
	private int choixNumeroCarte() {
		
		// La main est affich�e pour que le joueur puisse voir les num�ros des cartes.
		this.ecrireMain();
		System.out.println("Votre score : " + this.getScore());
		System.out.println("Les attaques profer�es contre vous : " + this.getAttaque() + " , " + this.getVitesse());
		System.out.println("Vos immunit�s : " + this.getImmunites());
		
		if (this.getAttaque() != null || this.getVitesse() != null) {
			if (this.getAttaque() != null) {
				for (int i=0; i<this.getMain().size(); i++) {
					if ((this.getMain().get(i).getEffet() == this.getAttaque().getEffet()) && ((this.getMain().get(i).getType() == 1) || (this.getMain().get(i).getType() == 2)))
						return i;
				}
			} else {
				for (int i=0; i<this.getMain().size(); i++) {
					if ((this.getMain().get(i).getEffet() == 2) && ((this.getMain().get(i).getType() == 1) || (this.getMain().get(i).getType() == 2)))
						return i;
				}
			}
		}
		
		int etapeMax = 0;
		int indexEtapeMax = 0;
		
		for (int i=0; i<this.getMain().size(); i++) {
			if (this.getMain().get(i).getType() == 3)
				if (this.getMain().get(i).getEffet() > etapeMax) {
					etapeMax = this.getMain().get(i).getEffet();
					indexEtapeMax = i;
				}
		}
		
		if (etapeMax != 0)
			return indexEtapeMax;
		else
			return -2;
	}
	
	/**
	 * M�thode permettant de r�cup�rer un num�ro de carte valide correspondant
	 * � la carte que souhaite jeter � la d�fausse le joueur.
	 * 
	 * @return Num�ro de carte choisi.
	 */
	private int choixCarteADefausser() {
		
		// La main est affich�e pour que le joueur puisse voir les num�ros des cartes.
		this.ecrireMain();
		
		for (int i=0; i<this.getMain().size(); i++) {
			if (this.getMain().get(i).getType() == 0) {
				System.out.println("Vous avez jet� la carte" + this.getMain().get(i));
				return i;
			}
		}
		
		for (int i=0; i<this.getMain().size(); i++) {
			if (this.getMain().get(i).getType() == 1) {
				System.out.println("Vous avez jet� la carte" + this.getMain().get(i));
				return i;
			}
		}
		
		for (int i=0; i<this.getMain().size(); i++) {
			if (this.getMain().get(i).getType() == 2) {
				System.out.println("Vous avez jet� la carte" + this.getMain().get(i));
				return i;
			}
		}
		
		for (int i=0; i<this.getMain().size(); i++) {
			if (this.getMain().get(i).getType() == 3) {
				System.out.println("Vous avez jet� la carte" + this.getMain().get(i));
				return i;
			}
		}
		
		return -1;
	}
	
}