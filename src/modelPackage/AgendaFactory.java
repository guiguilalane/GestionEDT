package modelPackage;


/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public abstract class AgendaFactory {

	/**
	 * Create a new <code>Event</code>
	 * @param uID the uID of the event
	 * @param module the module of the event
	 * @param classRoom the classRoom of the event
	 * @param courseType the course type of the event
	 * @param remarques Some remarques on the event
	 * @param dBegin the begin date of the event
	 * @param dEnd the end date of the event
	 * @return a reference to a new <code>Event</code>
	 */
	public abstract Event createEvent(String uID, String module, String classRoom,
			CategoriesCourse courseType, String remarques, CDate dBegin, CDate dEnd);
	/**
	 * Create a new <code>ICalAgenda</code>
	 * @return a reference to a new <code>ICalAgenda</code>
	 */
	public abstract Agenda createAgenda();
	
}
