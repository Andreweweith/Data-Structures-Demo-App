package model;

import java.util.Date;

import javafx.concurrent.Task;

/**
 * Task class for using a Binary Search Tree to search through
 * a shuffled ArrayList of DowJonesData, using the date to retrieve 
 * the corresponding value.
 * 
 * @author Andrew
 */
public class DowJonesDataSearchTask extends Task<Double> {

	private BST dataBST;
	private Date targetDate;
	
	/**
	 * Uses a Binary Search Tree to search through a randomized array of data,
	 * 
	 * @param shuffledBST
	 * @param targetDate
	 */
	public DowJonesDataSearchTask(BST shuffledBST, Date targetDate) {
		this.targetDate = targetDate;
		this.dataBST = shuffledBST;
	}
	
	@Override
	public Double call() throws Exception {

		Double targetValue = dataBST.get(targetDate);
		
		return targetValue;
	}

}
