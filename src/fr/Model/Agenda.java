package fr.Model;

/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public abstract class Agenda {
	
	/**
	 * Add a new <code>Event</code> to the Agenda
	 * @param event
	 */
	protected abstract void addEvent(Event event);
	
	/**
	 * Remove the specific <code>Event</code> from the Agenda
	 * @param event
	 */
	protected abstract void removeEvent(Event event);
	
}
