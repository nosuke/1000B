package jeu.modele;


/**
 * La classe Carte correspond à une carte du jeu comportant un type et un effet.
 * 
 * @author Florent LUCET et Julie ROMERO
 * @version 1.0
 */
public class Carte {
	
	/* Utilisation du tableau "effets" :
	- Selection d'un objet du tableau : effets[ligne][colonne]
	- Taille d'une colonne : effets[ligne].length
	- Nombre de lignes : effets.length
	- effets : tableau 2D non regulier?*/
	
	private static String[][] effets = {
			{ "attaque", "feu rouge", "limite de vitesse", "panne d'essence",
					"crevaison", "accident" },
			{ "parade", "feu vert", "fin de limite de vitesse", "essence",
					"roue de secours", "reparations" },
			{ "botte", "prioritaire", "prioritaire", "citerne", "increvable",
					"as du volant" },
			{ "etape", "25", "50", "75", "100", "200" } };
			
	private int type;
	private int effet;
	
	
	
	// *********************
	// *** CONSTRUCTEURS ***
	// *********************
	
	/**
	 * Constructeur avec attributs type et effet de la classe Carte.
	 * 
	 * @param type Type de la carte.
	 * @param effet Effet de la carte.
	 */
	public Carte(int type, int effet) {
		this.type = type;
		this.effet = effet;
	}
	
	/**
	 * Constructeur sans attribut (par defaut) de la classe Carte.
	 * Initialise le type et l'effet de la carte à 0.
	 */
	public Carte() {
		this.type = 0;
		this.effet = 0;
	}
	
	
	
	// ******************
	// *** ACCESSEURS ***
	// ******************
	
	/**
	 * Accesseur permettant de récupérer le type de la carte.
	 * 
	 * @return Type de la carte.
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Accesseur permettant de récupérer l'effet de la carte.
	 * 
	 * @return Effet de la carte.
	 */
	public int getEffet() {
		return effet;
	}
	
	/**
	 * Accesseur permettant de récupérer le tableau des types et effets des cartes.
	 * 
	 * @return Tableau des types et effets des cartes.
	 */
	public static String[][] getEffets() {
		return effets;
	}
	
	
	// *****************
	// *** MUTATEURS ***
	// *****************
	
	/**
	 * Mutateur permettant de changer le type de la carte.
	 * 
	 * @param type Nouveau type de la carte.
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	/**
	 * Mutateur permettant de changer le type de la carte.
	 * 
	 * @param effet Nouveau effet de la carte.
	 */
	public void setEffet(int effet) {
		this.effet = effet;
	}
	
	/**
	 * Mutateur permettant de changer le tableau des types et effets des cartes.
	 * 
	 * @param effets Tableau des types et effets des cartes.
	 */
	public static void setEffets(String[][] effets) {
		Carte.effets = effets;
	}
	
	
	
	// ***************************
	// *** MÉTHODE D'AFFICHAGE ***
	// ***************************
	
	/**
	 * Méthode permettant l'affichage des attributs de la carte, c'est-à-dire son type et son effet.
	 * 
	 * @return Chaîne de charactères comportant le type et l'effet de la carte.
	 */
	@Override
	public String toString() {
		return "[" + Carte.getEffets()[this.type][effet] + "]";
	}
	
}