package fr.modelPackage;

import java.util.HashMap;


/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public class ICalAgenda extends Agenda {

	private HashMap<String,ICalEvent> agenda;
	
	@Override
	public void addEvent(Event event) {
		agenda.put(event.getUID(), (ICalEvent) event);
	}
	
	@Override
	public void removeEvent(Event event) {
		agenda.remove(event.getUID());
	}
	
	/**
	 * Allocates a new <code>ICalAgenda</code> object and initialize it.
	 * @see HashMap
	 */
	public ICalAgenda()
	{
		agenda = new HashMap<String,ICalEvent>();
	}
	
	/**
	 * Allocates a new <code>ICalAgenda</code> object and initialize it with the specified <code>agenda</code>.
	 * @param agenda the specified agenda
	 * @see HashMap
	 */
	public ICalAgenda(HashMap<String,ICalEvent> agenda)
	{
		this.agenda = agenda;
	}

}
