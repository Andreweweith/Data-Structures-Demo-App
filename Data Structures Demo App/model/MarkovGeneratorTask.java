package model;

import java.util.Random;

import javafx.concurrent.Task;

/**
 * Task class for the MarkovGenerator application. 
 * <p>
 * Using a list that is previously generated from the MarkovTrainerTask and passed
 * to it from the controller, generates a specified amount of words, beginning from
 * the specified starter that has also been passed from the controller, created by the
 * Trainer task.
 * 
 * @author Andrew
 */
public class MarkovGeneratorTask extends Task<String>{

	private MyLinkedList<ListNode> wordList;
	private String starterString;
	private int numOfWords;
	
	private Random rnGenerator;
	
	/**
	 * Constructor for the task class, contains the list of words, the starter string,
	 * and the number of words to generate.
	 * 
	 * @param list
	 * @param starter
	 * @param num
	 */
	public MarkovGeneratorTask(MyLinkedList<ListNode> list, String starter, int num) {
		wordList = list;
		starterString = starter;
		numOfWords = num;
		
		rnGenerator = new Random();
	}
	
	@Override
	protected String call() throws Exception {
		String output = "";
		if (wordList.isEmpty()) {
			System.out.println("Nothing has been read yet");
			return output;
		}
		if (numOfWords == 0) {
			return output;
		}
		String currentWord = starterString;
		output = output + currentWord;
		int count = 1;
		while (count < numOfWords) {
			
			updateProgress(count, numOfWords);
			updateMessage(
					"     Generated " + count + "/" + numOfWords + " Words...");
			
			ListNode node = findNode(currentWord);
			String w = node.getRandomNextWord(rnGenerator);
			output = output + " " + w;
			currentWord = w;
			count++;
		}
		return output;
	}
	
	@Override
	protected void succeeded() {
		super.succeeded();
		updateMessage("     Text Generated Successfully!");
		updateProgress(-1, -1);
	}

	private ListNode findNode(String word) {
		for (ListNode node : wordList) {
			if (word.equals(node.getWord())) {
				return node;
			}
		}
		return null;
	}
}
