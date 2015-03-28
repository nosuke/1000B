package jeu.vue;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Classe modélisant le fond du menu.
 * @author JR
 *
 */
@SuppressWarnings("serial")
public class FondMenu extends JLabel{
	
	
	private ImageIcon decor;
	
	/**
	 * Constructeur de cette classe
	 * @param filename
	 */
	public FondMenu(String filename) {
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
