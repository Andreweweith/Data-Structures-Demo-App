package model;

import java.util.Random;

@SuppressWarnings("javadoc")
public class ListNode {
	private String word;
	private MyLinkedList<String> nextWords;
	
	public ListNode(String word) {
		this.word = word;
		nextWords = new MyLinkedList<String>();
	}
	
	public String getWord() {
		return word;
	}
	
	public void addNextWord(String nextWord) {
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator) {
		int size = nextWords.size();
		int index = generator.nextInt(size);
		
		return nextWords.get(index);
	}
	
	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + " -> ";
		}
		toReturn += "\n";
		return toReturn;
	}
}
