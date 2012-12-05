package fr.Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.fortuna.ical4j.connector.ObjectStoreException;

import fr.Controler.GestionnaireEDT;

/*
 * 
 * @author Florian
 */
public class CreateCalendarWindow extends JFrame {
	
	private JPanel container = new JPanel();
	
	private JTextField uRL = new JTextField(40);
	private JTextField description = new JTextField(40);
	private JTextField calendarName = new JTextField(40);
	private JTextField userName = new JTextField(40);
	private JPasswordField userPwd = new JPasswordField(40);
	
	private JLabel labelURL;
	private JLabel labelDescription;
	private JLabel labelCalendarName;
	private JLabel labelUserName;
	private JLabel labelPwd;
	
	private JButton validateButton;
	
	private JPanel myPanel;
	
	private MainWindow parent;
	
	public CreateCalendarWindow(JFrame parent){
		this.parent = (MainWindow) parent;
		
		this.setSize(650, 280);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Construire un calendrier");
		container.setBackground(Color.white);
	    container.setLayout(new BorderLayout());
	    JPanel top = new JPanel();
		
		labelURL = new JLabel("URL : ");
		labelDescription = new JLabel("Description de l'agenda : ");
		labelCalendarName = new JLabel("Nom de l'agenda : ");
		labelUserName = new JLabel("Nom utilisateur : ");
		labelPwd = new JLabel("Password : ");
		
		JPanel panelURL = new JPanel();
		panelURL.add(labelURL);
		panelURL.add(uRL);
		
		JPanel panelDescription = new JPanel();
		panelDescription.add(labelDescription);
		panelDescription.add(description);
		
		JPanel panelCalendarName = new JPanel();
		panelCalendarName.add(labelCalendarName);
		panelCalendarName.add(calendarName);
		
		JPanel panelUserName = new JPanel();
		panelUserName.add(labelUserName);
		panelUserName.add(userName);
		
		JPanel panelPwd = new JPanel();
		panelPwd.add(labelPwd);
		panelPwd.add(userPwd);
		
		validateButton = new JButton("Valider");
		
		top.add(panelURL);
		top.add(panelDescription);
		top.add(panelCalendarName);
		top.add(panelUserName);
		top.add(panelPwd);	
		top.add(validateButton);
		
		validateButton.addActionListener(new CreateCalendar());
		
		this.setContentPane(top);
		this.setVisible(true);
				
	}
	
	public void close(){
		parent.addButtonEnable(true);
		this.setVisible(false);
	}
	
	class CreateCalendar implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			GestionnaireEDT mon_gestionnaire = GestionnaireEDT.getInstance();
			try {
				mon_gestionnaire.createConnection(userName.getText(), userPwd.getText());
				mon_gestionnaire.createNewCalendar(calendarName.getText(), description.getText());
				close();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ObjectStoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
