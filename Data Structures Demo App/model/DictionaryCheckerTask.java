package model;

import java.util.ArrayList;
import java.util.Collections;

import javafx.concurrent.Task;

/**
 * Task class that uses a custom HashMap class that is already loaded with
 * a full dictionary of data. This Task is called using a HashMap(dictionary),
 * and a String(wordToCheck) to determine if the wordToCheck exists within the 
 * dictionary.
 * <p>
 * If the wordToCheck does not exist within the dictionary, then a suggestion to replace
 * the wordToCheck is generated, and returned through the Task's call method. If it does 
 * exist within the dictionary, then a null value is passed as the return value of the 
 * call method. The controller will then take action contingent upon what return value is
 * passed to it from the call method.
 * 
 * @author Andrew
 */
public class DictionaryCheckerTask extends Task<String> {

	private String suggestion;
	
	private MyHashMap<String, String> dictionary; // stores all words of the dictionary
	private MyHashMap<String, Integer> probabilityDB; // stores probability of words occurring
	private String wordCheck; // word to be checked
	
	private boolean suggestWord; // (true) == incorrect spelling, (false) == correct spelling
	
	/**
	 * Uses a custom HashMap class to determine if the wordToCheck exists within the
	 * preloaded dictionary of words.
	 * <p>
	 * If the dictionary does not contain the wordToCheck, then an ArrayList
	 * of spelling suggestions will be generated, and passed as a return from
	 * the call method.
	 * 
	 * @param wordToCheck
	 */
	public DictionaryCheckerTask(MyHashMap<String, String> dict,
			MyHashMap<String, Integer> probabDB, String wordToCheck) {
		this.dictionary = dict;
		this.probabilityDB = probabDB;
		this.wordCheck = wordToCheck;
	}
	
	@Override
	protected String call() throws Exception {
		
		suggestWord = true;
		String outputWord = checkWord();
		
		if(suggestWord) {
			suggestion = correct(outputWord);
		}
		
		return suggestion;
	}
	
	private String checkWord() {
		String unpunctWord;
		String word = wordCheck.toLowerCase();
		
		//if word is found in dictionary, then it is spelled correctly. Return as is.
		//*Note* inflections like "es", "ing" are provided by the dictionary.
		if ((wordCheck = (String) dictionary.get(word)) != null) {
			suggestWord = false; // No suggestions -- word is correct
			return wordCheck;
		}
		
		// Removing punctuation at the end of word and giving another shot ("." or "," or "?!")
		int length = word.length();
		
		// Checking for the beginning of quotes (example: "she)
		if (length > 1 && word.substring(0, 1).equals("\"")) {
			unpunctWord = word.substring(1, length);
			
			if ((wordCheck = (String)dictionary.get(unpunctWord)) != null) {
				suggestWord = false; // No suggestions -- word is correct
				return wordCheck;
			}
			else { // Not found
				return unpunctWord; // removing the punctuation and returning
			}
		}
		
		// Checking if "." or ",", etc.. at the end is the problem
		// (example: book. when book exists in dictionary)
        if( word.substring(length - 1).equals(".")  || word.substring(length - 1).equals(",") ||  
        		word.substring(length - 1).equals("!") ||  word.substring(length - 1).equals(";") ||
        		word.substring(length - 1).equals(":")) {
            
        	unpunctWord = word.substring(0, length-1);
            
            if ((wordCheck = (String)dictionary.get(unpunctWord)) != null) {
                suggestWord = false; // No suggestions -- word is correct
                return wordCheck ;
            }
            else { // Not found
                return unpunctWord; // removing the punctuation and returning
            }
        }
        
        // Checking if "!\"", etc.. is the problem 
        // (example: watch!" when watch exists in dictionary)
        if (length > 2 && word.substring(length-2).equals(",\"")  || word.substring(length-2).equals(".\"") 
            || word.substring(length-2).equals("?\"") || word.substring(length-2).equals("!\"") ) {
            
        	unpunctWord = word.substring(0, length-2);
            
            if ((wordCheck = (String)dictionary.get(unpunctWord)) != null)
            {
                suggestWord = false; // No suggestions -- word is correct
                return wordCheck ;
            }
            else // Not found
                return unpunctWord; // removing the inflections and returning
        }
        
        // If word continues to fail checks, word couldn't be corrected, and thus is spelled incorrectly
        return word; // return the word, and then ask for suggestions
	}
	
	// Return ArrayList of all possible corrections of supplied word
	private final ArrayList<String> edits(String word)  {
		ArrayList<String> result = new ArrayList<String>();
		
		for(int i = 0; i < word.length(); ++i)  {
		    result.add(word.substring(0, i) + word.substring(i + 1)); 
		}
		for(int i = 0; i < word.length() - 1; ++i)  {
		    result.add(word.substring(0, i) + word.substring(i + 1, i + 2) + 
		    		word.substring(i, i + 1) + word.substring(i + 2)); 
		}
		for(int i = 0; i < word.length(); ++i)  {    
		    for(char c = 'a'; c <= 'z'; ++c)   {
		        result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i + 1));
		    }
		}
		for(int i = 0; i <= word.length(); ++i)  {
		    for(char c = 'a'; c <= 'z'; ++c)  {
		        result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
		    }
		}
		return result;
	}
	
	@SuppressWarnings("javadoc")
	public final String correct(String word) {
		if(probabilityDB.containsKey(word)) {
		    return word; // perfectly safe word
		}
		ArrayList<String> editsList = edits(word);
		MyHashMap<Integer, String> candidates = new MyHashMap<Integer, String>();
		
		// Iterating through list of all possible corrections for word
		for(String s : editsList) {
		    if(probabilityDB.containsKey(s)) 
		    {
		        candidates.put(probabilityDB.get(s),s);
		    }
		} 
		// In the first stage of error correction, any possible corrections from the editsList are found 
		// the word database probabilityDB, then returns the one verified highest probability correction
		if(candidates.size() > 0) {
		     return candidates.get(Collections.max(candidates.keySet()));
		}
		// In the second stage we apply the first stage method on possible collections of editsList.
		// Second stage statistics would suggest accuracy of ~98%
		for(String s : editsList) {    
		     for(String w : edits(s)) { 
		            if(probabilityDB.containsKey(w)) { 
		                candidates.put(probabilityDB.get(w),w);
		            }
		     }
		}
		   
		return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet())) : null;
	}

}
