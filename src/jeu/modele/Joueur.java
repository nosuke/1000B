package jeu.modele;

import java.util.ArrayList;


/**
 * La classe Joueur correspond à un joueur du jeu comportant un nom, un score,
 * une main de cartes, une possibilite de démarrer, la carte attaque active sur
 * lui (s'il y en a une), la carte vitesse active sur lui (s'il y en a une) et
 * ses immunités.
 * 
 * @author Florent LUCET et Julie ROMERO
 * @version 1.0
 */
public abstract class Joueur {
	
	// Identité du joueur.
	private String nom;
	private int score;
	
	// Main de cartes du joueur.
	private ArrayList<Carte> main;
	
	// Cartes jouées sur le joueur.
	private boolean demarre;
	private Carte attaque;
	private Carte vitesse;
	private ArrayList<Carte> immunites;
	
	
	
	// ********************
	// *** CONSTRUCTEUR ***
	// ********************
	
	/**
	 * Constructeur avec l'attribut nom de la classe Joueur.
	 * Les autres attributs que le nom du joueur sont initialisés.
	 * 
	 * @param nom
	 *            Nom du joueur.
	 */
	public Joueur(String nom) {
		this.nom = nom;
		this.score = 0;
		this.main = new ArrayList<Carte>();
		this.demarre = false;
		this.attaque = null;
		this.vitesse = null;
		this.immunites = new ArrayList<Carte>();
	}
	
	
	
	// ********************************
	// *** MÉTHODES SUR LES JOUEURS ***
	// ********************************
	
	/**
	 * Méthode abstraite permettant de définir le déroulement du tour d'un
	 * joueur, qui se fera différemment s'il est réel ou virtuel.
	 * 
	 * @param joueurs Liste des joueurs de la partie.
	 * @param defausse Défausse de la partie.
	 * @param pioche Pioche de la partie.
	 */
	public abstract void deroulementTour(ArrayList<Joueur> joueurs,
			TasDeCartes defausse, TasDeCartes pioche);
	
	/**
	 * Méthode permettant de vérifier si deux instances de la classe Joueur
	 * correspondent au même joueur.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Joueur other = (Joueur) obj;
		if (attaque == null) {
			if (other.attaque != null)
				return false;
		} else if (!attaque.equals(other.attaque))
			return false;
		if (demarre != other.demarre)
			return false;
		if (immunites == null) {
			if (other.immunites != null)
				return false;
		} else if (!immunites.equals(other.immunites))
			return false;
		if (main == null) {
			if (other.main != null)
				return false;
		} else if (!main.equals(other.main))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (score != other.score)
			return false;
		if (vitesse == null) {
			if (other.vitesse != null)
				return false;
		} else if (!vitesse.equals(other.vitesse))
			return false;
		return true;
	}
	
	
	
	// ***********************************************
	// *** MÉTHODES DE LA PIOCHE ET DE LA DÉFAUSSE ***
	// ***********************************************
	
	/**
	 * Méthode permettant de piocher une carte dans le tas de cartes indiqué en
	 * paramètre.
	 * 
	 * @param pioche
	 *            Tas de cartes dans lequel une carte est piochée.
	 */
	public void piocherCarte(TasDeCartes pioche) {
		this.main.add((Carte) pioche.pop());

	}
	
	/**
	 * Méthode permettant d'envoyer une carte à la defausse.
	 * 
	 * @param carte
	 *            Carte envoyée à la defausse.
	 * @param defausse
	 *            Tas de cartes correspondant à la defausse.
	 */
	public void defausserCarte(Carte carte, TasDeCartes defausse) {

		defausse.push(carte);
		this.main.remove(carte);
	}
	
	
	
	// ***********************
	// *** MÉTHODE D'ÉTAPE ***
	// ***********************
	
	/**
	 * Méthode permettant de jouer une carte de type étape.
	 * 
	 * @param carteEtape
	 *            Carte étape jouée.
	 */
	public void poserEtape(Carte carteEtape) {
		int bornes = carteEtape.getEffet();
		bornes = Integer.parseInt(Carte.getEffets()[3][bornes]);
		this.score += bornes;
		this.main.remove(carteEtape);

	}
	
	
	// **************************
	// *** MÉTHODES D'ATTAQUE ***
	// **************************
	
	/**
	 * Méthode permettant d'attaquer un autre joueur avec une carte de type
	 * attaque.
	 * 
	 * @param carteAttaque
	 *            Carte attaque jouée.
	 * @param joueur
	 *            Joueur attaqué.
	 */
	public void attaquer(Carte carteAttaque, Joueur joueur) {
		joueur.setAttaque(carteAttaque);
		this.main.remove(carteAttaque);
	}
	
	/**
	 * Méthode permettant de savoir si le joueur peut utiliser une carte de type
	 * attaque.
	 * 
	 * @param attaque Carte attaque que le joueur souhaite poser.
	 * @param joueurs Liste des joueurs présents dans la partie de jeu.
	 * @return Si le joueur peut utiliser une carte de type attaque.
	 */
	protected boolean aUnJoueurAttaquable(Carte attaque,
			ArrayList<Joueur> joueurs) {
		boolean aUnJoueurAttaquable = false;
		Joueur j = null;
		for (int i = 0; !aUnJoueurAttaquable && i < joueurs.size(); i++) {
			if (!this.equals(joueurs.get(i))) {
				j = joueurs.get(i);

				if (j.getImmunites().size() != 0) {
						for (int k = 0; !aUnJoueurAttaquable
								&& k < j.getImmunites().size(); k++) {

							if ((j.getImmunites().get(k).getEffet() != attaque
									.getEffet()) && (j.getAttaque() == null))
								aUnJoueurAttaquable = true;
						}

				} else if (!aUnJoueurAttaquable && j.getAttaque() == null)
					aUnJoueurAttaquable = true;

			}
		}
		return aUnJoueurAttaquable;
	}
	
	
	// ***************************
	// *** MÉTHODES DE DÉFENSE ***
	// ***************************
	
	/**
	 * Méthode permettant de parer une carte de type attaque jouée précédemment
	 * dans la partie.
	 * 
	 * @param carteParade
	 *            Carte parade jouée.
	 * @param defausse
	 *            Tas de cartes correspondant à la defausse.
	 */
	public void poserParade(Carte carteParade, TasDeCartes defausse) {
		if (!demarre && carteParade.getEffet() == 1)
			this.demarre = true;
		else {
			defausse.push(this.attaque);
			defausse.push(carteParade);
		}
		this.attaque = null;
		this.main.remove(carteParade);
		
	}
	
	/**
	 * Méthode permettant de parer une carte de type attaque d'effet
	 * "limite de vitesse" jouée précédemment dans la partie.
	 * 
	 * @param carteParade
	 *            Carte parade jouée.
	 * @param defausse
	 *            Tas de cartes correspondant à la defausse.
	 */
	public void poserFinLimite(Carte carteParade, TasDeCartes defausse) {
		defausse.push(this.vitesse);
		defausse.push(carteParade);
		this.vitesse = null;
		this.main.remove(carteParade);

	}
	
	
	// ************************
	// *** MÉTHODE DE BOTTE ***
	// ************************
	
	/**
	 * Méthode permettant de poser une botte en l'ajoutant à la liste des immunités
	 * et en l'enlevant à la main du joueur.
	 * 
	 * @param botte Botte posée par le joueur.
	 */
	public void poserBotte(Carte botte) {
		this.immunites.add(botte);
		this.main.remove(botte);
	}
	
	
	
	// ******************
	// *** ACCESSEURS ***
	// ******************
	
	/**
	 * Accesseur permettant de récupérer le nom du joueur.
	 * 
	 * @return Nom du joueur.
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Accesseur permettant de récupérer le score du joueur.
	 * 
	 * @return Score du joueur.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Accesseur permettant de récupérer la main de cartes du joueur.
	 * 
	 * @return Main de cartes du joueur.
	 */
	public ArrayList<Carte> getMain() {
		return main;
	}
	
	/**
	 * Accesseur permettant de récupérer la possibilité de démarrer du joueur.
	 * 
	 * @return Possibilité de démarrer du joueur.
	 */
	public boolean isDemarre() {
		return demarre;
	}
	
	/**
	 * Accesseur permettant de récupérer la carte attaque active sur le joueur.
	 * 
	 * @return Carte attaque active sur le joueur.
	 */
	public Carte getAttaque() {
		return attaque;
	}
	
	/**
	 * Accesseur permettant de récupérer la carte vitesse active sur le joueur.
	 * 
	 * @return Carte vitesse active sur le joueur.
	 */
	public Carte getVitesse() {
		return vitesse;
	}
	
	/**
	 * Accesseur permettant de récupérer les immunités du joueur.
	 * 
	 * @return Immunités du joueur.
	 */
	public ArrayList<Carte> getImmunites() {
		return immunites;
	}
	
	
	// *****************
	// *** MUTATEURS ***
	// *****************
	
	/**
	 * Mutateur permettant de changer le nom du joueur.
	 * 
	 * @param nom
	 *            Nouveau nom du joueur.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Mutateur permettant de changer le score du joueur.
	 * 
	 * @param score
	 *            Nouveau score du joueur.
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Mutateur permettant de changer la main du joueur.
	 * 
	 * @param main
	 *            Nouvelle main du joueur.
	 */
	public void setMain(ArrayList<Carte> main) {
		this.main = main;
	}
	
	/**
	 * Mutateur permettant de changer la possibilité de démarrer du joueur.
	 * 
	 * @param demarre
	 *            Possibilité de démarrer du joueur.
	 */
	public void setDemarre(boolean demarre) {
		this.demarre = demarre;
	}
	
	/**
	 * Mutateur permettant de changer la carte attaque active sur le joueur.
	 * 
	 * @param attaque
	 *            Nouvelle carte attaque active sur le joueur.
	 */
	public void setAttaque(Carte attaque) {
		this.attaque = attaque;
	}
	
	/**
	 * Mutateur permettant de changer la carte vitesse active sur le joueur.
	 * 
	 * @param vitesse
	 *            Nouvelle carte vitesse active sur le joueur.
	 */
	public void setVitesse(Carte vitesse) {
		this.vitesse = vitesse;
	}
	
	/**
	 * Mutateur permettant de changer les immunités du joueur.
	 * 
	 * @param immunites
	 *            Nouvelles immunités du joueur.
	 */
	public void setImmunites(ArrayList<Carte> immunites) {
		this.immunites = immunites;
	}
	
	
	
	// ****************************
	// *** MÉTHODES D'AFFICHAGE ***
	// ****************************
	
	/**
	 * Méthode permettant l'affichage des attributs du joueur, c'est-à-dire son
	 * nom, son score, sa main, sa possibilité de démarrer, la carte attaque
	 * active sur lui (s'il y en a une), la carte vitesse active sur lui (s'il y
	 * en a une) et ses immunités.
	 * 
	 * @return Chaîne de charactères comportant le nom, le score, la main, la
	 *         possibilité de démarrer, la carte attaque active sur lui (s'il y
	 *         en a une), la carte vitesse active sur lui (s'il y en a une) et
	 *         les immunités du joueur.
	 */
	public String toString() {
		return "Joueur [nom=" + nom + ", score=" + score + ", main=" + main
				+ ", demarre=" + demarre + ", attaque=" + attaque
				+ ", vitesse=" + vitesse + ", immunites=" + immunites + "]";
	}
	
	/**
	 * Méthode permettant l'affichage détaillé de la main du joueur.
	 */
	public void ecrireMain() {
		System.out.print("Contenu de la main: [ ");
		int i = 0;
		for (i = 0; i < this.getMain().size() - 1; i++) {
			System.out.print("Carte n°" + i + " : " + this.getMain().get(i)
					+ ", ");
		}
		System.out
				.println("Carte n°" + i + " : " + this.getMain().get(i) + " ]");
	}
	
}