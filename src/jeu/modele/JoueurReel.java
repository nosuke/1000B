package jeu.modele;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Scanner;


/**
 * La classe JoueurReel correspond à un joueur réel du jeu comportant un nom, un
 * score, une main de cartes, une possibilite de démarrer, la carte attaque active
 * sur lui (s'il y en a une), la carte vitesse active sur lui (s'il y en a une), ses
 * immunités et sa dernière saisie.
 * 
 * @author Florent LUCET et Julie ROMERO
 * @version 1.0
 */
public class JoueurReel extends Joueur {
	
	private Scanner sc;
	
	
	
	// ********************
	// *** CONSTRUCTEUR ***
	// ********************
	
	/**
	 * Constructeur avec attribut nom de la classe JoueurReel.
	 * 
	 * @param nom Nom du joueur réel.
	 */
	public JoueurReel(String nom) {
		super(nom);
		this.sc = new Scanner(System.in);
	}
	
	
	
	// ********************************************
	// *** MÉTHODES DE DÉROULEMENT DE LA PARTIE ***
	// ********************************************
	
	/**
	 * Méthode permettant de définir le déroulement du tour d'un joueur.
	 * 
	 * @param joueurs Liste des joueurs de la partie.
	 * @param defausse Défausse de la partie.
	 * @param pioche Pioche de la partie.
	 */
	public void deroulementTour(ArrayList<Joueur> joueurs,
			TasDeCartes defausse, TasDeCartes pioche) {
		
		Carte carteajouer = new Carte();
		boolean cartejouee = false;
		
		// Demande de saisie du tas où le joueur veut piocher.
		this.saisiePioche(defausse, pioche);
		int numSaisie = -1;
		
		while (!cartejouee) {
			/* Demande de saisie du numéro de carte de la carte que le joueur veut
			jouer. */
			numSaisie = this.saisieNumeroCarte();
			
			/* Si le joueur rentre "stop", c'est qu'il ne souhaite pas jouer
			pour ce tour.
			On arrête alors le tour. */
			if (numSaisie == -2)
				cartejouee = true;
			
			// Si la saisie est valide, la carte est jouée.
			else if (numSaisie != -1) {
				// Conservation de la carte à jouer.
				carteajouer = this.getMain().get(numSaisie);
				// Vérification de la jouabilité de la carte.
				cartejouee = this.jouerCarte(carteajouer, joueurs, defausse);
			}
		}
		while (this.getMain().size() > 4) {
				numSaisie = this.saisieCarteADefausser();
				this.defausserCarte(this.getMain().get(numSaisie), defausse);
		}
	}
	
	/**
	 * Méthode permettant à un joueur de jouer une carte de sa main.
	 * 
	 * @param carteajouer Carte que le joueur souhaite jouer.
	 * @param joueurs Liste des joueurs de la partie.
	 * @param defausse Défausse de la parie.
	 * @return Vrai si la carte est jouable et jouée, faux sinon.
	 */
	private boolean jouerCarte(Carte carteajouer, ArrayList<Joueur> joueurs,
			TasDeCartes defausse) {
		int typecarteajouer = carteajouer.getType();
		boolean possible = false;
		
		switch (typecarteajouer) {
		
		// ****************
		// *** ATTAQUES ***
		// ****************
		
		case 0:

			// Vérification de la possibilité d'attaquer un joueur adverse.
			if (this.aUnJoueurAttaquable(carteajouer, joueurs)) {
				possible = true;
				this.jouerCarteAttaque(carteajouer, joueurs);
			}
			else
				System.out.println("Personne n'est attaquable.");
			
			break;
			
			
		// ****************
		// *** DÉFENSES ***
		// ****************
		
		case 1:
			/* Vérification de l'utilisation d'une carte "feu vert".
			Deux utilisations possibles selon qu'on soit en début de partie
			ou après avoir démarré au moins une fois. */
			if (!this.isDemarre() && carteajouer.getEffet() == 1) {
				possible = true;
				this.poserParade(carteajouer, defausse);
			}
			
			// Vérification que la joueur a bien été attaqué.
			else if (this.getAttaque() != null || this.getVitesse() != null) {

				/* Vérification de la correspondance entre la carte attaque et
				la carte parade. */
				if (this.getAttaque() != null
						&& this.getAttaque().getEffet() == carteajouer
								.getEffet()) {
					possible = true;
					this.poserParade(carteajouer, defausse);
				} else if (this.getVitesse() != null
						&& this.getVitesse().getEffet() == carteajouer
								.getEffet()) {
					possible = true;
					this.poserFinLimite(carteajouer, defausse);
				}
			} else
				System.out.println("Vous n'avez pas été attaqué !");
			
			break;
			
			
		// **************
		// *** BOTTES ***
		// **************
		
		case 2:
			// Utilisation en préventif.
			if(this.getImmunites().size()!=2)
			{
				this.poserBotte(carteajouer);
				possible = true;
			} else
				System.out.println("Vous disposez déjà de deux bottes.");
			
			break;
			
			
		// **************
		// *** ÉTAPES ***
		// **************
		
		case 3:
			// Vérification qu'un feu vert a déjà été posé au moins une fois.
			if (this.isDemarre()) {
				// Vérification qu'aucune attaque ne bloque l'avancement.
				if (this.getAttaque() == null) {
					// Vérification de la limite de vitesse.
					if (this.getVitesse() != null) {
						if ((int) carteajouer.getEffet() <= 50) {
							possible = true;
							this.poserEtape(carteajouer);
						} else
							System.out.println("Vous êtes limité à 50.");
					} else if (this.getScore() + (int) carteajouer.getEffet() > 1000)
						System.out.println("Vous ne pouvez pas dépasser mille bornes !");
					else {
						possible = true;
						this.poserEtape(carteajouer);
					}
				} else
					System.out.println("Vous avez été attaqué !");
			} else
				System.out.println("Veuillez poser un feu vert pour pouvoir démarrer.");
			
			break;
			
		}
		return possible;
	}
	
	/**
	 * Méthode permettant à un jouer de jouer une carte attaque de sa main.
	 * 
	 * @param carteajouer Carte que le joueur souhaite jouer.
	 * @param joueurs Liste des joueurs de la partie.
	 */
	private void jouerCarteAttaque(Carte carteajouer, ArrayList<Joueur> joueurs) {
		Joueur joueuraattaque;
		boolean valide = false;
		String saisie;
		int numSaisie;
		System.out.println("Sur qui voulez-vous jouer cette carte ? ");
		for (int i = 0; i < joueurs.size(); i++) {
			joueuraattaque = joueurs.get(i);
			if (!joueuraattaque.equals(this)
					&& joueuraattaque.getAttaque() == null)
				System.out.println("Joueur " + i + " :[ nom:"
						+ joueuraattaque.getNom() + ", score: "
						+ joueuraattaque.getScore() + ", attaque: "
						+ joueuraattaque.getAttaque() + ", bottes: "
						+ joueuraattaque.getImmunites() + ", vitesse: "
						+ joueuraattaque.getVitesse());
		}
		while (!valide) {
			saisie = sc.nextLine();
			numSaisie = this.typeNumerique(saisie);
			if (numSaisie != -1) {
				if (numSaisie >= 0 && numSaisie < joueurs.size()
						&& !this.equals(joueurs.get(numSaisie))) {
					joueuraattaque = joueurs.get(numSaisie);
					if (joueuraattaque.getAttaque() == null) {
						valide = true;
						this.attaquer(carteajouer, joueuraattaque);
					} else
						System.out.println("Ce joueur a déjà attaqué.");
				} else
					System.out
							.println("Ce numéro ne correspond à aucun joueur adverse.");
			} else
				System.out
						.println("Votre saisie ne correspond pas à un chiffre.");
		}
	}
	
	
	
	// ************************************
	// *** MÉTHODES DE SAISIE DU JOUEUR ***
	// ************************************
	
	/**
	 * Méthode qui va demander au joueur la saisie du tas où il veut piocher.
	 * 
	 * @param defausse Défausse de la partie.
	 * @param pioche Pioche de la partie.
	 */
	private void saisiePioche(TasDeCartes defausse, TasDeCartes pioche) {
		boolean valide = false;
		String saisie = new String();
		int numSaisie = -1;
		
		// Si la défausse est vide, une exception est levée sur le peek().
		try {
			
			// Si la défausse n'est pas vide, le choix de la pioche est laissé au joueur.
			System.out.println("Dernière carte de la défausse : "
					+ defausse.peek());
			System.out
					.println("Voulez-vous piocher une carte dans la défausse ou la pioche ? Tapez 1 pour la défausse ou 2 pour la pioche.");
			
			// Tant que la saisie n'est pas valide, la saisie est redemandée.
			while (!valide) {
				
				// Récupération de la saisie rentrée dans la console.
				saisie = this.sc.nextLine();
				
				// Vérification que la saisie est bien un entier.
				numSaisie = this.typeNumerique(saisie);
				
				// Si la saisie est un entier…
				if (numSaisie != -1) {
					
					/* Une vérification permet de voir si celle-ci correspond à 1 ou à 2.
					Si c'est le cas, on agit en fonction. */
					if (numSaisie == 1) {
						valide = true;
						this.piocherCarte(defausse);
					} else if (numSaisie == 2) {
						this.piocherCarte(pioche);
						valide = true;
					} else
						System.out
								.println("Ce numéro ne correspond à aucune commande. Tapez 1 pour la défausse ou 2 pour la pioche.");
				} else
					System.out
							.println("Commande non valide. Tapez 1 pour la défausse ou 2 pour la pioche.");
			}
			
		} catch (EmptyStackException e) {
			
			/* Si la défausse est vide, le joueur n'a pas le choix et il tire
			obligatoirement dans la pioche. */
			System.out.println("Dernière carte de la défausse : rien.");
			System.out.println("Vous devez tirer dans la pioche.");
			this.piocherCarte(pioche);
		}
	}

	/**
	 * Méthode permettant de récupérer un numéro de carte valide correspondant
	 * à la carte que souhaite jouer le joueur.
	 * 
	 * @return Numéro de carte saisi.
	 */
	private int saisieNumeroCarte() {
		// Tant que la saisie n'est pas valide, on recommence la saisie.
		boolean valide = false;
		String saisie = new String();
		int numSaisie = -2;
		
		// La main est affichée pour que le joueur puisse voir les numéros des cartes.
		this.ecrireMain();
		System.out.println("Votre score : " + this.getScore());
		System.out.println("Les attaques proferées contre vous : " + this.getAttaque() + " , " + this.getVitesse());
		System.out.println("Vos immunités : " + this.getImmunites());
		System.out
				.println("Que voulez vous faire ? Tapez le numéro de la carte que vous voulez utiliser. Ou \"stop\" si vous ne souhaitez pas utiliser de carte.");
		
		while (!valide) {
			// Récupération de la saisie rentrée dans la console.
			saisie = this.sc.nextLine();
			
			// Si le joueur ne souhaite pas jouer, la saisie n'est plus demandée.
			if (saisie.equals("stop")) {
				valide = true;
				return -2;
			}
			
			// Vérification que la saisie est bien un entier.
			numSaisie = this.typeNumerique(saisie);
			
			// Si la saisie est un entier…
			if (numSaisie != -1) {
				
				/* Vérification qu'elle correspond bien à l'index d'une carte de la
				main du joueur. */
				if (numSaisie >= 0 && numSaisie < this.getMain().size()) {
					valide = true;
				} else
					System.out
							.println("Ce numéro ne correspond à aucune carte. Tapez le numéro de la carte que vous voulez jouer.");
			} else {
				System.out
						.println("Commande non valide. Tapez le numéro de la carte que vous voulez jouer.");
			}
			
		}
		return numSaisie;
	}
	
	/**
	 * Méthode permettant de récupérer un numéro de carte valide correspondant
	 * à la carte que souhaite jeter à la défausse le joueur.
	 * 
	 * @return Numéro de carte saisi.
	 */
	private int saisieCarteADefausser() {
		// Tant que la saisie n'est pas valide, on recommence la saisie.
		boolean valide = false;
		String saisie = new String();
		int numSaisie = -1;
		
		// La main est affichée pour que le joueur puisse voir les numéros des cartes.
		this.ecrireMain();
		System.out
				.println("Quelle carte voulez-vous jeter ? Tapez le numéro de la carte que vous voulez jeter.");
		
		while (!valide) {
			// Récupération de la saisie rentrée dans la console.
			saisie = this.sc.nextLine();
			
			// Vérification que la saisie est bien un entier.
			numSaisie = this.typeNumerique(saisie);
			
			// Si la saisie est bien un entier.
			if (numSaisie != -1) {
				
				// Vérification que la saisie correspond bien à l'index d'une carte.
				if (numSaisie >= 0 && numSaisie < this.getMain().size()) {
					valide = true;
				} else
					System.out
							.println("Ce numéro ne correspond à aucune carte. Tapez le numéro de la carte que vous voulez jeter.");
			} else {
				System.out
						.println("Commande non valide. Tapez le numéro de la carte que vous voulez jeter.");
			}
		}
		return numSaisie;
	}
	
	
	
	// *******************************
	// *** MÉTHODE DE VÉRIFICATION ***
	// *******************************
	
	/**
	 * Méthode permettant de tester si une chaîne de caractères est un entier.
	 * Retourne l'entier si c'est le cas, -1 si ce n'est pas le cas.
	 * 
	 * @param chaine
	 *            Chaîne de caractères.
	 * @return Entier contenu dans la chaîne de caractères ou -1.
	 */
	private int typeNumerique(String chaine) {
		try {
			return Integer.parseInt(chaine);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
}