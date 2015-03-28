package jeu.vue;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Classe qui modélise les boutons du menu principal
 * @author JR
 *
 */
@SuppressWarnings("serial")
public class BoutonsMenu extends JPanel{

	private JButton jouer;
	private JButton options;

	/**
	 * Constructeur de cette classe et qui a pour parametre les dimensions de la fenetre 
	 * principale
	 * @param largeur
	 * @param hauteur
	 */
	public BoutonsMenu(int largeur, int hauteur) {
		
		//on cree et configure notre panel
		super();
		this.setLayout(null);	
		this.setSize(largeur, hauteur);
		
		//on cree nos boutons
		this.jouer = new JButton("Jouer");
		this.options = new JButton("Options");
		
		//Ici on place les boutons
		this.jouer.setBounds((largeur - 200)/2, (hauteur-70)/2-140, 200, 70);
		this.options.setBounds((largeur - 200)/2, (hauteur-70)/2 , 200, 70);

		//on les ajoute
		this.add(this.jouer);
		this.add(this.options);
		
		// rend visible le fond
		this.setOpaque(false); 

	}

	/**
	 * Accesseur du bouton jouer
	 * @return Bouton jouer.
	 */
	public JButton getJouer() {
		return jouer;
	}

	/**
	 * Modificateur du bouton joueur
	 * @param jouer
	 */
	public void setJouer(JButton jouer) {
		this.jouer = jouer;
	}

	/**
	 * Accesseur du bouton options
	 * @return Bouton des options
	 */
	public JButton getOptions() {
		return options;
	}

	/**
	 * Modificateur du bouton options
	 * @param options
	 */
	public void setOptions(JButton options) {
		this.options = options;
	}
	
	

	
	
	
	
}
