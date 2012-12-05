/**
 * 
 */
package fr.CalendarConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.connector.FailedOperationException;
import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.connector.dav.CalDavCalendarStore;
import net.fortuna.ical4j.connector.dav.PathResolver;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ConstraintViolationException;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.PropertyFactoryImpl;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.util.SimpleHostInfo;
import net.fortuna.ical4j.util.UidGenerator;
import fr.Model.Event;

/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public class ChandlerConnection extends Connection {
	
	protected static String usr;
	protected static String CalUrl;
	protected static CalDavCalendarStore store;

	/**
	 * Allocate and initalize a new ChandlerConnection
	 * @param url the remote calendar url
	 * @param usr the user identifier
	 * @param mdp the user password
	 * @throws MalformedURLException when the url is MalFormed
	 * @throws ObjectStoreException when usr is not connected
	 */
	public ChandlerConnection(String url, String usr, String mdp) throws MalformedURLException, ObjectStoreException {
		this.url = new URL("https://hub.chandlerproject.org/pim");
		store = new CalDavCalendarStore("-//Open Source Applications Foundation//NONSGML Chandler Server//EN", this.url, PathResolver.CHANDLER);
//		store.connect(usr, mdp.toCharArray());
		//TODO:a enlever quand integration de la fenetre de connexion faite
		store.connect("guiguilalane", "feuenlat1".toCharArray());
		this.usr = "guiguilalane"; //TODO
		CalUrl = url;
	}
	
	/* (non-Javadoc)
	 * @see fr.CalendarConnection.Connection#createCalendar(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public CalDavCalendarCollection createCalendar() throws ObjectStoreException, ObjectNotFoundException {
		calendar = store.getCollection(CalUrl);
		return calendar;
	}

	public static void createNewCalendar(String CalName,
			String calDescription, String[] calComponents, Calendar timeZone) throws ObjectStoreException {
		CalUrl = "https://hub.chandlerproject.org/dav/"+usr+"/"+CalName;
		store.addCollection(CalUrl, CalName, calDescription, calComponents, timeZone);
	}

	/* (non-Javadoc)
	 * @see fr.CalendarConnection.Connection#createEvent(java.lang.String, fr.Model.CDate, fr.Model.CDate, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Calendar createEvent(String timeZone, Event ev) throws IOException, URISyntaxException, ParseException, ObjectStoreException, ConstraintViolationException {
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
		TimeZone timezone = registry.getTimeZone(timeZone);
		VTimeZone tz = timezone.getVTimeZone();
		
		//create start Date
		java.util.Calendar startDate = new GregorianCalendar();
		startDate.setTimeZone(timezone);
		startDate.set(java.util.Calendar.MONTH, ev.getdBegin().getMonth()-1);
		startDate.set(java.util.Calendar.DAY_OF_MONTH, ev.getdBegin().getDay());
		startDate.set(java.util.Calendar.YEAR, ev.getdBegin().getYear());
		startDate.set(java.util.Calendar.HOUR_OF_DAY, ev.getdBegin().getHour());
		startDate.set(java.util.Calendar.MINUTE, ev.getdBegin().getMinute());
		startDate.set(java.util.Calendar.SECOND, 0);
		
		//create stop Date
		java.util.Calendar endDate = new GregorianCalendar(); 
		endDate.setTimeZone(timezone); 
		endDate.set(java.util.Calendar.MONTH, ev.getdEnd().getMonth()-1); 
		endDate.set(java.util.Calendar.DAY_OF_MONTH, ev.getdEnd().getDay()); 
		endDate.set(java.util.Calendar.YEAR, ev.getdEnd().getYear());
		endDate.set(java.util.Calendar.HOUR_OF_DAY, ev.getdEnd().getHour());
		endDate.set(java.util.Calendar.MINUTE, ev.getdEnd().getMinute());
		startDate.set(java.util.Calendar.SECOND, 0);
		
		//create the event
		DateTime start = new DateTime(startDate.getTime());
		DateTime end = new DateTime(endDate.getTime());
		String summary = ev.getCourseType().toString() + " - " + ev.getModule();
		VEvent newEvent = new VEvent(start, end, summary);
		Location local = new Location(ev.getClassRoom());
		newEvent.getProperties().add(local);
		
		Description remarques = new Description(ev.getRemarques());
		newEvent.getProperties().add(remarques);
		
		newEvent.getProperties().add(tz.getTimeZoneId());
		
		newEvent.getProperties().add(new Uid(ev.getUID()));
		
		cal.getComponents().add(newEvent);
		
		return cal;
	}
	
	public String createUid(){
		//generate uid
		UidGenerator ug = new UidGenerator(new SimpleHostInfo("hub.chandlerproject.org"), "1");
		Uid uid = ug.generateUid();
		return uid.getValue();
	}

	@Override
	public void modifyEvent(String uid, String propertyName,
			String propertyNewValue) throws FailedOperationException, ObjectStoreException, IOException, URISyntaxException, ParseException, ConstraintViolationException {
		//récupration de l'event à modifier
		Calendar c = calendar.getCalendar(uid);
		//suppression de l'event du calendrier
		calendar.removeCalendar(uid);
		//récupération du composant VEVENT
		Component comp = (Component) c.getComponents().getComponent("VEVENT");
		//récupration de la propriété propertyName
		Property p = comp.getProperty(propertyName);
		//modification de la propriété propertyName
		p.setValue(propertyNewValue);
		calendar.addCalendar(c);
	}

	@Override
	public void removeEvent(String uid) throws FailedOperationException, ObjectStoreException {
		calendar.removeCalendar(uid);
	}

	@Override
	public void addEvent(Calendar c) throws ObjectStoreException, ConstraintViolationException {
		calendar.addCalendar(c);
	}

}
