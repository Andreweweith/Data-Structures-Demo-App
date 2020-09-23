package model;

import java.util.*;
import java.util.regex.*;

/**
 * Helper is an abstract superclass to serve as an 
 * extension for the Text Analyzer classes. This class
 * serves as a template for the analyzer classes to follow,
 * while providing a common constructor method that passes
 * data to the Analyzer classes in the form of a String.
 * 
 * @author Andrew
 *
 */
public abstract class Helper {
	private String textAsString;
	
	/**
	 * Main class constructor for the Helper class.
	 * 
	 * @param textAsString Input string to be passed to the
	 * 					   Analyzer classes for further analysis.
	 */
	public Helper(String textAsString) {
		super();
		this.textAsString = textAsString;
		
	}

	/**
	 * @param pattern
	 * @return tokens	ArrayList of Strings containing a tokenized
	 * 					version of the string according to the specified
	 * 					pattern.
	 */
	public ArrayList<String> getTokens(String pattern) {
		ArrayList<String> tokens = new ArrayList<>();
		Pattern tokenizer = Pattern.compile(pattern);
		Matcher matcher = tokenizer.matcher(textAsString);
		
		while(matcher.find()) {
			tokens.add(matcher.group());
		}
		return tokens;
	}
	
	/**
	 * Abstract method to receive unique implementation
	 * by each of the Text Analyzer classes.
	 * 
	 * @return	Total number of words
	 */
	public abstract int getNumberOfWords();
	
	/**
	 * Abstract method to receive unique implementation
	 * by each of the Text Analyzer classes.
	 * 
	 * @return	Total number of sentences
	 */
	public abstract int getNumberOfSentences();
	
	/**
	 * Abstract method to receive unique implementation
	 * by each of the Text Analyzer classes.
	 * 
	 * @return	Total number of syllables
	 */
	public abstract int getNumberOfSyllables();
	
	/**
	 * A shared implementation of a method to be provided to
	 * each Analyzer class.
	 * <p>
	 * This method will calculate a value referred to as the 
	 * Flesch Score based on the determined values for the 
	 * Word Count, Sentence Count, and Syllable Count.
	 * <p>
	 * The Flesch Score is a numerical representation of the 
	 * readability of a specified body of text.
	 * 
	 * @param wordCount
	 * @param sentenceCount
	 * @param syllableCount
	 * @return	Double value representing the Flesch Score.
	 */
	public double getFleschScore(double wordCount, double sentenceCount, double syllableCount) {
		double asl = (wordCount / sentenceCount);
		double asw = (syllableCount / wordCount);
		double fleschScore = 206.835 - (1.015 * asl) - (84.6 * asw);
		
		return fleschScore;
	}
	
	/**
	 * @return textAsString
	 */
	public String getText() {
		return textAsString;
	}	
}
