/**
 * 
 */
package controlerPackage;

import java.util.ArrayList;
import java.util.Collection;

import modelPackage.Agenda;
import modelPackage.AgendaFactory;
import modelPackage.AgendaICalFactory;
import modelPackage.Event;
import modelPackage.ICalAgenda;
import modelPackage.ICalEvent;


/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public class GestionnaireEDT {
	
	private AgendaFactory factoryICal;
	
	private Agenda agendaICal;
	
	private ArrayList<? extends Event> iCalevents;
	
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
	public static GestionnaireEDT getInstance(){
		if(unique == 0){
			gEDT = new GestionnaireEDT();
		}
		return gEDT;
	}
}
