package jeu.vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import jeu.modele.Joueur;
import jeu.modele.JoueurReel;

/**
 * Classe modelisant l'interface de saisie des joueurs.
 * @author JR
 *
 */
@SuppressWarnings("serial")
public class InterfaceDeSaisie extends JPanel {
	@SuppressWarnings("rawtypes")
	private JComboBox jcb1 = new JComboBox();
	private JTextArea nomj1 = new JTextArea("Nom du Joueur");
	@SuppressWarnings("rawtypes")
	private JComboBox jcb2 = new JComboBox();
	private JTextArea nomj2 = new JTextArea("Nom du Joueur");
	@SuppressWarnings("rawtypes")
	private JComboBox jcb3 = new JComboBox();
	private JTextArea nomj3 = new JTextArea("Nom du Joueur");
	@SuppressWarnings("rawtypes")
	private JComboBox jcb4 = new JComboBox();
	private JTextArea nomj4 = new JTextArea("Nom du Joueur");

	/**
	 * Constructeur de cette classe
	 * @param largeur
	 * @param hauteur
	 * @param fenetreJeu
	 */
	public InterfaceDeSaisie(int largeur, int hauteur, FenetreJeu fenetreJeu) {

		// on cree et configure notre panel
		super();
		this.setLayout(null);
		this.setSize(largeur, hauteur);
		int largeurPanel = (largeur - (2 * 200 + 30)) / 2;
		int hauteurPanel = (hauteur - ((5 * 35) + (4 * 35))) / 2;

		JButton valider = new JButton("valider");
		JButton annuler = new JButton("annuler");

		final FenetreJeu fj = fenetreJeu;
		final InterfaceDeSaisie ids = this;
		valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				ids.recupererJoueurs(fj);

			}
		});

		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				fj.notifie("annuler");

			}
		});

		// création de la première ligne

		jcb1.addItem("Joueur reel");
		jcb1.addItem("Joueur virtuel offensif");
		jcb1.addItem("Joueur virtuel defensif");

		jcb1.setBounds(largeurPanel, hauteurPanel, 200, 35);
		largeurPanel += 230;
		nomj1.setBounds(largeurPanel, hauteurPanel, 200, 35);

		this.add(jcb1);
		this.add(nomj1);

		largeurPanel = (largeur - (2 * 200 + 30)) / 2;
		hauteurPanel += 70;

		// création de la deuxième ligne

		jcb2.addItem("Joueur reel");
		jcb2.addItem("Joueur virtuel offensif");
		jcb2.addItem("Joueur virtuel defensif");

		jcb2.setBounds(largeurPanel, hauteurPanel, 200, 35);
		largeurPanel += 230;
		nomj2.setBounds(largeurPanel, hauteurPanel, 200, 35);

		this.add(jcb2);
		this.add(nomj2);

		largeurPanel = (largeur - (2 * 200 + 30)) / 2;
		hauteurPanel += 70;

		// création de la troisième ligne

		jcb3.addItem("Pas de joueur");
		jcb3.addItem("Joueur reel");
		jcb3.addItem("Joueur virtuel offensif");
		jcb3.addItem("Joueur virtuel defensif");

		jcb3.setBounds(largeurPanel, hauteurPanel, 200, 35);
		largeurPanel += 230;
		nomj3.setBounds(largeurPanel, hauteurPanel, 200, 35);

		this.add(jcb3);
		this.add(nomj3);

		largeurPanel = (largeur - (2 * 200 + 30)) / 2;
		hauteurPanel += 70;

		// création de la quatrième ligne

		jcb4.addItem("Pas de joueur");
		jcb4.addItem("Joueur reel");
		jcb4.addItem("Joueur virtuel offensif");
		jcb4.addItem("Joueur virtuel defensif");

		jcb4.setBounds(largeurPanel, hauteurPanel, 200, 35);
		largeurPanel += 230;
		nomj4.setBounds(largeurPanel, hauteurPanel, 200, 35);

		this.add(jcb4);
		this.add(nomj4);

		largeurPanel = (largeur - (2 * 200 + 30)) / 2;
		hauteurPanel += 70;

		valider.setBounds(largeurPanel, hauteurPanel, 200, 35);
		annuler.setBounds((largeur - (2 * 200 + 30)) / 2 + 230, hauteurPanel,
				200, 35);
		this.add(valider);
		this.add(annuler);
		// rend visible le fond
		this.setOpaque(false);

	}

	/**
	 * Fonctions qui permet de créer une liste de joueurs à partir de la saisie 
	 * et de la notifier au controleur via la fenetre de jeu
	 * @param fj
	 */
	public void recupererJoueurs(FenetreJeu fj) {

		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

		joueurs.add(new JoueurReel(nomj1.getText()));

		joueurs.add(new JoueurReel(nomj2.getText()));

		if (!this.jcb3.getSelectedItem().equals("Pas de joueur")) {
			joueurs.add(new JoueurReel(nomj3.getText()));
		}
		if (!this.jcb4.getSelectedItem().equals("Pas de joueur")) {
			joueurs.add(new JoueurReel(nomj4.getText()));
		}
		fj.notifie(joueurs);

	}
}
