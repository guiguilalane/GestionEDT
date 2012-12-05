/**
 * 
 */
package fr.CalendarConnection;

import java.net.MalformedURLException;

import net.fortuna.ical4j.connector.ObjectStoreException;

/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public abstract class FactoryConnection {
	
	/**
	 * 
	 * @param url the remote Calendar loaction
	 * @param usr the user identifier
	 * @param mdp the user password
	 * @return a connection to a server
	 * @throws MalformedURLException when the url is MalFormed
	 * @throws ObjectStoreException when usr is not connected
	 */
	public abstract Connection createFactoryConnection(String url, String usr, String mdp) throws MalformedURLException, ObjectStoreException;

}
