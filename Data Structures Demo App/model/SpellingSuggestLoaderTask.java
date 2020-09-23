package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.concurrent.Task;

/**
 * Task class using a custom HashMap class to store the probability values of 
 * certain words appearing, based on the occurrences of each word appearing in
 * a large input text file. The results are stored in the HashMap (probabilityDB)
 * and passed as the return value of this Task's call method.
 * 
 * @author Andrew
 */
public class SpellingSuggestLoaderTask extends Task<MyHashMap<String, Integer>>{

	private MyHashMap<String, Integer> probabilityDB;
	
	private File wordProbabilityDatabaseFile = new File("Spell Checker/wordProbabilityDatabase.txt");
	
	@Override
	protected MyHashMap<String, Integer> call() throws Exception {
		
		// **** Task progress values **** //
		final int MAX = 132792;
		// update progress below; increment progress value after each line is read
		int progress = 1;
		
		
		probabilityDB = new MyHashMap<String, Integer>();
		
		try {
			BufferedReader dbReader = new BufferedReader(new FileReader(wordProbabilityDatabaseFile));
			Pattern p = Pattern.compile("\\w+");
			
			// Read from the input file, update probabilistic values of the according words accordingly
			for(String temp = ""; temp != null; temp = dbReader.readLine()) {
				
				//**** PROGRESS AND MESSAGE ****//
				updateMessage("     Reading Database Data: Line " + progress);
				updateProgress(progress++, MAX);
				
				Matcher m = p.matcher(temp.toLowerCase());
				while(m.find()) {
					// This serves as an indicator of the words' probability
					probabilityDB.put((temp = m.group()),
							probabilityDB.containsKey(temp) ? probabilityDB.get(temp) + 1 : 1);
				}
			}
			dbReader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error reading from probabilityDatabase");
		}
		
		return probabilityDB;
	}
	
	@Override
	protected void succeeded() {
		super.succeeded();
		updateMessage("     Database Data Read Successfully!");
		updateProgress(-1, -1);
	}
}
