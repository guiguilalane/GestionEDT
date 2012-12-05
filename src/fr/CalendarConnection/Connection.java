/**
 * 
 */
package fr.CalendarConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;

import fr.Model.Event;

import net.fortuna.ical4j.connector.FailedOperationException;
import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.connector.dav.CalDavCalendarStore;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ConstraintViolationException;

/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public abstract class Connection {
	
	protected URL url;
	protected String CalUrl;
	protected CalDavCalendarCollection calendar;
	protected String usr;
	protected CalDavCalendarStore store;
	
	/**
	 * 
	 * @param url the remote Calendar url
	 * @return a calendar
	 * @throws ObjectNotFoundException when the calendar is not found 
	 * @throws ObjectStoreException when the objectStore throws an exception
	 */
	//Pour iCal4j CaldDavCalendarCollection => calendrier.
	public abstract CalDavCalendarCollection createCalendar(String url) throws ObjectStoreException, ObjectNotFoundException;
	
//	/**
//	 * 
//	 * @param CalName the new calendar name
//	 * @param CalDescription the new calendar description
//	 * @param CalComponents the authorized components : VEVENT, VTODO, ...
//	 * @param timeZone the TimeZone
//	 * @return en new Calendar
//	 * @throws ObjectStoreException when the objectStore throws an exception
//	 */
//	public abstract CalDavCalendarCollection createNewCalendar(String CalName, String CalDescription, String[] CalComponents, Calendar timeZone) throws ObjectStoreException;
	
	//Pour iCal4j Calendar => event
	/**
	 * @param timeZone A string like "Europe/Paris"
	 * @param ev the event to add on remote calendar
	 * @return a new Event
	 * @throws ParseException when a parse error throws
	 * @throws URISyntaxException when a URI Syntax throws
	 * @throws IOException when a IO error throws
	 * @throws ConstraintViolationException when the objectStore throw an exception
	 * @throws ObjectStoreException when an adding calendar violation is set
	 */
	public abstract Calendar createEvent(String timeZone, Event ev) throws IOException, URISyntaxException, ParseException, ObjectStoreException, ConstraintViolationException;
	
	
	public abstract CalDavCalendarCollection createNewCalendar(String CalName,	String calDescription) throws ObjectStoreException;
	/**
	 * @return a uid
	 */
	public abstract String createUid();
	
	/**
	 * @param uid the event uid to modify
	 * @param propertyName the property to modify
	 * @param propertyNewValue the new value of the property
	 * @throws ObjectStoreException when the objectStore throw an exception
	 * @throws FailedOperationException when operation like removeCalendar throws
	 * @throws ParseException when a parse error throws
	 * @throws URISyntaxException when a URI Syntax throws
	 * @throws IOException when a IO error throws
	 * @throws ConstraintViolationException when an adding calendar violation is set
	 */
	public abstract void modifyEvent(String uid, String propertyName, String propertyNewValue) throws FailedOperationException, ObjectStoreException, IOException, URISyntaxException, ParseException, ConstraintViolationException;

	/**
	 * @param uid the event uid to remove
	 * @throws ObjectStoreException when the objectStore throw an exception
	 * @throws FailedOperationException when operation like removeCalendar throws
	 */
	public abstract void removeEvent(String uid) throws FailedOperationException, ObjectStoreException;	

	/**
	 * @param event the event to add
	 * @throws ObjectStoreException when the objectStore throw an exception
	 * @throws ConstraintViolationException when an adding calendar violation is set
	 */
	public abstract void addEvent(Calendar event) throws ObjectStoreException, ConstraintViolationException;
	
	public void Disconnect(){
		if(store != null){
			if(store.isConnected()){
				store.disconnect();
			}
		}
	}
	
}
