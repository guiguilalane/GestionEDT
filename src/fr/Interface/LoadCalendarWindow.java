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

import org.apache.jackrabbit.webdav.DavException;

import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import fr.Controler.GestionnaireEDT;
import fr.Model.ICalEvent;
import fr.utilities.MyModel;

/*
 * 
 * @author Florian
 */
public class LoadCalendarWindow extends JFrame {

	private JPanel container = new JPanel();

	private JTextField uRL = new JTextField(40);
	private JTextField userName = new JTextField(30);
	private JPasswordField userPwd = new JPasswordField(40);

	private JLabel labelURL;
	//	private JLabel labelDescription;
	//	private JLabel labelCalendarName;
	private JLabel labelUserName;
	private JLabel labelPwd;

	private JButton validateButton;

	private JPanel myPanel;

	private MainWindow parent;

	private JTable board;

	private static int connectionessai = 0;

	public LoadCalendarWindow(JFrame parent, JTable board){
		this.parent = (MainWindow) parent;
		this.board = board;

		this.setSize(600, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Charger un Calendrier un calendrier");
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		JPanel top = new JPanel();

		labelURL = new JLabel("URL : ");
		//		labelDescription = new JLabel("Description de l'agenda : ");
		//		labelCalendarName = new JLabel("Nom de l'agenda : ");
		labelUserName = new JLabel("Nom utilisateur : ");
		labelPwd = new JLabel("Password : ");

		JPanel panelURL = new JPanel();
		panelURL.add(labelURL);
		panelURL.add(uRL);

		JPanel panelUserName = new JPanel();
		panelUserName.add(labelUserName);
		panelUserName.add(userName);

		JPanel panelPwd = new JPanel();
		panelPwd.add(labelPwd);
		panelPwd.add(userPwd);

		validateButton = new JButton("Valider");

		top.add(panelURL);
		top.add(panelUserName);
		top.add(panelPwd);	
		top.add(validateButton);
		validateButton.addActionListener(new LoadCalendar());

		this.setContentPane(top);
		this.setVisible(true);

	}

	public void close(){
		parent.addButtonEnable(true);
		this.setVisible(false);
	}

	class LoadCalendar implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			GestionnaireEDT mon_gestionnaire = GestionnaireEDT.getInstance();
			try {
				mon_gestionnaire.createConnection(userName.getText(), userPwd.getText());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ObjectStoreException e) {
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
			MyModel modelTemp = (MyModel)board.getModel();
			modelTemp.removeAll();
			board.removeAll();
			mon_gestionnaire.clearEventModel();
			try {
				mon_gestionnaire.remplirCalendar(uRL.getText());

				for (ICalEvent e : mon_gestionnaire.getICalEvents().values()){
					String date = e.getdBegin().toDate();
					String hour = e.getdBegin().toHour() + "-" + e.getdEnd().toHour();
					String module = e.getModule();
					String uid = e.getUID();
					Object[] contenu = {date, hour, module,uid}; 
					modelTemp.addRow(contenu);
				}
				close();
			} catch (ObjectNotFoundException e1) {
				if(connectionessai<=3){
					System.out.println("le calendrier : " + e1.getMessage() + " n'a pas été trouvé!! \nVeuillez vérifier l'adresse.");
					connectionessai++;
				} else {
					connectionessai = 0;
					setVisible(false);
				}

			}



		}

	}
}