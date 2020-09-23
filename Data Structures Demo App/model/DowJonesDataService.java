package model;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service class for the DowJonesDataTask task class.
 * 
 * @author Andrew
 */
public class DowJonesDataService extends Service<ObservableList<DowJonesData>>{

	@Override
	public Task<ObservableList<DowJonesData>> createTask() {
		
		return new DowJonesDataRetrieveTask();
	}
}
