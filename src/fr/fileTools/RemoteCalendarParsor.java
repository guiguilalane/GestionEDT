/**
 * 
 */
package fr.fileTools;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import fr.Model.CDate;
import fr.Model.CategoriesCourse;
import fr.Model.ICalEvent;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;

//Ne prends pas en compte les evenements sans la propriété DTEND étalé sur plusieurs jours.
/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public class RemoteCalendarParsor {
	public static void loadingEvent(CalDavCalendarCollection toParse, HashMap<String,ICalEvent> iCalevents) throws ObjectStoreException{
		String dtstart = "";
		String dtend = "";
		String uid = "";
		String module = "";
		CategoriesCourse typeCours = CategoriesCourse.TD;
		String location = "";
		String description = "";
		
		for(int i = 0; i< toParse.getComponents().length; i++ ){
			Component component = toParse.getComponents()[i].getComponents().getComponent("VEVENT");
			dtstart = component.getProperty("DTSTART").getValue();
			try{
				dtend = component.getProperty("DTEND").getValue();
			} catch (NullPointerException e){
				String dtDuration = component.getProperty("DURATION").getValue().replaceAll("PT", "");
				CDate c = new CDate(dtstart);
				java.util.Calendar calendar = new GregorianCalendar(c.getYear(), c.getMonth()-1, c.getDay(), c.getHour(), c.getMinute());
				calendar.add(Calendar.HOUR_OF_DAY, new Integer(dtDuration.split("H")[0]).intValue());
				if(dtDuration.split("H").length > 1){
					calendar.add(Calendar.MINUTE, new Integer(dtDuration.split("H")[1].split("M")[0]).intValue());
				
				}
				CDate c2 = new CDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
				dtend = c2.toCalendarForm();
			}
			uid = component.getProperty("UID").getValue();
			if(component.getProperty("SUMMARY").getValue().split("- ").length == 2){
				module = component.getProperty("SUMMARY").getValue().split("- ")[1];
			} else {
				module = component.getProperty("SUMMARY").getValue();
			}
			
			
			if (component.getProperty("SUMMARY").getValue().contains("CM"))
			{typeCours = CategoriesCourse.CM;}
			else if (component.getProperty("SUMMARY").getValue().contains("TD"))
			{typeCours =  CategoriesCourse.TD;}
			else if (component.getProperty("SUMMARY").getValue().contains("CC") || component.getProperty("SUMMARY").getValue().contains("CONTROLE CONTINU"))
			{typeCours =  CategoriesCourse.CC;}
			else if (component.getProperty("SUMMARY").getValue().contains("TP"))
			{typeCours =  CategoriesCourse.TP;}
			else if (component.getProperty("SUMMARY").getValue().contains("CCTP"))
			{typeCours =  CategoriesCourse.CCTP;}
			else if (component.getProperty("SUMMARY").getValue().contains("RENDUPROJET"))
			{typeCours =  CategoriesCourse.RENDUPROJET;}
			else{typeCours =  CategoriesCourse.EXAMEN;}

			location = component.getProperty("LOCATION").getValue();
			description = component.getProperty("DESCRIPTION").getValue();
			dtstart = component.getProperty("DTSTART").getValue();
			
			iCalevents.put(uid, new ICalEvent(uid, module, location, typeCours ,description, new CDate(dtstart), new CDate(dtend)));
		}
	}
}
