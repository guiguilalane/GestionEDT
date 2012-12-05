package fr.fileTools;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.GregorianCalendar;
import java.util.HashMap;

import fr.Model.ICalEvent;

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

public class ICSCreator {

	public ICSCreator(){
	}

	public static void createFile(String name, HashMap<String,ICalEvent> iCalevents) throws IOException, URISyntaxException, ParseException, ObjectStoreException, ConstraintViolationException, ValidationException {
		for (ICalEvent e : iCalevents.values()){
			Calendar cal = new Calendar();
			//add prodId to the event
			cal.getProperties().add(new ProdId("-//Open Source Applications Foundation//NONSGML Chandler Server//EN"));
			//add the property VERSION to the event
			PropertyFactory pf = PropertyFactoryImpl.getInstance();
			Property p = pf.createProperty("VERSION");
			p.setValue("2.0");
			cal.getProperties().add(p);
			//add the property
			cal.getProperties().add(CalScale.GREGORIAN);

			//createTimeZone
			TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
			TimeZone timezone = registry.getTimeZone("Europe/Paris");
			VTimeZone tz = timezone.getVTimeZone();

			//create start Date
			java.util.Calendar startDate = new GregorianCalendar();
			startDate.setTimeZone(timezone);
			startDate.set(java.util.Calendar.MONTH, e.getdBegin().getMonth()-1);
			startDate.set(java.util.Calendar.DAY_OF_MONTH, e.getdBegin().getDay());
			startDate.set(java.util.Calendar.YEAR, e.getdBegin().getYear());
			startDate.set(java.util.Calendar.HOUR_OF_DAY, e.getdBegin().getHour());
			startDate.set(java.util.Calendar.MINUTE, e.getdBegin().getMinute());
			startDate.set(java.util.Calendar.SECOND, 0);

			//create stop Date
			java.util.Calendar endDate = new GregorianCalendar(); 
			endDate.setTimeZone(timezone); 
			endDate.set(java.util.Calendar.MONTH, e.getdEnd().getMonth()-1); 
			endDate.set(java.util.Calendar.DAY_OF_MONTH, e.getdEnd().getDay()); 
			endDate.set(java.util.Calendar.YEAR, e.getdEnd().getYear());
			endDate.set(java.util.Calendar.HOUR_OF_DAY, e.getdEnd().getHour());
			endDate.set(java.util.Calendar.MINUTE, e.getdEnd().getMinute());
			startDate.set(java.util.Calendar.SECOND, 0);

			//create the event
			DateTime start = new DateTime(startDate.getTime());
			DateTime end = new DateTime(endDate.getTime());
			String summary = e.getCourseType().toString() + " - " + e.getModule();
			VEvent newEvent = new VEvent(start, end, summary);
			Location local = new Location(e.getClassRoom());
			newEvent.getProperties().add(local);

			Description remarques = new Description(e.getRemarques());
			newEvent.getProperties().add(remarques);

			newEvent.getProperties().add(tz.getTimeZoneId());

			newEvent.getProperties().add(new Uid(e.getUID()));

			cal.getComponents().add(newEvent);
			FileOutputStream fout = new FileOutputStream("mycalendar.ics");
			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(cal, fout);
		}
	}
}
