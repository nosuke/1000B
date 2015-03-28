package jeu.vue;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;


import javax.swing.JFrame;

import jeu.modele.Joueur;
import jeu.modele.Partie;

/**
 * Classe modelisant la fenetre principale du jeu
 * 
 * @author JR
 *
 */
public class FenetreJeu extends Observable {

	private JFrame fenetre;
	//menu
	private FondMenu fondMenu;
	private BoutonsMenu boutonsMenu;
	//interface de saisie des joueurs
	private InterfaceDeSaisie interfaceDeSaisie;
	//interface de jeu
	private FondJeu fondJeu;
	
	/**
	 * Constructeur par défaut
	 * 
	 * @throws HeadlessException
	 */
	public FenetreJeu() throws HeadlessException {
		super();
	}

	/**
	 * Constructeur de la fenetre du jeu.
	 * 
	 * @param title
	 * @param largeur
	 * @param hauteur
	 * @throws HeadlessException
	 */
	public FenetreJeu(String title, int largeur, int hauteur)
			throws HeadlessException {
		super();
		this.fenetre = new JFrame(title);

		this.fenetre.setSize(largeur, hauteur);

		this.fondMenu = new FondMenu("src\\images\\decor.png");
		this.boutonsMenu = new BoutonsMenu(largeur, hauteur);
		
		this.interfaceDeSaisie = new InterfaceDeSaisie(largeur, hauteur, this);
		
		this.fondJeu =new FondJeu("src\\images\\fondjeu.png");
		this.lancerMenu();
		

	}

	/**
	 * Fonction qui permet d'afficher la fenetre.
	 * 
	 */
	public void lancer() {
		
		// Le fait de fermer la fenetre ferme toute l'activité
		this.fenetre.setDefaultCloseOperation(3);

		// desactive le redimensionnement
		this.fenetre.setResizable(false);

		// centre la fenetre dans l'écran
		this.fenetre.setLocationRelativeTo(null);

		// Fonction magique
		this.fenetre.pack();
		// On rend la fenetre visible
		this.fenetre.setVisible(true);
		
	}

	/**
	 * Fonction qui lance la procédure de saisis des joueurs.
	 */
	public void saisirJoueurs()
	{
		
		this.fenetre.getContentPane().removeAll();
		this.fenetre.add(interfaceDeSaisie);
		this.fenetre.pack();
		this.fenetre.repaint();
		
		
		
	}

	/**
	 * Accesseur au fond du menu
	 * @return Fond du monde.
	 */
	public FondMenu getFondMenu() {
		return fondMenu;
	}

	/**
	 * Modificateur du fond du menu
	 * @param fond
	 */
	public void setFondMenu(FondMenu fond) {
		this.fondMenu = fond;
	}

	
	/**
	 * Accesseur aux boutons du menu
	 * @return Boutons du menu.
	 */
	public BoutonsMenu getBoutonsMenu() {
		return boutonsMenu;
	}

	/**
	 * Modificateur des boutons du menu
	 * @param boutons
	 */
	public void setBoutonsMenu(BoutonsMenu boutons) {
		this.boutonsMenu = boutons;
	}

	/**
	 * Accesseur à la fenetre
	 * @return Fenetre.
	 */
	public JFrame getFenetre() {
		return fenetre;
	}

	/**
	 * Modificateur de la fenetre
	 * @param fenetre
	 */
	public void setFenetre(JFrame fenetre) {
		this.fenetre = fenetre;
	}

	/**
	 * Fonction qui lance le menu
	 */
	public void lancerMenu() {

		this.fenetre.getContentPane().removeAll();
		this.fenetre.setContentPane(fondMenu);
		this.fenetre.add(boutonsMenu);
		
		final FenetreJeu fen = this;
		this.boutonsMenu.getJouer().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				fen.saisirJoueurs();

			}
		});
		this.fenetre.pack();
		this.fenetre.repaint();
		
	}
	
	/**
	 * Fonction qui lance le jeu 
	 * @param partie
	 */
	public void lancerJeu(Partie partie)
	{
		this.fenetre.getContentPane().removeAll();
		this.fenetre.setContentPane(fondJeu);
		
		
		
		this.fenetre.pack();
		this.fenetre.repaint();
		
		
	}
	
	/**
	 * Fonction qui lance la vue du joueur j
	 * @param j
	 */
	public void lancerVueJoueur(Joueur j)
	{
		this.fenetre.getContentPane().removeAll();
		
		this.fenetre.pack();
		this.fenetre.repaint();
	}

	/**
	 * Fonction qui notifie au controleur des changements
	 * @param o
	 */
	public void notifie(Object o) {
		
		this.setChanged();
		this.notifyObservers(o);
		
	}

	/**
	 * Fonction qui permet de terminer la partie
	 * @param arg1
	 */
	public void terminerPartie(Joueur arg1) {
		// TODO Auto-generated method stub
		
	}
	
	

}
