package jeu.modele;

import java.util.ArrayList;


/**
 * La classe Joueur correspond � un joueur du jeu comportant un nom, un score,
 * une main de cartes, une possibilite de d�marrer, la carte attaque active sur
 * lui (s'il y en a une), la carte vitesse active sur lui (s'il y en a une) et
 * ses immunit�s.
 * 
 * @author Florent LUCET et Julie ROMERO
 * @version 1.0
 */
public abstract class Joueur {
	
	// Identit� du joueur.
	private String nom;
	private int score;
	
	// Main de cartes du joueur.
	private ArrayList<Carte> main;
	
	// Cartes jou�es sur le joueur.
	private boolean demarre;
	private Carte attaque;
	private Carte vitesse;
	private ArrayList<Carte> immunites;
	
	
	
	// ********************
	// *** CONSTRUCTEUR ***
	// ********************
	
	/**
	 * Constructeur avec l'attribut nom de la classe Joueur.
	 * Les autres attributs que le nom du joueur sont initialis�s.
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
	// *** M�THODES SUR LES JOUEURS ***
	// ********************************
	
	/**
	 * M�thode abstraite permettant de d�finir le d�roulement du tour d'un
	 * joueur, qui se fera diff�remment s'il est r�el ou virtuel.
	 * 
	 * @param joueurs Liste des joueurs de la partie.
	 * @param defausse D�fausse de la partie.
	 * @param pioche Pioche de la partie.
	 */
	public abstract void deroulementTour(ArrayList<Joueur> joueurs,
			TasDeCartes defausse, TasDeCartes pioche);
	
	/**
	 * M�thode permettant de v�rifier si deux instances de la classe Joueur
	 * correspondent au m�me joueur.
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
	// *** M�THODES DE LA PIOCHE ET DE LA D�FAUSSE ***
	// ***********************************************
	
	/**
	 * M�thode permettant de piocher une carte dans le tas de cartes indiqu� en
	 * param�tre.
	 * 
	 * @param pioche
	 *            Tas de cartes dans lequel une carte est pioch�e.
	 */
	public void piocherCarte(TasDeCartes pioche) {
		this.main.add((Carte) pioche.pop());

	}
	
	/**
	 * M�thode permettant d'envoyer une carte � la defausse.
	 * 
	 * @param carte
	 *            Carte envoy�e � la defausse.
	 * @param defausse
	 *            Tas de cartes correspondant � la defausse.
	 */
	public void defausserCarte(Carte carte, TasDeCartes defausse) {

		defausse.push(carte);
		this.main.remove(carte);
	}
	
	
	
	// ***********************
	// *** M�THODE D'�TAPE ***
	// ***********************
	
	/**
	 * M�thode permettant de jouer une carte de type �tape.
	 * 
	 * @param carteEtape
	 *            Carte �tape jou�e.
	 */
	public void poserEtape(Carte carteEtape) {
		int bornes = carteEtape.getEffet();
		bornes = Integer.parseInt(Carte.getEffets()[3][bornes]);
		this.score += bornes;
		this.main.remove(carteEtape);

	}
	
	
	// **************************
	// *** M�THODES D'ATTAQUE ***
	// **************************
	
	/**
	 * M�thode permettant d'attaquer un autre joueur avec une carte de type
	 * attaque.
	 * 
	 * @param carteAttaque
	 *            Carte attaque jou�e.
	 * @param joueur
	 *            Joueur attaqu�.
	 */
	public void attaquer(Carte carteAttaque, Joueur joueur) {
		joueur.setAttaque(carteAttaque);
		this.main.remove(carteAttaque);
	}
	
	/**
	 * M�thode permettant de savoir si le joueur peut utiliser une carte de type
	 * attaque.
	 * 
	 * @param attaque Carte attaque que le joueur souhaite poser.
	 * @param joueurs Liste des joueurs pr�sents dans la partie de jeu.
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
	// *** M�THODES DE D�FENSE ***
	// ***************************
	
	/**
	 * M�thode permettant de parer une carte de type attaque jou�e pr�c�demment
	 * dans la partie.
	 * 
	 * @param carteParade
	 *            Carte parade jou�e.
	 * @param defausse
	 *            Tas de cartes correspondant � la defausse.
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
	 * M�thode permettant de parer une carte de type attaque d'effet
	 * "limite de vitesse" jou�e pr�c�demment dans la partie.
	 * 
	 * @param carteParade
	 *            Carte parade jou�e.
	 * @param defausse
	 *            Tas de cartes correspondant � la defausse.
	 */
	public void poserFinLimite(Carte carteParade, TasDeCartes defausse) {
		defausse.push(this.vitesse);
		defausse.push(carteParade);
		this.vitesse = null;
		this.main.remove(carteParade);

	}
	
	
	// ************************
	// *** M�THODE DE BOTTE ***
	// ************************
	
	/**
	 * M�thode permettant de poser une botte en l'ajoutant � la liste des immunit�s
	 * et en l'enlevant � la main du joueur.
	 * 
	 * @param botte Botte pos�e par le joueur.
	 */
	public void poserBotte(Carte botte) {
		this.immunites.add(botte);
		this.main.remove(botte);
	}
	
	
	
	// ******************
	// *** ACCESSEURS ***
	// ******************
	
	/**
	 * Accesseur permettant de r�cup�rer le nom du joueur.
	 * 
	 * @return Nom du joueur.
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Accesseur permettant de r�cup�rer le score du joueur.
	 * 
	 * @return Score du joueur.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Accesseur permettant de r�cup�rer la main de cartes du joueur.
	 * 
	 * @return Main de cartes du joueur.
	 */
	public ArrayList<Carte> getMain() {
		return main;
	}
	
	/**
	 * Accesseur permettant de r�cup�rer la possibilit� de d�marrer du joueur.
	 * 
	 * @return Possibilit� de d�marrer du joueur.
	 */
	public boolean isDemarre() {
		return demarre;
	}
	
	/**
	 * Accesseur permettant de r�cup�rer la carte attaque active sur le joueur.
	 * 
	 * @return Carte attaque active sur le joueur.
	 */
	public Carte getAttaque() {
		return attaque;
	}
	
	/**
	 * Accesseur permettant de r�cup�rer la carte vitesse active sur le joueur.
	 * 
	 * @return Carte vitesse active sur le joueur.
	 */
	public Carte getVitesse() {
		return vitesse;
	}
	
	/**
	 * Accesseur permettant de r�cup�rer les immunit�s du joueur.
	 * 
	 * @return Immunit�s du joueur.
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
	 * Mutateur permettant de changer la possibilit� de d�marrer du joueur.
	 * 
	 * @param demarre
	 *            Possibilit� de d�marrer du joueur.
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
	 * Mutateur permettant de changer les immunit�s du joueur.
	 * 
	 * @param immunites
	 *            Nouvelles immunit�s du joueur.
	 */
	public void setImmunites(ArrayList<Carte> immunites) {
		this.immunites = immunites;
	}
	
	
	
	// ****************************
	// *** M�THODES D'AFFICHAGE ***
	// ****************************
	
	/**
	 * M�thode permettant l'affichage des attributs du joueur, c'est-�-dire son
	 * nom, son score, sa main, sa possibilit� de d�marrer, la carte attaque
	 * active sur lui (s'il y en a une), la carte vitesse active sur lui (s'il y
	 * en a une) et ses immunit�s.
	 * 
	 * @return Cha�ne de charact�res comportant le nom, le score, la main, la
	 *         possibilit� de d�marrer, la carte attaque active sur lui (s'il y
	 *         en a une), la carte vitesse active sur lui (s'il y en a une) et
	 *         les immunit�s du joueur.
	 */
	public String toString() {
		return "Joueur [nom=" + nom + ", score=" + score + ", main=" + main
				+ ", demarre=" + demarre + ", attaque=" + attaque
				+ ", vitesse=" + vitesse + ", immunites=" + immunites + "]";
	}
	
	/**
	 * M�thode permettant l'affichage d�taill� de la main du joueur.
	 */
	public void ecrireMain() {
		System.out.print("Contenu de la main: [ ");
		int i = 0;
		for (i = 0; i < this.getMain().size() - 1; i++) {
			System.out.print("Carte n�" + i + " : " + this.getMain().get(i)
					+ ", ");
		}
		System.out
				.println("Carte n�" + i + " : " + this.getMain().get(i) + " ]");
	}
	
}