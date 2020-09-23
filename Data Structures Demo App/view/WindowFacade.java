package view;

import org.jfree.chart.fx.ChartViewer;

import com.jfoenix.controls.JFXDrawersStack;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.DowJonesData;

/**
 * Facade class for the Application.
 * <p> 
 * The purpose of this class is to manage the windows 
 * in such a way that is easy managed by the controller.
 * The facade class will provide each window class, where
 * the controller decides what is to be done with them.
 * 
 * @author Andrew
 */
public class WindowFacade {

	private Stage primaryStage;
	
	private MainWindow mainWindow;
	private GraphWindow graphWindow;
	private TextEditorWindow textEditorWindow;
	
	/**
	 * Method to construct a facade for handling windows
	 * 
	 * @param primaryStage
	 */
	public WindowFacade(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setResizable(false);
		
		this.mainWindow = new MainWindow();
		this.graphWindow = new GraphWindow();
		this.textEditorWindow = new TextEditorWindow();
	}
	
	/** Simple method to construct a main window, which handles 
	 *  all functionality for the program. */
	public void constructMainWindow() {
		primaryStage.setTitle("Andrew Weith - Final Project");
		primaryStage.setScene(mainWindow.getMainWindowScene());
		primaryStage.show();
	}
	
	/**
	 * Allows the controller class to manage the main window's 
	 * graph tab contents. It is expected that it should be provided
	 * a pane from the GraphWindow.
	 * 
	 * @param graphPane
	 */
	public void setMainWindowGraphTabContent(ChartViewer graphPane) {
		mainWindow.setGraphTabContent(graphPane);
	}
	/**
	 * Allows the controller class to manage the main window's 
	 * spell-check tab contents.
	 */
	public void setMainWindowSpellCheckTabContent() {
		mainWindow.setSpellCheckTabContent();
	}
	/**
	 * Given a list of suggestions as input, this allows the controller
	 * to set the detailNode of the SpellCheckTab's MasterDetailPane 
	 * by providing it a list of suggestions.
	 * 
	 * @param suggestionsList
	 */
	public void setMainWindowSpellCheckDetailContent(String suggestion) {
		mainWindow.setSpellCheckDetailContent(suggestion);
	}
	
	/** @return mainWindow */
	public MainWindow getMainWindow() {
		return mainWindow;
	}
	/** @return textEditorWindow */
	public TextEditorWindow getTextEditorWindow() {
		return textEditorWindow;
	}
	
	/** @param djDataList
	 * @return new graphWindowBorderPane*/
	public ChartViewer generateGraphViewer(ObservableList<DowJonesData> djDataList) {
		return this.graphWindow.generateGraphViewer(djDataList);
	}
	
	/**
	 * @return new drawersStack
	 */
	public JFXDrawersStack generateTextEditorWindow() {
		return this.textEditorWindow.generateTextEditorWindow(primaryStage);
	}
}
