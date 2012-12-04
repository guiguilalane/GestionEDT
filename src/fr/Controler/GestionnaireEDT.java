/**
 * 
 */
package fr.Controler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import fr.CalendarConnection.ChandlerFactoryConnection;
import fr.CalendarConnection.Connection;
import fr.CalendarConnection.FactoryConnection;
import fr.Model.Agenda;
import fr.Model.AgendaFactory;
import fr.Model.AgendaICalFactory;
import fr.Model.CDate;
import fr.Model.CategoriesCourse;
import fr.Model.ICalAgenda;
import fr.Model.ICalEvent;
import fr.fileTools.ICSParsor;



/**
 * @author Florian FAGNIEZ - NoÔøΩmie RULLIER - Guillaume COUTABLE
 *
 */
public class GestionnaireEDT {

	private AgendaFactory factoryICal;

	private Agenda agendaICal;

	private HashMap<String, ICalEvent> iCalevents;
	
	private FactoryConnection factoryConnection;
	
	private Connection connection;

	private static int unique = 0;

	private static GestionnaireEDT gEDT;

	private GestionnaireEDT() {
		unique = 1;
		factoryICal = new AgendaICalFactory();
		iCalevents = new HashMap<String,ICalEvent>();
		agendaICal = new ICalAgenda((HashMap<String,ICalEvent>) iCalevents);
		factoryConnection = new ChandlerFactoryConnection();
		//a enlever quand fenetre de connection integrée
		try {
			createConnection("plop", "plop");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ObjectStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * create a new connection with a remote calendar
	 * @param usr the user identifier
	 * @param mdp the user password
	 * @throws MalformedURLException when the url is MalFormed
	 * @throws ObjectStoreException when usr is not connected
	 */
	public void createConnection(String usr, String mdp) throws MalformedURLException, ObjectStoreException{
		connection = factoryConnection.createFactoryConnection(usr, mdp);
	}

	/**
	 * Allocates and return a new instance of <code>GestionnaireEDT</code>, if not exists.
	 * If an instance exists return this instance.
	 * @return an instance of <code>GestionnaireEDT</code>
	 */
	static public GestionnaireEDT getInstance(){
		if(unique == 0){
			gEDT = new GestionnaireEDT();
		}
		return gEDT;
	}

	public HashMap<String,ICalEvent> getICalEvents(){
		return iCalevents;
	}

	public void remplirList(String name) throws FileNotFoundException, IOException, ParserException{
		ICSParsor.loadingEvent(name, iCalevents);            
	}

	public ICalEvent createEvent(String module, String salle, CategoriesCourse cat, String description, CDate begin, CDate end, boolean rec, boolean per){
		//TODO faire un connection.createUid() pour récupérer un uid
		//puis pour ajouter l'event sur le calendrier distant, dans ta fenetreAjout faire "gestionnaireEDT".addEvent() //regarde les paramètre de addEvent
		String uid = "bloup";
		if(rec){
			if(per){
				uid ="RW"+uid;
			}
			else{
				uid ="RB"+uid;
			}
		}
		return new ICalEvent(uid, module, salle,cat,description,begin,end);
	}
	
	public void modifyEvent(ICalEvent event){

	}

	public ArrayList<ICalEvent> addEvent(ICalEvent event, int mois){
		ArrayList<ICalEvent> listRec = new ArrayList();
		int moisCourant;
		int semDebut;
		Calendar c = Calendar.getInstance();
		c.set(event.getdBegin().getYear(),event.getdBegin().getMonth()-1,event.getdBegin().getDay());
		semDebut = c.get(Calendar.WEEK_OF_YEAR);
		moisCourant = c.get(Calendar.MONTH);
		int nbMoisAjoute = 0;
		int nb = 1;
		this.getICalEvents().put(event.getUID(), event);
		listRec.add(event);
		if(event.getUID().charAt(0)=='R'){
			if(event.getUID().charAt(1)=='W'){
				nb=1;
			}
			else if (event.getUID().charAt(1)=='B'){
				nb=2;
			}
			while(nbMoisAjoute<(4*mois/nb)){
				c.add(Calendar.DAY_OF_MONTH,7*nb);
				nbMoisAjoute++;
				CDate beg = new CDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH),event.getdBegin().getHour(),event.getdBegin().getMinute());
				CDate fin = new CDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH),event.getdEnd().getHour(),event.getdEnd().getMinute());
				ICalEvent e = this.createEvent(event.getModule(), event.getClassRoom(), event.getCourseType(), event.getRemarques(), beg, fin, true, event.getUID().charAt(1)=='W');
				listRec.add(e);
				this.getICalEvents().put(e.getUID(), e);
			}
		}
	return listRec;
}

public static void main(String args[]) throws FileNotFoundException, ParserException, IOException{
	GestionnaireEDT myEDT = new GestionnaireEDT();
	myEDT.remplirList("myEDT");
	for (ICalEvent e : myEDT.iCalevents.values()){
		System.out.println(e.getUID());
	}
	//		for(int i=0; i<myEDT.iCalevents.size();i++){
	//			System.out.println(myEDT.iCalevents. .get(i).getUID());
	//			System.out.println(myEDT.iCalevents.get(i).getCourseType());
	//			System.out.println(myEDT.iCalevents.get(i).getdBegin().toString());
	//			System.out.println(myEDT.iCalevents.get(i).getdEnd().toString());
	//			System.out.println(myEDT.iCalevents.get(i).getModule());
	//			System.out.println(myEDT.iCalevents.get(i).getRemarques());
	//			System.out.println(myEDT.iCalevents.get(i).getClass());                
	//		}
}
}
