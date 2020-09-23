package model;

import javafx.concurrent.Service;

/**
 * Service class for the MarkovTrainerTask task class.
 * 
 * @author Andrew
 */
public class MarkovTrainerService extends Service<TrainerDataPackage> {

	private String sourceText;
	
	/**
	 * @param sourceText
	 */
	public MarkovTrainerService(String sourceText) {
		this.sourceText = sourceText;
	}
	
	@Override
	protected MarkovTrainerTask createTask() {
		
		return new MarkovTrainerTask(sourceText);
	}

}
