package fr.interfacePackage;

import javax.swing.JFrame;


public class FenetreModification extends FenetreEvenement{

	public FenetreModification(JFrame parent, boolean modal/*, Evenement evenement*/) {
		super(parent, "Modifier votre �venement", modal);
		this.dateJour.setText("04")/*evenement.dateJour*/;
		this.dateMois.setText("04");
		this.dateAnnee.setText("04");
		this.heureDebHeure.setText("04");
		this.heureDebMinute.setText("04");
		this.heureFinHeure.setText("04");
		this.heureFinMinute.setText("04");
		this.titreEv.setText("04");
		this.lieuEv.setText("04");
		this.categorieEv.setSelectedItem("TP");
		this.descriptionEv.setText("04");
		
	}

}
