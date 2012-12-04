package fr.modelPackage;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Florian FAGNIEZ - No√©mie RULLIER - Guillaume COUTABLE
 *
 */
public class ICalEvent extends Event {

	
	/**
	 * Allocates an <code>ICalEvent</code> and initialize it with the given parameters.
	 * @param uID the uID of the event
	 * @param module the module of the event
	 * @param classRoom the classRoom of the event
	 * @param courseType the course type of the event
	 * @param remarques Some remarques on the event
	 * @param dBegin the begin date of the event
	 * @param dEnd the end date of the event
	 * @see Event#Event(String, String, String, CategoriesCourse, String, CDate, CDate)
	 */
	public ICalEvent(String uID, String module, String classRoom,
			CategoriesCourse courseType, String remarques, CDate dBegin, CDate dEnd) {
		super(uID, module, classRoom, courseType, remarques, dBegin, dEnd);
	}
	
	public boolean incorrectEvenement(){
		int coherenceDebFin;
		int coherenceDebActuelle;
		Date current = new Date(new GregorianCalendar().getTime().getTime());
		// Creation du calendrier actuelle 
		Calendar cActuelle = Calendar.getInstance();
		cActuelle.setLenient(false);
		cActuelle.setTime(current);
		// Creation des dates et heures dans un calendrier
		Calendar cDeb = Calendar.getInstance();
		cDeb.setLenient(false);
		cDeb.set(this.dBegin.getYear(),this.dBegin.getMonth()-1,this.dBegin.getDay(),this.dBegin.getHour(),this.dBegin.getMinute());
		Calendar cFin = Calendar.getInstance();
		cFin.setLenient(false);
		cFin.set(this.dEnd.getYear(),this.dEnd.getMonth()-1,this.dEnd.getDay(),this.dEnd.getHour(),this.dEnd.getMinute());
		try{
			// On regarde si les dates et heures fournies sont correctes
		  cDeb.getTime();
		  cFin.getTime();
		  // On vérifie que la date de début ainsi que l'heure de début est inférieur à la date de fin ainsi que l'heure de fin
		  coherenceDebFin = cDeb.compareTo(cFin);
		  // On vérifie si la date de début et l'heure de début est supérieur ou égale à l'heure actuelle
		  coherenceDebActuelle = cDeb.compareTo(cActuelle);
		  return (this.module.equals("") || this.classRoom.equals("") || coherenceDebFin!=(-1) || coherenceDebActuelle==(-1));
		}
		catch(IllegalArgumentException iAE){
		  return true;
		}
	}
	
	
}
