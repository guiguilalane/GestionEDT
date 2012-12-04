package fr.Model;


/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public abstract class Event {

	/**
	 * Mettre en préfixe R(B)W_identifiant, signifie que l'evenement est récurent weekly ou bi-weekly
	 */
	protected String UID;

	protected String module;
	
	protected String classRoom;
	
	protected CategoriesCourse courseType;
	
	protected String remarques;
	
	protected CDate dBegin;
	
	protected CDate dEnd;
	
	/**
	 * 
	 */
	public Event(){
		
	}

	/**
	 * Allocates a <code>Event</code> object and initialize it .
	 * @param uID the uID of the event
	 * @param module the module of the event
	 * @param classRoom the classRoom of the event
	 * @param courseType the course type of the event
	 * @param remarques Some remarques on the event
	 * @param dBegin the begin date of the event
	 * @param dEnd the end date of the event
	 */
	public Event(String uID, String module, String classRoom,
			CategoriesCourse courseType, String remarques, CDate dBegin, CDate dEnd) {
		UID = uID;
		this.module = module;
		this.classRoom = classRoom;
		this.courseType = courseType;
		this.remarques = remarques;
		this.dBegin = dBegin;
		this.dEnd = dEnd;
	}
	
	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}

	public CategoriesCourse getCourseType() {
		return courseType;
	}

	public void setCourseType(CategoriesCourse courseType) {
		this.courseType = courseType;
	}

	public String getRemarques() {
		return remarques;
	}

	public void setRemarques(String remarques) {
		this.remarques = remarques;
	}

	public CDate getdBegin() {
		return dBegin;
	}

	public void setdBegin(CDate dBegin) {
		this.dBegin = dBegin;
	}

	public CDate getdEnd() {
		return dEnd;
	}

	public void setdEnd(CDate dEnd) {
		this.dEnd = dEnd;
	}
}
