package fr.Interface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ConstraintViolationException;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.PropertyFactoryImpl;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import fr.Controler.GestionnaireEDT;
import fr.Model.ICalEvent;
import fr.fileTools.ICSCreator;

public class CreateICSFileWindow extends JFrame {
	private JPanel container = new JPanel();
	
	private JTextField fileName = new JTextField(40);
	
	private JLabel labelFile;
	
	private JButton validateButton;

	public CreateICSFileWindow(){
		this.setSize(600, 100);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Charger un Calendrier un calendrier");
		container.setBackground(Color.white);
	    container.setLayout(new BorderLayout());
	    JPanel top = new JPanel();
		
		labelFile = new JLabel("Nom du fichier : ");
		
		JPanel panelURL = new JPanel();
		panelURL.add(labelFile);
		panelURL.add(fileName);
		
		validateButton = new JButton("Valider");
		
		top.add(panelURL);	
		top.add(validateButton);
		validateButton.addActionListener(new exportFile());
		
		this.setContentPane(top);
		this.setVisible(true);
				
	}
	
	class exportFile implements ActionListener{
		private GestionnaireEDT mon_gestionnaire = GestionnaireEDT.getInstance();

		@Override 
		public void actionPerformed(ActionEvent arg0) {
			try {
				ICSCreator.createFile("myCalendar.ics", mon_gestionnaire.getICalEvents());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjectStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConstraintViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]){
		CreateICSFileWindow icsW = new CreateICSFileWindow();
		icsW.setVisible(true);
	}
}
