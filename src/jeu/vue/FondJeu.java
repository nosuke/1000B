package jeu.vue;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Classe qui modelise le fond du jeu.
 * 
 * @author JR
 *
 */
public class FondJeu extends JLabel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon decor;
	
	/**
	 * Constructeur de cette classe.
	 * 
	 * @param filename
	 */
	public FondJeu(String filename) {
		//on cree notre JLabel
		super();
		
		//On cree notre decor
		this.decor = new ImageIcon(filename);
		
		//on l'ajoute
		this.setIcon(decor);
		
		//On configure l'emplacement
		this.setLayout(new BorderLayout());
	}


}
