package fr.Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.fortuna.ical4j.connector.ObjectStoreException;
import fr.Controler.GestionnaireEDT;
import fr.utilities.MyModel;

/*
 * 
 * @author Florian
 */
public class CreateCalendarWindow extends JFrame {
	
	private JPanel container = new JPanel();
	
//	private JTextField uRL = new JTextField(40);
	private JTextField description = new JTextField(30);
	private JTextField calendarName = new JTextField(40);
	private JTextField userName = new JTextField(40);
	private JPasswordField userPwd = new JPasswordField(40);
	
//	private JLabel labelURL;
	private JLabel labelDescription;
	private JLabel labelCalendarName;
	private JLabel labelUserName;
	private JLabel labelPwd;
	
	private JButton validateButton;
	
	private JPanel myPanel;
	
	private MainWindow parent;
	
	private JTable board;
	
	private static int connectionessai = 0;

	
	public CreateCalendarWindow(JFrame parent, JTable board){
		this.parent = (MainWindow) parent;
		this.board = board;
		
		this.setSize(650, 240);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Construire un calendrier");
		container.setBackground(Color.white);
	    container.setLayout(new BorderLayout());
	    JPanel top = new JPanel();
		
		labelDescription = new JLabel("Description de l'agenda : ");
		labelCalendarName = new JLabel("Nom de l'agenda : ");
		labelUserName = new JLabel("Nom utilisateur : ");
		labelPwd = new JLabel("Password : ");
		
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
				MyModel modelTemp = (MyModel)board.getModel();
				modelTemp.removeAll();
				board.removeAll();
				mon_gestionnaire.clearEventModel();
				close();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ObjectStoreException e1) {
				if(connectionessai<=3){
					System.out.println("Vous vous êtes trompé d'identifiant!! \nVeuillez les saisir de nouveau. " + connectionessai);
					userName.setText("");
					userPwd.setText("");
					connectionessai++;
				} else {
					connectionessai = 0;
					setVisible(false);
				}
			} 
		}
		
	}
}
