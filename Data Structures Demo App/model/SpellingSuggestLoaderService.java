package model;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Service class for the SpellingSuggestLoaderTask task class.
 * 
 * @author Andrew
 */
public class SpellingSuggestLoaderService extends Service<MyHashMap<String, Integer>> {

	@Override
	protected Task<MyHashMap<String, Integer>> createTask() {

		return new SpellingSuggestLoaderTask();
	}

}
