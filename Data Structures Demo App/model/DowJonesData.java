package model;

import java.util.Date;

/**
 * Data class to represent a single piece of data from 
 * the Dow Jones Industrial Average.
 * 
 * @author Andrew
 */
public class DowJonesData implements Comparable<DowJonesData>{

	private Date date;
	private double value;
	
	/**
	 * Constructs a piece of Dow Jones data, which contains
	 * a date and a value associated with said date.
	 * 
	 * @param date
	 * @param value
	 */
	public DowJonesData(Date date, double value) {
		this.date = date;
		this.value = value;
	}

	@SuppressWarnings("javadoc")
	public Date getDate() {
		return date;
	}
	@SuppressWarnings("javadoc")
	public void setDate(Date date) {
		this.date = date;
	}
	@SuppressWarnings("javadoc")
	public double getValue() {
		return value;
	}
	@SuppressWarnings("javadoc")
	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public int compareTo(DowJonesData otherData) {
		return otherData.getDate().compareTo(this.date);
	}
	
	
}
