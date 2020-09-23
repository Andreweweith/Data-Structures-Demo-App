package model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service class for the MarkovGeneratorTask task class
 * 
 * @author Andrew
 */
public class MarkovGeneratorService extends Service<String>{

	private MyLinkedList<ListNode> wordList;
	private String starterString;
	private int numOfWords;
	
	/**
	 * @param list
	 * @param starter
	 * @param num
	 */
	public MarkovGeneratorService(MyLinkedList<ListNode> list, String starter, int num) {
		wordList = list;
		starterString = starter;
		numOfWords = num;
	}
	
	@Override
	protected Task<String> createTask() {

		return new MarkovGeneratorTask(wordList, starterString, numOfWords);
	}

}
