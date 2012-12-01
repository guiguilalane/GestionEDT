package modelPackage;

/**
 * Class AgendaICalFactory is a factory class, that is use to create specific {@link ICalAgenda iCal agenda} and/or specific {@link ICalEvent iCal event}
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 * @see AgendaFactory
 *
 */
public class AgendaICalFactory extends AgendaFactory {

	/**
	 * Initialize a newly created <code>AgendaICalFactory</code>
	 */
	public AgendaICalFactory(){
	}
	
	@Override
	public ICalEvent createEvent(String uID, String module, String classRoom,
			CategoriesCourse courseType, String remarques, CDate dBegin, CDate dEnd) {
		return new ICalEvent(uID, module, classRoom, courseType, remarques, dBegin, dEnd);
	}

	@Override
	public ICalAgenda createAgenda() {
		return new ICalAgenda();
	}

}
