package fr.Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import fr.Controler.GestionnaireEDT;
import fr.Model.CDate;
import fr.Model.CategoriesCourse;
import fr.Model.ICalEvent;
import fr.utilities.MyModel;


public class ModifEventWindow extends EventWindow{

	private JTable board;
	private ICalEvent event;

	public ModifEventWindow(JFrame parent, boolean modal, JTable boardP, ICalEvent eventP) {
		super(parent, "Modifier votre évenement", modal);

		this.board=boardP;
		this.event=eventP;

		this.dateJour.setText(String.valueOf(event.getdBegin().getDay()));
		this.dateMois.setText(String.valueOf(event.getdBegin().getMonth()));
		this.dateAnnee.setText(String.valueOf(event.getdBegin().getYear()));
		this.heureDebHeure.setText(String.valueOf(event.getdBegin().getHour()));
		this.heureDebMinute.setText(String.valueOf(event.getdBegin().getMinute()));
		this.heureFinHeure.setText(String.valueOf(event.getdEnd().getHour()));
		this.heureFinMinute.setText(String.valueOf(event.getdEnd().getMinute()));
		this.titreEv.setText(event.getModule());
		this.lieuEv.setText(event.getClassRoom());
		this.categorieEv.setSelectedItem(event.getCourseType().toString());
		this.descriptionEv.setText(event.getRemarques());

		validation.addActionListener(new ModifyEvent());
	}

	public void close(){
		this.setVisible(false);
	}

	class ModifyEvent implements ActionListener{

		private GestionnaireEDT mon_gestionnaire = GestionnaireEDT.getInstance();

		@Override 
		public void actionPerformed(ActionEvent arg0){

			boolean incorrectEvent = true;
			JOptionPane lettre;
			CDate begin;
			CDate end;

			try{
				// Creation des differentes informations d'un evenement
				String module = titreEv.getText();
				String salle = lieuEv.getText();
				CategoriesCourse cat = CategoriesCourse.fromString(categorieEv.getSelectedItem().toString());
				String des = descriptionEv.getText();

				begin = new CDate(Integer.parseInt(dateAnnee.getText()),Integer.parseInt(dateMois.getText()),Integer.parseInt(dateJour.getText()), Integer.parseInt(heureDebHeure.getText()),Integer.parseInt(heureDebMinute.getText()));
				end = new CDate(Integer.parseInt(dateAnnee.getText()),Integer.parseInt(dateMois.getText()),Integer.parseInt(dateJour.getText()), Integer.parseInt(heureFinHeure.getText()),Integer.parseInt(heureFinMinute.getText()));

				ICalEvent ev = new ICalEvent(event.getUID(),module,salle,cat, des, begin, end);

				incorrectEvent = ev.incorrectEvenement();
				if (!incorrectEvent){

					
					// Modification dans le modele
					event.setModule(module);
					event.setClassRoom(salle);
					event.setCourseType(cat);
					event.setRemarques(des);
					event.setdBegin(begin);
					event.setdEnd(end);
					
					mon_gestionnaire.modifyEvent(event);

					// Modification dans l'interface
					MyModel modelTemp = (MyModel) board.getModel();
					String heure = ev.getdBegin().toHour()+"-"+ev.getdEnd().toHour();
					Object[] contenu = {ev.getdBegin().toDate(), heure, module, ev.getUID()};
					modelTemp.removeRow(board.getSelectedRow());
					modelTemp.addRow(contenu);
					
					// On ferme la fenetre si tout est bon
					close();
				}
			}
			catch (java.lang.NumberFormatException e){
				lettre = new JOptionPane();
				lettre.showMessageDialog(null, "Merci de ne rentrer que des chiffres pour la date et les heures !!!", "Attention", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
}
