package jeu;

import jeu.controleur.Controleur;


/**
 * La classe InterfaceGraphique correspond à la classe principale du lancement en
 * interface graphique du projet de 1000 bornes.
 * L'exécution du projet de 1000 bornes peut donc commencer donc dans cette classe.
 * 
 * @author Florent LUCET et Julie ROMERO
 * @version 1.0
 */
public class InterfaceGraphique {
	
	// **************************
	// *** MÉTHODE PRINCIPALE ***
	// **************************
	
	/**
	 * La méthode main est la méthode principale du projet de 1000 bornes.
	 * L'exécution du projet de 1000 bornes peut donc commencer dans cette méthode.
	 * 
	 * @param args Les arguments en lignes de commande.
	 */
	public static void main(String[] args) {
		Controleur c = new Controleur(1000, 750);
		c.lancer();
	}
	
}