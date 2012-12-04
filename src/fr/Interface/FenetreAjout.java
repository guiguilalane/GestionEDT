package fr.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import fr.Controler.GestionnaireEDT;
import fr.Model.CDate;
import fr.Model.CategoriesCourse;
import fr.Model.ICalEvent;
import fr.utilities.MyModel.MyModel;


public class FenetreAjout extends FenetreEvenement{

	private JTextField nbMois = new JTextField("0",2);
	private ButtonGroup rec = new ButtonGroup();
	private JRadioButton recY = new JRadioButton("Yes");
	private JRadioButton recN = new JRadioButton("No");
	private ButtonGroup periode = new ButtonGroup();
	private JRadioButton periodeW = new JRadioButton("Weekly");
	private JRadioButton periodeB = new JRadioButton("Bi-weekly");
	private JTable board;

	public FenetreAjout(JFrame parent, boolean modal, JTable boardP) {
		super(parent, "Ajouter votre evenement", modal);
		this.board=boardP;

		gl.setRows(gl.getRows()+2);

		// Parametre du champ Pï¿½riodicitï¿½
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

		// Parametre du champ Pï¿½riodicitï¿½
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
			JOptionPane lettre;
			CDate begin;
			CDate end;
			// Vï¿½rifier que les champs de date soient seulement des chiffres ---> Pour le moment renvoi une exception

			// TODO Crï¿½er un URI automatiquement --> Et rï¿½currence
			// TODO Ajouter Interface (board.model)
			// Ajout rŽcurrence

			try{
				// Creation des differentes informations d'un evenement
				String uid = "bloup";
				String module = titreEv.getText();
				String salle = lieuEv.getText();
				CategoriesCourse cat = CategoriesCourse.fromString(categorieEv.getSelectedItem().toString());
				String des = descriptionEv.getText();

				begin = new CDate(Integer.parseInt(dateAnnee.getText()),Integer.parseInt(dateMois.getText()),Integer.parseInt(dateJour.getText()), Integer.parseInt(heureDebHeure.getText()),Integer.parseInt(heureDebMinute.getText()));
				end = new CDate(Integer.parseInt(dateAnnee.getText()),Integer.parseInt(dateMois.getText()),Integer.parseInt(dateJour.getText()), Integer.parseInt(heureFinHeure.getText()),Integer.parseInt(heureFinMinute.getText()));

				boolean rec = recY.isSelected(); // Vrai si on a de la rï¿½currence
				boolean per = periodeW.isSelected(); // Vrai si la pï¿½riode est weekly
				int mois = Integer.parseInt(nbMois.getText());
				ICalEvent newEvent = mon_gestionnaire.createEvent(module, salle, cat, des, begin, end, rec, per);

				incorrectEvent = newEvent.incorrectEvenement();
				if (!incorrectEvent){

					// Ajout dans le modele
					mon_gestionnaire.addEvent(newEvent, mois);

					// Ajout dans l'interface
					MyModel modelTemp = (MyModel) board.getModel();
					String heure = newEvent.getdBegin().toHour()+"-"+newEvent.getdEnd().toHour();
					Object[] contenu = {newEvent.getdBegin().toDate(), heure, module, newEvent.getUID()}; 
					modelTemp.addRow(contenu);

					// On ferme la fenï¿½tre si tout est bon
					close();
				}
			}
			catch (java.lang.NumberFormatException e){
				lettre = new JOptionPane();
				lettre.showMessageDialog(null, "Merci de ne rentrer que des chiffres pour la date et les heures !!!", "Attention", JOptionPane.WARNING_MESSAGE);
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
