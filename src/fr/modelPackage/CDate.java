package fr.modelPackage;

import java.lang.Integer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;




/**
 * @author Florian FAGNIEZ - Noémie RULLIER - Guillaume COUTABLE
 *
 */
public class CDate {

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;

	/*format de la date caldav :
	 * yyyymmddThhiissZ
	 * y : year
	 * m : month
	 * d : day
	 * T : ?
	 * h : heure
	 * i : minute
	 * s : seconde
	 * Z : TimeZone
	 */
	/**
	 * Allocate a new <code>CDate</code> object and initialize it with a format <code>String</code> : <code>yyyymmddThhiissZ</code>.
	 * <p>Where :
	 * <ul>
	 * 		<li> <code>yyyy</code> is the year, as four decimal digits
	 * 		<li> <code>mm</code> is the month (01 through 12), as two decimal digits
	 * 		<li> <code>dd</code> is the day (01 through 31), as two decimal digits
	 * 		<li> <code>hh</code> is the hour of the day (0 through 23), as two decimal digits
	 * 		<li> <code>ii</code> is the minute within the hour (0 through 59), as two decimal digits
	 * 		<li> <code>ss</code> is the seconde within the minute (0 through 59), as two decimal digits
	 * </ul>
	 * </p>
	 * 
	 * @param calendarVal the <code>String</code> format date to convert
	 * @see Integer#intValue()
	 * @see String#substring(int, int)
	 */
	public CDate(String calendarVal) {
		Integer a = new Integer(calendarVal.substring(0, 4));
		Integer mo = new Integer(calendarVal.substring(4, 6));
		Integer j = new Integer(calendarVal.substring(6, 8));
		Integer h = new Integer(calendarVal.substring(9, 11));
		Integer mi = new Integer(calendarVal.substring(11, 13));
		year = a.intValue();
		month = mo.intValue();
		day = j.intValue();
		hour = h.intValue();
		minute = mi.intValue();
	}

	/**
	 * Allocate a new <code>CDate</code> object and initialize it so that it represents the instant at the start of the second specified by the 
	 * <code>year, month, day, hour,</code> and <code>min</code>, in the local time zone.
	 * @param year the year
	 * @param month the month between 1-12
	 * @param day the day of the month between 1-31
	 * @param hour the hours between 0-23
	 * @param minute the minutes between 0-59
	 */
	public CDate(int year, int month, int day, int hour, int minute) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
	}

	/**
	 * Creates a string representation of this <code>CDate</code> object of the form : yyyymmddThhiissZ.
	 * 
	 * Where :
	 * <ul>
	 * 		<li> <code>yyyy</code> is the year, as four decimal digits
	 * 		<li> <code>mm</code> is the month (01 through 12), as two decimal digits
	 * 		<li> <code>dd</code> is the day (01 through 31), as two decimal digits
	 * 		<li> <code>hh</code> is the hour of the day (0 through 23), as two decimal digits
	 * 		<li> <code>ii</code> is the minute within the hour (0 through 59), as two decimal digits
	 * 		<li> <code>ss</code> is the seconde within the minute (0 through 59), as two decimal digits
	 * </ul>
	 * @return a string representation if this date
	 * @see String#format(String, Object...)
	 */
	public String toCalendarForm() {
		String s = "";
		s = String.format("%4d%2d%2dT%2d%2d00Z", year, month, day, hour, minute);
		return s;
	}

	public String toString(){
		return "" + year + " " + month + " " + day + " " + hour + " " + minute;
	}

	public String toDate(){
		return day + "/" + month + "/" + year;
	}
	
	public String toHour(){
		return hour + ":" + minute;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	@SuppressWarnings("deprecation")
	public int getDayOfWeek() throws ParseException{
		String sDate = year + "/" + month + "/" + day;
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date date = format.parse(sDate);
		return date.getDay();
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}


	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}


	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}


	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}


	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}


	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}


	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}


	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}


	/**
	 * @param minute the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	public static void main(String args[]) throws ParseException{
		CDate test1 = new CDate(2012, 12, 3, 14, 00);
		CDate test2 = new CDate(2012, 5, 12, 14, 00);
		CDate test3 = new CDate(2012, 7, 29, 14, 00);
		System.out.println(test1.getDayOfWeek());
		System.out.println(test2.getDayOfWeek());
		System.out.println(test3.getDayOfWeek());
	}
}
