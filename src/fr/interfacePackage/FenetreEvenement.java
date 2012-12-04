package fr.interfacePackage;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


abstract class FenetreEvenement extends JDialog {

	protected GridLayout gl = new GridLayout();
	protected JPanel elements = new JPanel();
	
	protected JTextField dateJour = new JTextField("21",2);
	protected JTextField dateMois = new JTextField("12",2);
	protected JTextField dateAnnee = new JTextField("2012",4);
	protected JTextField heureDebHeure = new JTextField("12",2);
	protected JTextField heureDebMinute = new JTextField("23",2);
	protected JTextField heureFinHeure = new JTextField("12",2);
	protected JTextField heureFinMinute = new JTextField("30",2);
	protected JTextField titreEv = new JTextField("Genie Logiciel",25);
	protected JTextField lieuEv = new JTextField("Salle U5",25);
	protected JComboBox categorieEv = new JComboBox();
	protected JTextField descriptionEv = new JTextField("Super cours",20);

	
	// Creation du bouton de validation
	protected JButton validation = new JButton("OK");
	
	public FenetreEvenement(JFrame parent, String title, boolean modal){
		super(parent, title, modal);
		//On specifie une taille
		this.setSize(380, 480);
		//La position
		this.setLocationRelativeTo(null);
		//La boite ne devra pas etre redimensionnable
		this.setResizable(false);
                
		
		BorderLayout bd = new BorderLayout();
		this.setLayout(bd);
		
		// Division du panneau en 7 lignes
		gl.setColumns(1);
		gl.setRows(7);
		elements.setLayout(gl);
		
		
		// Parametre du champ date
		JLabel dateLabel = new JLabel("Date:");
		JLabel dateSep1 = new JLabel("/");
		JLabel dateSep2 = new JLabel("/");
		JPanel dateP = new JPanel();
		dateP.add(dateLabel);
		dateP.add("Date Jour", dateJour);
		dateP.add(dateSep1);
		dateP.add("Date Mois", dateMois);
		dateP.add(dateSep2);
		dateP.add("Date Annee", dateAnnee);
		
		
		// Parametre du champ Heure dÃ©but
		JLabel heureDebLabel = new JLabel("Heure debut:");
		JLabel heureDebSep = new JLabel(":");
		JPanel heureDebP = new JPanel();
		heureDebP.add(heureDebLabel);
		heureDebP.add("Heure heure", heureDebHeure);
		heureDebP.add(heureDebSep);
		heureDebP.add("Heure minute", heureDebMinute);
		
		// Parametre du champ Heure fin
		JLabel heureFinLabel = new JLabel("Heure fin:");
		JLabel heureFinSep = new JLabel(":");
		JPanel heureFinP = new JPanel();
		heureFinP.add(heureFinLabel);
		heureFinP.add("Heure heure", heureFinHeure);
		heureFinP.add(heureFinSep);
		heureFinP.add("Heure minute", heureFinMinute);
		
		// Parametre du champ Titre
		JLabel titreLabel = new JLabel("Titre:");
		JPanel titreP = new JPanel();
		titreP.add(titreLabel);
		titreP.add("Titre", titreEv);
		
		// Parametre du champ Lieu
		JLabel lieuLabel = new JLabel("Lieu:");
		JPanel lieuP = new JPanel();
		lieuP.add(lieuLabel);
		lieuP.add("Lieu", lieuEv);
		
		// Parametre du champ Categorie
		JLabel categorieLabel = new JLabel("Categorie:");
		categorieEv.addItem("CM");
		categorieEv.addItem("TD");
		categorieEv.addItem("TP");
		categorieEv.addItem("Controle Continu");
		categorieEv.addItem("Controle Continu de TP");
		categorieEv.addItem("Rendu de projet");
		categorieEv.addItem("Examen");
		JPanel categorieP = new JPanel();
		categorieP.add(categorieLabel);
		categorieP.add("Categorie", categorieEv);
		
		// Parametre du champ Description
		JLabel descriptionLabel = new JLabel("Description:");
		JPanel descriptionP = new JPanel();
		descriptionP.add(descriptionLabel);
		descriptionP.add("Description", descriptionEv);
				
		
		// Ajout des composants a la grille
		elements.add(dateP);
		elements.add(heureDebP);
		elements.add(heureFinP);
		elements.add(titreP);
		elements.add(lieuP);
		elements.add(categorieP);
		elements.add(descriptionP);
		
		this.getContentPane().add(elements,bd.CENTER);
		this.getContentPane().add(validation,bd.SOUTH);
	}
}
