/**
 * 
 */
package fr.controlerPackage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import net.fortuna.ical4j.data.ParserException;
import fr.fileTools.ICSParsor;
import fr.modelPackage.Agenda;
import fr.modelPackage.AgendaFactory;
import fr.modelPackage.AgendaICalFactory;
import fr.modelPackage.Event;
import fr.modelPackage.ICalAgenda;
import fr.modelPackage.ICalEvent;



/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public class GestionnaireEDT {

	private AgendaFactory factoryICal;

	private Agenda agendaICal;

	private ArrayList<ICalEvent> iCalevents;

	private static int unique = 0;

	private static GestionnaireEDT gEDT;

	private GestionnaireEDT() {
		unique = 1;
		factoryICal = new AgendaICalFactory();
		iCalevents = new ArrayList<ICalEvent>();
		agendaICal = new ICalAgenda((ArrayList<ICalEvent>) iCalevents);
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

	public ArrayList<ICalEvent> getICalEvents(){
		return (ArrayList<ICalEvent>) iCalevents;
	}

	public void remplirList(String name) throws FileNotFoundException, IOException, ParserException{
		ICSParsor.loadingEvent(name, iCalevents);            
	}

	public static void main(String args[]) throws FileNotFoundException, ParserException, IOException{
		GestionnaireEDT myEDT = new GestionnaireEDT();
		myEDT.remplirList("myEDT");
		for(int i=0; i<myEDT.iCalevents.size();i++){
			System.out.println(myEDT.iCalevents.get(i).getUID());
			System.out.println(myEDT.iCalevents.get(i).getCourseType());
			System.out.println(myEDT.iCalevents.get(i).getdBegin().toString());
			System.out.println(myEDT.iCalevents.get(i).getdEnd().toString());
			System.out.println(myEDT.iCalevents.get(i).getModule());
			System.out.println(myEDT.iCalevents.get(i).getRemarques());
			System.out.println(myEDT.iCalevents.get(i).getClass());                
		}
	}
}
