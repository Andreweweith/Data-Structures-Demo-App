package model;

/**
 * Package of data for the MarkovTrainerTask to return, containing
 * a list of words, and a starter string for the MarkovGeneratorTask
 * 
 * @author Andrew
 */
public class TrainerDataPackage {

	private MyLinkedList<ListNode> wordList;
	private String starterString;
	
	@SuppressWarnings("javadoc")
	public TrainerDataPackage(MyLinkedList<ListNode> wordList, String starterString) {
		super();
		this.wordList = wordList;
		this.starterString = starterString;
	}
	@SuppressWarnings("javadoc")
	public MyLinkedList<ListNode> getWordList() {
		return wordList;
	}
	@SuppressWarnings("javadoc")
	public String getStarterString() {
		return starterString;
	}
	@SuppressWarnings("javadoc")
	public void setWordList(MyLinkedList<ListNode> wordList) {
		this.wordList = wordList;
	}
	@SuppressWarnings("javadoc")
	public void setStarterString(String starterString) {
		this.starterString = starterString;
	}
	
	
	
}
