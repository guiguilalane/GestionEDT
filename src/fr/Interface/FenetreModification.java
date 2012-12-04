package fr.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		validation.addActionListener(new ModifyEvent());
	}
	
	class ModifyEvent implements ActionListener{
		@Override 
		public void actionPerformed(ActionEvent arg0){
			// TODO V�rifier que les parametres sont corrects appeler une fonction
			// V�rifier que les champs de date soient seulement des chiffres
			// idem pour heure_deb et heure_fin
			// V�rifier que le titre et la salle sont non vide
			// TODO Modifier au mod�le (HashMap) et Interface (board.model)
			System.out.println("Modification OK");
			// TODO fermer la fen�tre si tout est OK
		}
	}

}
