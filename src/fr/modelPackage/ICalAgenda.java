package fr.modelPackage;

import java.util.ArrayList;


/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public class ICalAgenda extends Agenda {

	private ArrayList<ICalEvent> agenda;
	
	@Override
	public void addEvent(Event event) {
		agenda.add((ICalEvent) event);
	}
	
	@Override
	public void removeEvent(Event event) {
		agenda.remove((ICalEvent) event);
	}
	
	/**
	 * Allocates a new <code>ICalAgenda</code> object and initialize it.
	 * @see ArrayList
	 */
	public ICalAgenda()
	{
		agenda = new ArrayList<ICalEvent>();
	}
	
	/**
	 * Allocates a new <code>ICalAgenda</code> object and initialize it with the specified <code>agenda</code>.
	 * @param agenda the specified agenda
	 * @see ArrayList
	 */
	public ICalAgenda(ArrayList<ICalEvent> agenda)
	{
		this.agenda = agenda;
	}

}
