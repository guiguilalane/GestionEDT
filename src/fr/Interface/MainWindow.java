/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.Interface;

/**
 *
 * @author Florian
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import net.fortuna.ical4j.data.ParserException;
import fr.Controler.GestionnaireEDT;
import fr.Model.ICalEvent;
import fr.utilities.MyModel.MyModel;

public class MainWindow extends JFrame {

	private GestionnaireEDT mon_gestionnaire = GestionnaireEDT.getInstance();
	/* Menu déroulant*/
	private JMenuBar my_menuBar = new JMenuBar();
	/* Onglets */           
	private JMenu tab1 = new JMenu("Configuration Calendrier");  
	private JMenu tab2 = new JMenu("Aide");
	/* Items pour onglets */
	private JMenuItem item1_1 = new JMenuItem("Créer un Calendrier");
	private JMenuItem item1_2 = new JMenuItem("Charger Calendrier");
	private JMenuItem item2_1 = new JMenuItem("Equipe de Developpement");
	/* Boutons */
	private JButton addButton = new JButton("Ajouter");
	private JButton modifyButton = new JButton("Modifier");
	private JButton deleteButton = new JButton("Supprimer");

	private JTextArea textInformation = new JTextArea();
	private JPanel my_panel1 = new JPanel();
	private JPanel my_panel2 = new JPanel();

	private JTable board;

	public MainWindow(){
		this.setTitle("Logiciel de gestion d'emploi du temps");
		this.setSize(900,550);
		//Position the window on the center of the screen
		this.setLocationRelativeTo(null);
		//Termine le processus lorsqu'on clique sur la croix rouge
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  

		this.tab1.add(item1_1);
		this.tab1.add(item1_2);
		this.tab2.add(item2_1);
		item1_2.addActionListener(new loadingFile());
		item2_1.addActionListener(new ItemListener());

		this.my_menuBar.add(tab1);
		this.my_menuBar.add(tab2);

		this.setJMenuBar(my_menuBar);

		//Action des boutons
		addButton.addActionListener(new AddEvent());
		modifyButton.addActionListener(new ModifyEvent());
		deleteButton.addActionListener(new DelRowListener());

		//Les donnes du tableau
		Object[][] donnees = {

		};

		//Les titres des colonnes
		String titres[] = {"Date", "Horaires", "Matières"};

		textInformation = new JTextArea("---------- Informations sur le Module ----------"/*,100,39*/);  
		textInformation.setEditable(false);

		my_panel1.add(textInformation);
		my_panel2.add(addButton);
		my_panel2.add(modifyButton);
		my_panel2.add(deleteButton);

		MyModel model = new MyModel(donnees, titres);
		board = new JTable(model);

		//Definition d'un nouveau MouseListener
		board.addMouseListener(new MyMouseListener());


		my_panel2.add(new JScrollPane(board));

		this.getContentPane().add(my_panel1, BorderLayout.EAST);
		this.getContentPane().add(my_panel2, BorderLayout.SOUTH);
		this.getContentPane().add(new JScrollPane(board), BorderLayout.WEST);
	}

	class MyMouseListener implements MouseListener{
		//Redefinition d'un MouseListener
		@Override
		public void mouseClicked(MouseEvent e) {
			int selectedRow = board.getSelectedRow();
			MyModel modelTemp = (MyModel)board.getModel();
			HashMap<String,ICalEvent> listTemp = mon_gestionnaire.getICalEvents();
			ICalEvent event = listTemp.get(modelTemp.getValueAt(selectedRow, 3));
			textInformation.setText("---------- Informations ----------" + "\n" +
					event.getCourseType().toString() + " - " + (String) event.getModule() + "\n" +
					"Salle : " + (String) event.getClassRoom() + "\n" +
					"Le " + event.getdBegin().toDate() + " de " + event.getdBegin().toHour() + " à " + event.getdEnd().toHour() + "\n" +
					"Description : " + event.getRemarques() + "\n");
		}

		@Override
		public void mousePressed(MouseEvent me) {
			//throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			//throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public void mouseEntered(MouseEvent me) {
			//throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public void mouseExited(MouseEvent me) {
			//throw new UnsupportedOperationException("Not supported yet.");
		}
	};

	class loadingFile implements ActionListener{
		@Override 
		public void actionPerformed(ActionEvent arg0){
			try {						
				mon_gestionnaire.remplirList("myEDT");
				MyModel modelTemp = (MyModel)board.getModel();
				board.removeAll();
				for (ICalEvent e : mon_gestionnaire.getICalEvents().values()){
					String date = e.getdBegin().toDate();
					String hour = e.getdBegin().toHour() + "-" + e.getdEnd().toHour();
					String module = e.getModule();
					String uid = e.getUID();
					Object[] contenu = {date, hour, module,uid}; 
					modelTemp.addRow(contenu); 	
				}               
			} catch (FileNotFoundException ex) {
				Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ParserException ex) {
				Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
			}          
		}
	}

	class connectToCalendar implements ActionListener{
		@Override 
		public void actionPerformed(ActionEvent arg0){
			ConnectionWindow connecWind = new ConnectionWindow();
			connecWind.setVisible(true);
		}
	}


	class AddEvent implements ActionListener{
		//Redefintion de la methode actionPerformed()
		@Override
		public void actionPerformed(ActionEvent arg0) {   
			FenetreEvenement ev = new FenetreAjout(null, true, board);
			ev.setVisible(true);
		}
	}
	class ModifyEvent implements ActionListener{
		//Redefintion de la methode actionPerformed()
		@Override
		public void actionPerformed(ActionEvent arg0) {
			MyModel modelTemp = (MyModel)board.getModel();
			HashMap<String,ICalEvent> listTemp = mon_gestionnaire.getICalEvents();
			ICalEvent event = listTemp.get(modelTemp.getValueAt(board.getSelectedRow(), 3));
			FenetreEvenement ev = new FenetreModification(null, true, board, event);
			ev.setVisible(true);
		}
	}


	class DelRowListener implements ActionListener{
		//Redefintion de la methode actionPerformed()
		@Override
		public void actionPerformed(ActionEvent arg0){   
			int selectedRow = board.getSelectedRow(); 
			MyModel modelTemp = (MyModel)board.getModel();
			HashMap<String,ICalEvent> listTemp = mon_gestionnaire.getICalEvents();
			char rec = listTemp.get(modelTemp.getValueAt(selectedRow, 3)).getUID().charAt(0);
			ICalEvent event = listTemp.get(modelTemp.getValueAt(selectedRow, 3));
			if (rec == '\0'){
				listTemp.remove(modelTemp.getValueAt(selectedRow, 3));
			}
			// Si l'evenement est recurrent 
			else if(rec == 'R'){
				for (ICalEvent e : mon_gestionnaire.getICalEvents().values()){
					try {
						e.getdBegin().getDayOfWeek();
						event.getdBegin().getDayOfWeek();
						e.getdEnd().getDayOfWeek();
						event.getdEnd().getDayOfWeek();
						if (e.getdBegin().getDayOfWeek()==event.getdBegin().getDayOfWeek() &&
								e.getdBegin().getHour()==event.getdBegin().getHour() && 
								e.getdBegin().getMinute()==event.getdBegin().getMinute() &&
								e.getdEnd().getDayOfWeek()==event.getdEnd().getDayOfWeek() &&
								e.getdEnd().getHour()==event.getdEnd().getHour() &&
								e.getdEnd().getMinute()==event.getdEnd().getMinute() && 
								e.getModule().equals(event.getModule())){
							listTemp.remove(e.getUID());
						}
					}
					catch (Exception execption){
					}

				}
			}
			modelTemp.removeRow(selectedRow);
		}
	}

	class ItemListener implements ActionListener{
		//Redefinition de la methode actionPerformed()
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFrame fenetreDev = new JFrame("Equipe de developpement");
			fenetreDev.setSize(300,100);
			fenetreDev.setLocationRelativeTo(null);
			JTextArea textArea = new JTextArea("Ce logiciel à été développé par : Florian FAGNIEZ,\nGuillaume Coutable et Noémie RULLIER \nM1 ALMA - TPA");
			JPanel panelDev = new JPanel();
			fenetreDev.getContentPane().add(textArea, BorderLayout.CENTER); 
			fenetreDev.setVisible(true);
		}
	}

	public static void main(String[] args){
		MainWindow fen = new MainWindow();
		fen.setVisible(true);
	}

}

