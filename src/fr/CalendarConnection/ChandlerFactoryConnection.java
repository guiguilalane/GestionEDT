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
public class ChandlerFactoryConnection extends FactoryConnection {

	/* (non-Javadoc)
	 * @see fr.CalendarConnection.FactoryConnection#createFactoryConnection()
	 */
	@Override
	public Connection createConnection(String usr, String mdp) throws MalformedURLException, ObjectStoreException {
		return new ChandlerConnection(usr, mdp);
	}
	
	@Override
	public Connection createConnection(){
		return new ChandlerConnection();
	}

}
