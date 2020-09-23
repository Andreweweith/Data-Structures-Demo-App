package demo;

import model.*;
import view.*;
import controller.*;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Demo class to run the application.
 * 
 * @author Andrew
 */
public class FinalProjectDemo extends Application {

	/**
	 * Main Application Launch Method
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		WindowFacade windowFacade = new WindowFacade(primaryStage); // VIEW
		DowJonesDataService djDataService = new DowJonesDataService(); // MODEL
		DictionaryLoaderService dictionaryLoaderService = new DictionaryLoaderService(); // MODEL
		SpellingSuggestLoaderService spellingSuggestLoaderService = new SpellingSuggestLoaderService(); // MODEL
		Controller controller = new Controller(
				djDataService, 
				dictionaryLoaderService, 
				spellingSuggestLoaderService,
				windowFacade); // CONTROLLER
		
		controller.constructMainWindow();
	}

}
