package model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service class for the DictionaryLoaderTask task class.
 * 
 * @author Andrew
 */
public class DictionaryLoaderService extends Service<MyHashMap<String, String>> {

	@Override
	protected Task<MyHashMap<String, String>> createTask() {
		
		return new DictionaryLoaderTask();
	}
}
