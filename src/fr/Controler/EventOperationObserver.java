/**
 * 
 */
package fr.Controler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import net.fortuna.ical4j.connector.FailedOperationException;
import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.model.ConstraintViolationException;

import fr.CalendarConnection.Connection;
import fr.Model.Event;

/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public class EventOperationObserver implements Observer {

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof GestionnaireEDT){
			GestionnaireEDT gestionEDT = GestionnaireEDT.getInstance();
			HashMap<String, Event> hash = (HashMap<String, Event>) arg1;
			Entry<String, Event> entry= hash.entrySet().iterator().next();
			Connection connection = gestionEDT.getConnection();
			if(entry.getKey()=="Add"){
				try {
					connection.addEvent(connection.createEvent("Europe/Paris", entry.getValue()));
				} catch (ObjectStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ConstraintViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(entry.getKey()=="Modify"){
				try {
					connection.addEvent(connection.createEvent("Europe/Paris", entry.getValue()));
				} catch (ObjectStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ConstraintViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(entry.getKey()=="Remove"){
				try {
					connection.removeEvent(entry.getValue().getUID());
				} catch (FailedOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ObjectStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
