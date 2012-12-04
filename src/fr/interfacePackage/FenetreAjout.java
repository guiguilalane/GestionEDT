package fr.interfacePackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.fortuna.ical4j.util.SimpleHostInfo;
import net.fortuna.ical4j.util.UidGenerator;

import fr.controlerPackage.GestionnaireEDT;
import fr.interfacePackage.MainWindow.AddEvent;
import fr.modelPackage.CDate;
import fr.modelPackage.CategoriesCourse;
import fr.modelPackage.ICalEvent;
import fr.utilities.MyModel.MyModel;


public class FenetreAjout extends FenetreEvenement{

	private JTextField nbMois = new JTextField("0",2);
	private ButtonGroup rec = new ButtonGroup();
	private JRadioButton recY = new JRadioButton("Yes");
	private JRadioButton recN = new JRadioButton("No");
	private ButtonGroup periode = new ButtonGroup();
	private JRadioButton periodeW = new JRadioButton("Weekly");
	private JRadioButton periodeB = new JRadioButton("Bi-weekly");

	public FenetreAjout(JFrame parent, boolean modal) {
		super(parent, "Ajouter votre evenement", modal);

		gl.setRows(gl.getRows()+2);

		// Parametre du champ Périodicité
		JLabel recLabel = new JLabel("Cet evenement est-il recurrrent ?");
		JPanel recP = new JPanel();
		rec.add(recY);
		rec.add(recN);
		recY.setSelected(true);
		recP.add(recLabel);
		recP.add(recY);
		recP.add(recN);

		recY.addActionListener(new ActiveRecurrence());
		recN.addActionListener(new DesactiveRecurrence());

		// Parametre du champ Périodicité
		JLabel perLabel = new JLabel("Recurrence sur:");
		JLabel perMois = new JLabel("mois."); 
		JPanel perP = new JPanel();
		periode.add(periodeW);
		periode.add(periodeB);
		periodeW.setSelected(true);
		perP.add(perLabel);
		perP.add("Rec", nbMois);
		perP.add(perMois);
		perP.add(periodeW);
		perP.add(periodeB);

		elements.add(recP);
		elements.add(perP);
		validation.addActionListener(new AddEvent());
	}

	public void close(){
		this.setVisible(false);
	}
	
	class AddEvent implements ActionListener{
		private GestionnaireEDT mon_gestionnaire = GestionnaireEDT.getInstance();
		@Override 
		public void actionPerformed(ActionEvent arg0){
			boolean incorrectEvent = true;
			// Vérifier que les champs de date soient seulement des chiffres ---> Pour le moment renvoi une exception

			// TODO Créer un URI automatiquement --> Et récurrence
			// TODO Ajouter Interface (board.model)

			// Creation des differentes informations d'un evenement
			String uid = "bloup";
			String module = titreEv.getText();
			String salle = lieuEv.getText();
			CategoriesCourse cat = CategoriesCourse.fromString(categorieEv.getSelectedItem().toString());
			String des = descriptionEv.getText();
			CDate begin = new CDate(Integer.parseInt(dateAnnee.getText()),Integer.parseInt(dateMois.getText()),Integer.parseInt(dateJour.getText()), Integer.parseInt(heureDebHeure.getText()),Integer.parseInt(heureDebMinute.getText()));
			CDate end = new CDate(Integer.parseInt(dateAnnee.getText()),Integer.parseInt(dateMois.getText()),Integer.parseInt(dateJour.getText()), Integer.parseInt(heureFinHeure.getText()),Integer.parseInt(heureFinMinute.getText()));
			boolean rec = recY.isSelected(); // Vrai si on a de la récurrence
			boolean per = periodeW.isSelected(); // Vrai si la période est weekly
			int mois = Integer.parseInt(nbMois.getText());
			ICalEvent newEvent = mon_gestionnaire.createEvent(module, salle, cat, des, begin, end, rec, per);

			incorrectEvent = newEvent.incorrectEvenement();
			if (!incorrectEvent){

				// Ajout dans le modele
				mon_gestionnaire.addEvent(newEvent, mois);

				// Ajout dans l'interface
				//MyModel modelTemp = (MyModel) board.getModel();

				// On ferme la fenêtre si tout est bon
				close();
			}
		}
	}

	class ActiveRecurrence implements ActionListener{
		@Override 
		public void actionPerformed(ActionEvent arg0){
			elements.getComponent(8).setVisible(true);
		}
	}

	class DesactiveRecurrence implements ActionListener{
		@Override 
		public void actionPerformed(ActionEvent arg0){
			elements.getComponent(8).setVisible(false);
		}
	}
}
