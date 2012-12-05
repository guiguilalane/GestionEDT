package fr.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
import fr.utilities.MyModel;


public class AddEventWindow extends EventWindow{

	private JTextField nbMois = new JTextField("1",2);
	private ButtonGroup rec = new ButtonGroup();
	private JRadioButton recY = new JRadioButton("Yes");
	private JRadioButton recN = new JRadioButton("No");
	private ButtonGroup periode = new ButtonGroup();
	private JRadioButton periodeW = new JRadioButton("Weekly");
	private JRadioButton periodeB = new JRadioButton("Bi-weekly");
	private JTable board;
	private ImageIcon stop = new ImageIcon("resources/stop.png");

	public AddEventWindow(JFrame parent, boolean modal, JTable boardP) {
		super(parent, "Ajouter votre evenement", modal);
		this.board=boardP;

		gl.setRows(gl.getRows()+2);

		// Parametre du champ P�riodicit�
		JLabel recLabel = new JLabel("Cet evenement est-il recurrrent ?");
		JPanel recP = new JPanel();
		rec.add(recY);
		rec.add(recN);
		recN.setSelected(true);
		recP.add(recLabel);
		recP.add(recY);
		recP.add(recN);

		recY.addActionListener(new ActiveRecurrence());
		recN.addActionListener(new DesactiveRecurrence());

		// Parametre du champ P�riodicit�
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
		elements.getComponent(8).setVisible(false);
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
			JOptionPane lettre = new JOptionPane();
			CDate begin;
			CDate end;
			ArrayList<ICalEvent> liste;

			try{
				// Creation des differentes informations d'un evenement
				String module = titreEv.getText();
				String salle = lieuEv.getText();
				CategoriesCourse cat = CategoriesCourse.fromString(categorieEv.getSelectedItem().toString());
				String des = descriptionEv.getText();

				begin = new CDate(Integer.parseInt(dateAnnee.getText()),Integer.parseInt(dateMois.getText()),Integer.parseInt(dateJour.getText()), Integer.parseInt(heureDebHeure.getText()),Integer.parseInt(heureDebMinute.getText()));
				end = new CDate(Integer.parseInt(dateAnnee.getText()),Integer.parseInt(dateMois.getText()),Integer.parseInt(dateJour.getText()), Integer.parseInt(heureFinHeure.getText()),Integer.parseInt(heureFinMinute.getText()));

				boolean rec = recY.isSelected(); // Vrai si on a de la r�currence
				boolean per = periodeW.isSelected(); // Vrai si la p�riode est weekly
				int mois = Integer.parseInt(nbMois.getText());
				ICalEvent newEvent = mon_gestionnaire.createEvent(module, salle, cat, des, begin, end, rec, per);

				incorrectEvent = newEvent.incorrectEvenement();
				if (!incorrectEvent){

					// Ajout dans le modele
					liste = mon_gestionnaire.addEvent(newEvent, mois);

					// Ajout dans l'interface
					MyModel modelTemp = (MyModel) board.getModel();
					for(ICalEvent ev : liste){
						String heure = ev.getdBegin().toHour()+"-"+ev.getdEnd().toHour();
						Object[] contenu = {ev.getdBegin().toDate(), heure, module, ev.getUID()}; 
						modelTemp.addRow(contenu);
					}

					// On ferme la fenetre si tout est bon
					close();
				}
				else{
					lettre.showMessageDialog(null, "Veuillez vérifier la cohérence de la date et entre l'heure de début et fin. \n Veuillez aussi vérifier que le champ 'Titre' est rempli.", "Attention", JOptionPane.WARNING_MESSAGE,stop);
				}
			}
			catch (java.lang.NumberFormatException e){
				lettre.showMessageDialog(null, "Merci de ne rentrer que des chiffres pour la date et les heures !!!", "Attention", JOptionPane.WARNING_MESSAGE,stop);
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
