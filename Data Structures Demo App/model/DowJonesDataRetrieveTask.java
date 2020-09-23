package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * Task class for the DowJonesData class. 
 * <p>
 * The call method for this task will read data from a 
 * text file containing Dow Jones Industrial Average data.
 * As it reads each line of the file, it will split the 
 * read line by a " " (Whitespace) delimiter, where the 
 * first String stored is the Date, and the second String stored
 * is the Value associated with said date.
 * <p>
 * When a line is read, if the value does not contain any 
 * 
 * @author Andrew
 */
public class DowJonesDataRetrieveTask extends Task<ObservableList<DowJonesData>> {

	private File DJIAFile = new File("Dow Jones/DJIA.txt");
	
	@Override
	public ObservableList<DowJonesData> call() throws Exception {

		/** Set the maximum progress as the total lines being read */
		final int MAX = 2610;
		// progress is updated below, after each line is read
		// increment progress variable after each updateProgress
		int progress = 1;
		
		BufferedReader djInput = new BufferedReader(new FileReader(DJIAFile));
		
		ObservableList<DowJonesData> dowJonesDataList = FXCollections.observableArrayList();
		String line; // line variable will hold the current line (of string) read from the buffered reader
		
		
		// Each line read from the file is formatted as such:
		// DATE			VALUE
		// yyyy-mm-dd	#####.##
		while((line = djInput.readLine()) != null) {
			
			/** UPDATE PROGRESS EACH LINE READ */
			updateMessage("    Reading Dow Jones Data: Line " + progress);
			updateProgress(progress++, MAX);
			
			// Here we split the Date and Value by ANY whitespace
			String[] splitLine = line.split("\\s+");
			
			// Store strings in variables
			String date = splitLine[0];
			String value = splitLine[1];
			
			// Split the date into 3 strings - YEAR[0], MONTH[1], DAY[2]
			String[] dateSplitter = date.split("-");
			int year = Integer.parseInt(dateSplitter[0]);
			int month = Integer.parseInt(dateSplitter[1]);
			int day = Integer.parseInt(dateSplitter[2]);
			
			// Store values in calendar --> use calendar to produce Date object for Data class
			// *NOTE* Month MUST be decremented, as GregorianCalendar reads months (0-11), etc.
			GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
			Date dateData = calendar.getTime();
			
			// Check if value is empty on particular day
			if(!value.equals(".")) {
				Double doubleValue = Double.parseDouble(value);
				
				DowJonesData djData = new DowJonesData(dateData, doubleValue);
				dowJonesDataList.add(djData);
			}
		}
		
		djInput.close();
		
		return dowJonesDataList;
	}
	
	@Override
	protected void succeeded() {
		super.succeeded();
		updateMessage("    Finished Reading Data!");
		updateProgress(-1, -1);
	}
}
