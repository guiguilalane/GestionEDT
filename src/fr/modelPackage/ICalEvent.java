package fr.modelPackage;

/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public class ICalEvent extends Event {

	
	/**
	 * Allocates an <code>ICalEvent</code> and initialize it with the given parameters.
	 * @param uID the uID of the event
	 * @param module the module of the event
	 * @param classRoom the classRoom of the event
	 * @param courseType the course type of the event
	 * @param remarques Some remarques on the event
	 * @param dBegin the begin date of the event
	 * @param dEnd the end date of the event
	 * @see Event#Event(String, String, String, CategoriesCourse, String, CDate, CDate)
	 */
	public ICalEvent(String uID, String module, String classRoom,
			CategoriesCourse courseType, String remarques, CDate dBegin, CDate dEnd) {
		super(uID, module, classRoom, courseType, remarques, dBegin, dEnd);
	}
	
	
}
