package jeu.modele;

import java.util.Stack;


/**
 * La Classe TasDeCartes correspond à un tas de cartes du jeu (la pile Bataille, par exemple).
 * Le comportement d'un tas de cartes est similaire à celui d'une pile.
 * 
 * @author Florent LUCET et Julie ROMERO
 * @version 1.0
 */
@SuppressWarnings("serial")
public class TasDeCartes extends Stack<Carte>{
	
	/**
	 * Constructeur sans attribut (par défaut) de la classe TasDeCartes.
	 * Initialise le nombre de cartes du tas de cartes à 100.
	 */
	public TasDeCartes() {
		super();
	}
	
}
