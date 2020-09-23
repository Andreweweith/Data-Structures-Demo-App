package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.concurrent.Task;

/**
 * Task class that allows the controller to read in all values from
 * a dictionary text file, and store them in a custom HashMap class.
 * It will then return the newly created HashMap.
 * <p>
 * Each word is stored in the HashMap with the key/value pair being
 * identical (the string representing the word).
 * 
 * @author Andrew
 */
public class DictionaryLoaderTask extends Task<MyHashMap<String, String>>{

	private MyHashMap<String, String> dictionary;
	
	private File dictionaryFile = new File("Spell Checker/dictionary.txt");
	
	@Override
	protected MyHashMap<String, String> call() throws Exception {
		
		// **** Task progress values ****** //
		final int MAX = 99171;
		// update progress below; increment progress value after each line is read.
		int progress = 1;
		
		dictionary = new MyHashMap<String, String>();
		
		try {
			BufferedReader dictionaryReader = new BufferedReader(new FileReader(dictionaryFile));
			
			while (dictionaryReader.ready()) {
				
				//**** PROGRESS AND MESSAGE **** //
				updateMessage("     Reading Dictionary Data: Line " + progress);
				updateProgress(progress++, MAX);
				
				String dictionaryInput = dictionaryReader.readLine();
				String[] dictionarySplit = dictionaryInput.split("\\s");
				
				for(int i = 0; i < dictionarySplit.length; i++) {
					// Key and value are IDENTICAL
					dictionary.put(dictionarySplit[i], dictionarySplit[i]);
				}
			}
			dictionaryReader.close();

		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error reading dictionary from file");
		}
		
		return dictionary;
	}
	
	@Override
	protected void succeeded() {
		super.succeeded();
		updateMessage("     Dictionary Data Read Successfully!");
		updateProgress(-1, -1);
	}
}
