/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.fileTools;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import fr.Model.CDate;
import fr.Model.CategoriesCourse;
import fr.Model.Event;
import fr.Model.ICalEvent;

/**
 *
 * @author Florian
 */
public class ICSParsor{

	public ICSParsor(){
	}

	public static void loadingEvent(String name, HashMap<String,ICalEvent> iCalevents) throws FileNotFoundException, IOException, ParserException{
		int test = 0;
		String dtstart = "";
		String dtend = "";
		String uid = "";
		String module = "";
		CategoriesCourse typeCours = CategoriesCourse.TD;
		String location = "";
		String description = "";

		String fileName = name + ".ics";

		/* Parsing du fichier ICS */
		FileInputStream fin = new FileInputStream(fileName);
		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(fin); 

		/* Itération pour un Calendrier ics 
		 * Une itération par Composant (VEVENT)
		 * Dans laquelle on parcourt l'ensemble des propriétés
		 * On donne ainsi leur nom et leur valeur
		 */

		for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
			Component component = (Component) i.next();
			System.out.println("Component [" + component.getName() + "]");

			dtstart = component.getProperty("DTSTART").getValue();
			dtend = component.getProperty("DTEND").getValue();
			uid = component.getProperty("UID").getValue();
			module = component.getProperty("SUMMARY").getValue();
			
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
			
			//            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
			//            Property property = (Property) j.next();
			//            System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
			//            
			//            
			//            if (property.getName().equals("DTSTART")){dtstart = property.getValue();}
			//            if (property.getName().equals("DTEND")){dtend = property.getValue();}
			//            if (property.getName().equals("UID")){uid = property.getValue();}
			//            if (property.getName().equals("SUMMARY"))
			//            {
			//                if (property.getValue().contains("CM")){typeCours = CategoriesCourse.CM ; module = property.getValue();}
			//                if (property.getValue().contains("TD")){typeCours =  CategoriesCourse.TD ; module = property.getValue();}
			//                if (property.getValue().contains("CC") || (property).getValue().contains("CONTROLE CONTINU")){typeCours =  CategoriesCourse.CC ; module = property.getValue();}
			//                if (property.getValue().contains("TP")){typeCours =  CategoriesCourse.TP ; module = property.getValue();}
			//                if (property.getValue().contains("CCTD")){typeCours =  CategoriesCourse.CCTP ; module = property.getValue();}
			//                if (property.getValue().contains("RENDUPROJET")){typeCours =  CategoriesCourse.RENDUPROJET ; module = property.getValue();}
			//                if (property.getValue().contains("EXAMEN")){typeCours =  CategoriesCourse.EXAMEN ; module = property.getValue();}
			//            }
			//            if (property.getName().equals("LOCATION")){location = property.getValue();}
			//            if (property.getName().equals("DESCRIPTION")){description = property.getValue();}        
			//
			//            }
			
			iCalevents.put(uid, new ICalEvent(uid, module, location, typeCours ,description, new CDate(dtstart), new CDate(dtend)));
		}
	}

	public static void main(String args[]) throws FileNotFoundException, IOException, ParserException{
		loadingEvent("myEDT", new HashMap<String, ICalEvent>());
	}
}

