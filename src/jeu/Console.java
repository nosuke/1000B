package jeu;

import java.util.ArrayList;

import jeu.modele.Joueur;
import jeu.modele.JoueurReel;
import jeu.modele.JoueurVirtuel;
import jeu.modele.Partie;


/**
 * La classe Console correspond à la classe principale du lancement en console
 * du projet de 1000 bornes.
 * L'exécution du projet de 1000 bornes peut donc commencer donc dans cette classe.
 * 
 * @author Florent LUCET et Julie ROMERO
 * @version 1.0
 */
public class Console {
	
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
		Joueur gagnant;
		
		Joueur j1 = new JoueurReel("Jean-Pierre");
		Joueur j2 = new JoueurVirtuel("Marie-Françoise");
		
		Joueur j3 = new JoueurReel("Bob");
		Joueur j4 = new JoueurReel("Geneviève");
		
		ArrayList<Joueur> al = new ArrayList<Joueur>();
		al.add(j1);
		al.add(j2);
		
		al.add(j3);
		al.add(j4);
		
		Partie p = new Partie(al);
		p.initialiser();
		gagnant = p.lancer();
		
		System.out.println("Et le gagnant est : " + gagnant.getNom() + " !");
	}
	
}