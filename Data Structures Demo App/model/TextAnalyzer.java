package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is an extension of the Helper class.
 * <p>
 * The Text Analyzer class is intended to use a 
 * collection of methods to analyze a given 
 * string to determine the following:
 * <p>
 *  - Total Number of Words <p>
 *  - Total Number of Sentences <p>
 *  - Total Number of Syllables <p>
 *  - Flesch Score based on the previous totals
 * <p>
 * 
 * @author Andrew
 *
 */
public class TextAnalyzer extends Helper{

	/**
	 * Main class constructor
	 * <p>
	 * Inherits the String text from the superclass Helper.
	 * 
	 * @param text	Input string to be analyzed. Inherited from superclass.
	 */
	public TextAnalyzer(String text) {
		super(text);
	}

	// METHOD IMPLEMENTATION 
	@Override
	public int getNumberOfWords() {
		int num = 0;
		String pattern = "[a-zA-Z]\\w{0,}";
		Pattern tokenizer = Pattern.compile(pattern);
		Matcher matcher = tokenizer.matcher(this.getText());
		while(matcher.find()) { 
			++num;
		}
		return num;
	}

	// METHOD IMPLEMENTATION 
	@Override
	public int getNumberOfSentences() {
		int num = 0;
		String pattern = "[.!?:]+";
		Pattern tokenizer = Pattern.compile(pattern);
		Matcher matcher = tokenizer.matcher(this.getText());
		while (matcher.find()) {
			++num;
		} 
		return num;
	}
	
	// METHOD IMPLEMENTATION 
	@Override
	public int getNumberOfSyllables() {
		int count = 0;

	    if (this.getText().charAt(this.getText().length()-1) == 'e') {
	        if (silentE(this.getText())){
	            String newword = this.getText().substring(0, this.getText().length()-1);
	            count = count + countIt(newword);
	        } else {
	            count++;
	        }
	    } else {
	        count = count + countIt(this.getText());
	    }
	    return count;
	}
	// for getNumberOfSyllables
	private int countIt(String word) {
	    int count = 0;
	    Pattern splitter = Pattern.compile("[^aeiouy]*[aeiouy]+");
	    Matcher m = splitter.matcher(word);

	    while (m.find()) {
	        count++;
	    }
	    return count;
	}
	// for getNumberOfSyllables
	private boolean silentE(String word) {
	    word = word.substring(0, word.length()-1);

	    Pattern pattern = Pattern.compile("[aeiouy]");
	    Matcher m = pattern.matcher(word);

	    if (m.find()) {
	        return true;
	    } else
	        return false;
	}
	
}
