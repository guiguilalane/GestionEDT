package fr.interfacePackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * 
 * @author Florian
 */
public class ConnectionWindow extends JFrame {
	
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
	
	public ConnectionWindow(){
		
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
		
		this.setContentPane(top);
		this.setVisible(true);
				
	}

	public static void main(String[] args){
		ConnectionWindow fe = new ConnectionWindow();
		fe.setVisible(true);
	}
}
