package view;

import java.io.*;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Window class for the Generator/Text Analyzer Application.
 * <p>
 * This class provides a JFXDrawersStack that contains a BorderPane of the primary controls,
 * and may be passed to the controller class in order to process the Event Handling from this Window.
 * <p>
 * It provides this JFXDrawersStack through the generateTextEditorWindow method
 * 
 * @author Andrew
 *
 */
public class TextEditorWindow {

	private LoadFileListener loadFileListener;
	private WordCountListener wordCountListener;
	private SentenceCountListener sentenceCountListener;
	private SyllableCountListener syllableCountListener;
	private FleschScoreListener fleschScoreListener;
	
	private BorderPane textEditorBorderPane;
	
	// MENU BAR
	private StackPane menuBar;
	private HBox menuOptionsTray;
	private MenuButton fileMenu;
	private MenuButton editMenu;
	// Under FILE
	private MenuItem newMenuItem;
	private MenuItem loadFileMenuItem;
	private FileChooser loadFileChooser;
	private File loadTextFile;
	// Under EDIT
	private MenuItem wordCountMenuItem;
	private MenuItem sentenceCountMenuItem;
	private MenuItem syllableCountMenuItem;
	private MenuItem fleschScoreMenuItem;
	
	// TEXT AREA
	private StackPane treeViewStackPane;
	
	private JFXTextArea textArea;
	
	// STATUS BAR
	private HBox statusBar;
	private VBox wordCountBox;
	private JFXTextField wordCountTextField;
	private VBox sentenceCountBox;
	private JFXTextField sentenceCountTextField;
	private VBox syllableCountBox;
	private JFXTextField syllableCountTextField;
	private VBox fleschScoreBox;
	private JFXTextField fleschScoreTextField;
	
	// Drawer Contents
	private JFXDrawer fleschLegendDrawer;
	private JFXDrawersStack drawersStack;
	private StackPane legendDrawerPane;
	
	private JFXButton fleschLegendButton;
	
	
	/**
	 * Method to construct a new TextEditorWindow. The primaryStage
	 * is automatically passed to it from the WindowFacade, so it is 
	 * not handled by the controller. 
	 * <p>
	 * This method will return a new JFXDrawersStack, which simply acts as
	 * a stack pane containing drawers. The majority of the content is 
	 * contained within the textEditorBorderPane, which is what the 
	 * JFXDrawersStack holds as its content, along with said drawers.
	 */
	@SuppressWarnings("unchecked")
	public JFXDrawersStack generateTextEditorWindow(Stage primaryStage) {
		textEditorBorderPane = new BorderPane();
		textEditorBorderPane.getStyleClass().add("nightmode-stack-pane");
		
		/********************************
		*         Menu Buttons          *
		********************************/
		fileMenu = new MenuButton("LOAD");
		editMenu = new MenuButton("ANALYZE");

		/********************************
		*     Application Functions     *
		********************************/
		newMenuItem = new MenuItem("Refresh");
		newMenuItem.setOnAction(e -> {
			textArea.clear();
			textArea.getStyleClass().remove("jfx-text-area-focussed");
			
			wordCountTextField.clear();
			wordCountTextField.getStyleClass().remove("jfx-text-field-focussed");
			sentenceCountTextField.clear();
			sentenceCountTextField.getStyleClass().remove("jfx-text-field-focussed");
			syllableCountTextField.clear();
			syllableCountTextField.getStyleClass().remove("jfx-text-field-focussed");
			fleschScoreTextField.clear();
			fleschScoreTextField.getStyleClass().remove("jfx-text-field-focussed");
			
		});
		
		loadFileMenuItem = new MenuItem("Initialize Text File");
		loadFileChooser = new FileChooser();
		loadFileChooser.setTitle("** LOAD A TEXT FILE FOR ANALYZING **");
		loadFileChooser.setInitialDirectory(new File("Generator/generatorInput"));
		loadFileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Text File", "*.txt", "*.txt.*"));
		loadFileMenuItem.setOnAction(e -> {
			// Obtain file information, then set as the current file
			loadTextFile = loadFileChooser.showOpenDialog(primaryStage);
			if (loadTextFile != null) {
				LoadFileEventObject ev = new LoadFileEventObject(this, loadTextFile);
				if(loadFileListener != null) {
					loadFileListener.loadFileClicked(ev);
					
					textArea.getStyleClass().add("jfx-text-area-focussed");
				}
			}
		});
		
		fileMenu.getItems().addAll(newMenuItem, loadFileMenuItem);
		
		
		/********************************
		*       Editor Functions        *
		********************************/
		wordCountMenuItem = new MenuItem("Word Count");
		wordCountMenuItem.setOnAction(e -> {
			if(textArea.getText() != null) {
				WordCountEventObject ev = new WordCountEventObject(this, textArea.getText());
				if(wordCountListener != null) {
					wordCountListener.wordCountClicked(ev);
					
					wordCountTextField.getStyleClass().add("jfx-text-field-focussed");
				}
			}
		});
		sentenceCountMenuItem = new MenuItem("Sentence Count");
		sentenceCountMenuItem.setOnAction(e -> {
			if(textArea.getText() != null) {
				SentenceCountEventObject ev = new SentenceCountEventObject(this, textArea.getText());
				if(sentenceCountListener != null) {
					sentenceCountListener.sentenceCountClicked(ev);
					
					sentenceCountTextField.getStyleClass().add("jfx-text-field-focussed");
				}
			}
		});
		syllableCountMenuItem = new MenuItem("Syllable Count");
		syllableCountMenuItem.setOnAction(e -> {
			if(textArea.getText() != null) {
				SyllableCountEventObject ev = new SyllableCountEventObject(this, textArea.getText());
				if(syllableCountListener != null) {
					syllableCountListener.syllableCountClicked(ev);
					
					syllableCountTextField.getStyleClass().add("jfx-text-field-focussed");
				}
			}
		});
		fleschScoreMenuItem = new MenuItem("Flesch Score");
		fleschScoreMenuItem.setOnAction(e -> {
			if(textArea.getText() != null) {
				FleschScoreEventObject ev = new FleschScoreEventObject(this, textArea.getText());
				if(fleschScoreListener != null) {
					fleschScoreListener.fleschScoreClicked(ev);
					
					fleschScoreTextField.getStyleClass().add("jfx-text-field-focussed");
				}
			}
		});
		
		// * DISABLE UNTIL OTHER FIELDS ENTERED *
		fleschScoreMenuItem.setDisable(true); 
		
		editMenu.getItems().addAll(wordCountMenuItem, sentenceCountMenuItem,
				syllableCountMenuItem, fleschScoreMenuItem);
		
		
		/********************************
		*    Flesch Legend - Table      *
		********************************/
		JFXTreeTableColumn<FleschScoreLabel, String> dataRangeColumn =
				new JFXTreeTableColumn<>("Data Range");
		dataRangeColumn.setPrefWidth(352);
		dataRangeColumn.setResizable(false);
		dataRangeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<FleschScoreLabel, String> param) ->{
			if(dataRangeColumn.validateValue(param)) return param.getValue().getValue().dataRangeValue;
			else return dataRangeColumn.getComputedValue(param);
		});
		JFXTreeTableColumn<FleschScoreLabel, String> readabilityColumn =
				new JFXTreeTableColumn<>("Readability");
		readabilityColumn.setPrefWidth(352);
		readabilityColumn.setResizable(false);
		readabilityColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<FleschScoreLabel, String> param) ->{
			if(readabilityColumn.validateValue(param)) return param.getValue().getValue().readabilityValue;
			else return readabilityColumn.getComputedValue(param);
		});
		JFXTreeTableColumn<FleschScoreLabel, String> educationColumn =
				new JFXTreeTableColumn<>("Education");
		educationColumn.setPrefWidth(352);
		educationColumn.setResizable(false);
		educationColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<FleschScoreLabel, String> param) ->{
			if(educationColumn.validateValue(param)) return param.getValue().getValue().educationValue;
			else return educationColumn.getComputedValue(param);
		});
		JFXTreeTableColumn<FleschScoreLabel, String> comprehensionColumn =
				new JFXTreeTableColumn<>("Adult Comprehension");
		comprehensionColumn.setPrefWidth(352);
		comprehensionColumn.setResizable(false);
		comprehensionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<FleschScoreLabel, String> param) ->{
			if(comprehensionColumn.validateValue(param)) return param.getValue().getValue().comprehensionValue;
			else return comprehensionColumn.getComputedValue(param);
		});
		// Data 
		ObservableList<FleschScoreLabel> fleschScoreLabels = FXCollections.observableArrayList();
		fleschScoreLabels.add(new FleschScoreLabel("0 - 29", "Very Difficult","College Graduates", "5%"));
		fleschScoreLabels.add(new FleschScoreLabel("30 - 49", "Difficult","College", "30%"));
		fleschScoreLabels.add(new FleschScoreLabel("50 - 59", "Fairly Difficult","Senior High School", "50%"));
		fleschScoreLabels.add(new FleschScoreLabel("60 - 69", "Plain English","13 to 15 year-olds", "80%"));
		fleschScoreLabels.add(new FleschScoreLabel("70 - 79", "Fairly Easy","12 year-olds", "90%"));
		fleschScoreLabels.add(new FleschScoreLabel("80 - 89", "Easy","11 year-olds", "90%"));
		fleschScoreLabels.add(new FleschScoreLabel("90 - 100", "Very Easy","10 year-olds", "90%"));
				
		final TreeItem<FleschScoreLabel> root = new RecursiveTreeItem<FleschScoreLabel>(
				fleschScoreLabels, RecursiveTreeObject::getChildren);
		JFXTreeTableView<FleschScoreLabel> treeView = 
				new JFXTreeTableView<FleschScoreLabel>(root);
			
		treeView.setShowRoot(false);
		treeView.setEditable(false);
		treeView.getColumns().setAll(dataRangeColumn, readabilityColumn, educationColumn, comprehensionColumn);
			
		// Add treeView to a stack pane
		treeViewStackPane = new StackPane();
		treeViewStackPane.setAlignment(Pos.CENTER);
		treeViewStackPane.getChildren().add(treeView);		
		
		
		/************************************
		*  Flesch Legend - Drawer Handling  *
		************************************/
		fleschLegendDrawer = new JFXDrawer();
		legendDrawerPane = new StackPane();
		legendDrawerPane.setAlignment(Pos.CENTER);
		// Add tree view - stack pane to the legendDrawerPane
		legendDrawerPane.getChildren().addAll(treeViewStackPane);
		fleschLegendDrawer.setDirection(DrawerDirection.BOTTOM);
		fleschLegendDrawer.setDefaultDrawerSize(395);
		fleschLegendDrawer.setSidePane(legendDrawerPane);
		fleschLegendDrawer.setOverLayVisible(true);
		fleschLegendDrawer.setResizableOnDrag(false);
		
		// Pane and Button to display the full menu
		drawersStack = new JFXDrawersStack();
		drawersStack.setContent(textEditorBorderPane);
		
		
		/********************************
		*    Flesch Legend - Actions    *
		********************************/
		fleschLegendButton = new JFXButton("Flesch Score Legend");
		fleschLegendButton.getStyleClass().add("drawer-button-ready");
		fleschLegendButton.setOnAction(e -> {
			drawersStack.toggle(fleschLegendDrawer, true);
			
			// DEFAULT READY -> remove ready, add/remove transition, add waiting
			fleschLegendButton.getStyleClass().remove("drawer-button-ready");
			fleschLegendButton.getStyleClass().add("drawer-button-transition");
			fleschLegendButton.getStyleClass().remove("drawer-button-transition");
			fleschLegendButton.getStyleClass().add("drawer-button-waiting");
			
		});
		// DEFAULT WAITING -> remove waiting, add/remove transition, add ready
		fleschLegendDrawer.setOnDrawerClosed(e -> {
			fleschLegendButton.getStyleClass().remove("drawer-button-waiting");
			fleschLegendButton.getStyleClass().add("drawer-button-transition");
			fleschLegendButton.getStyleClass().remove("drawer-button-transition");
			fleschLegendButton.getStyleClass().add("drawer-button-ready");
		}); 
		
		
		/********************************
		*           Menu Bar            *
		********************************/
		menuBar = new StackPane();
		menuBar.setPadding(new Insets(20, 0, 20, 0));
		menuBar.setAlignment(Pos.CENTER);
		menuBar.getStyleClass().add("dark-menu-bar");
		
		menuOptionsTray = new HBox(50);
		menuOptionsTray.setAlignment(Pos.CENTER);
		
		fileMenu.getStyleClass().add("button-raised2");
		editMenu.getStyleClass().add("button-raised2");
		
		menuOptionsTray.getChildren().addAll(fileMenu, fleschLegendButton,
				editMenu);
		
		menuBar.getChildren().add(menuOptionsTray);
		

		/********************************
		*        Main Text Area         *
		********************************/
		textArea = new JFXTextArea();
		textArea.setPromptText("Text Editor:");
		textArea.setLabelFloat(true);
		textArea.setEditable(true);
		textArea.setMaxWidth(1270);
		textArea.setMinHeight(400);
		textArea.setPadding(new Insets(30, 0, 30, 0));
		textArea.setWrapText(true);
		
		/********************************
		*          Status Bar           *
		********************************/
		statusBar = new HBox(30);
		statusBar.setAlignment(Pos.CENTER);
		statusBar.setPadding(new Insets(15, 0, 30, 0));
		
		// Word Count
		wordCountBox = new VBox(5);
		wordCountTextField = new JFXTextField();
		wordCountTextField.setPromptText("Word Count:");
		wordCountTextField.setLabelFloat(true);
		wordCountTextField.setEditable(false);
		wordCountTextField.setFocusTraversable(false);
		wordCountBox.getChildren().addAll(wordCountTextField);
		
		// Sentence Count
		sentenceCountBox = new VBox(5);
		sentenceCountTextField = new JFXTextField();
		sentenceCountTextField.setPromptText("Sentence Count:");
		sentenceCountTextField.setLabelFloat(true);
		sentenceCountTextField.setEditable(false);
		sentenceCountTextField.setFocusTraversable(false);
		sentenceCountBox.getChildren().addAll(sentenceCountTextField);
		
		// Syllable Count
		syllableCountBox = new VBox(5);
		syllableCountTextField = new JFXTextField();
		syllableCountTextField.setPromptText("Syllable Count:");
		syllableCountTextField.setLabelFloat(true);
		syllableCountTextField.setEditable(false);
		syllableCountTextField.setFocusTraversable(false);
		syllableCountBox.getChildren().addAll(syllableCountTextField);
		
		// Flesch Score
		fleschScoreBox = new VBox(5);
		fleschScoreTextField = new JFXTextField();
		fleschScoreTextField.setPromptText("Flesch Score:");
		fleschScoreTextField.setLabelFloat(true);
		fleschScoreTextField.setEditable(false);
		fleschScoreTextField.setFocusTraversable(false);
		fleschScoreBox.getChildren().addAll(fleschScoreTextField);
		
		statusBar.getChildren().addAll(wordCountBox, sentenceCountBox, syllableCountBox, fleschScoreBox);
		
		
		
		/********************************
		*       Property Bindings       *
		********************************/
		wordCountMenuItem.disableProperty().bind(Bindings.isEmpty(textArea.textProperty()));
		sentenceCountMenuItem.disableProperty().bind(Bindings.isEmpty(textArea.textProperty()));
		syllableCountMenuItem.disableProperty().bind(Bindings.isEmpty(textArea.textProperty()));
		
		
		/********************************
		*      Set Main BorderPane      *
		********************************/
		textEditorBorderPane.setTop(menuBar);
		textEditorBorderPane.setCenter(textArea);
		textEditorBorderPane.setBottom(statusBar);
		
		/********************************
		*      Return DrawersStack      *
		********************************/
		
		return drawersStack;
	}
	
	@SuppressWarnings("javadoc")
	public void enableFleschScore() {
		this.fleschScoreMenuItem.setDisable(false);
	}
	
	// Data Count fields - Setters and Getters
	@SuppressWarnings("javadoc")
	public int getWordCount() {
		return Integer.parseInt(this.wordCountTextField.getText());
	}
	@SuppressWarnings("javadoc")
	public void setWordCount(int wordCount) {
		this.wordCountTextField.setText(Integer.toString(wordCount));
	}
	@SuppressWarnings("javadoc")
	public int getSentenceCount() {
		return Integer.parseInt(this.sentenceCountTextField.getText());
	}
	@SuppressWarnings("javadoc")
	public void setSentenceCount(int sentenceCount) {
		this.sentenceCountTextField.setText(Integer.toString(sentenceCount));
	}
	@SuppressWarnings("javadoc")
	public int getSyllableCount() {
		return Integer.parseInt(this.syllableCountTextField.getText());
	}
	@SuppressWarnings("javadoc")
	public void setSyllableCount(int syllableCount) {
		this.syllableCountTextField.setText(Integer.toString(syllableCount));
	}
	@SuppressWarnings("javadoc")
	public double getFleschScore() {
		return Double.parseDouble(this.fleschScoreTextField.getText());
	}
	@SuppressWarnings("javadoc")
	public void setFleschScore(double fleschScore) {
		this.fleschScoreTextField.setText(Double.toString(fleschScore));
	}
	
	// Set main text area
	@SuppressWarnings("javadoc")
	public void clearTextArea() {
		this.textArea.clear();
	}
	/** Sets the primary text area as the string provided */
	public void setTextArea(String text) {
		this.textArea.setText(text);
	}
	/** Appends to the primary text area using the provided string */
	public void appendTextArea(String text) {
		this.textArea.appendText(text);
	}
	
	// Listeners - Setters
	@SuppressWarnings("javadoc")
	public void setLoadFileListener(LoadFileListener loadFileListener) {
		this.loadFileListener = loadFileListener;
	}
	@SuppressWarnings("javadoc")
	public void setWordCountListener(WordCountListener wordCountListener) {
		this.wordCountListener = wordCountListener;
	}
	@SuppressWarnings("javadoc")
	public void setSentenceCountListener(SentenceCountListener sentenceCountListener) { 
		this.sentenceCountListener = sentenceCountListener;
	}
	@SuppressWarnings("javadoc")
	public void setSyllableCountListener(SyllableCountListener syllableCountListener) { 
		this.syllableCountListener = syllableCountListener;
	}
	@SuppressWarnings("javadoc")
	public void setFleschScoreListener(FleschScoreListener fleschScoreListener) {
		this.fleschScoreListener = fleschScoreListener;
	}
	
	// ------- LABEL OBJECT CLASS FOR FLESCH SCORE INFO TABLE -------------
	private class FleschScoreLabel extends RecursiveTreeObject<FleschScoreLabel>{
		
		StringProperty dataRangeValue;
		StringProperty readabilityValue;
		StringProperty educationValue;
		StringProperty comprehensionValue;

		public FleschScoreLabel(String dataVal, String readVal, String eduVal, String compVal) {
			this.dataRangeValue = new SimpleStringProperty(dataVal) ;
			this.readabilityValue = new SimpleStringProperty(readVal);
			this.educationValue = new SimpleStringProperty(eduVal);
			this.comprehensionValue = new SimpleStringProperty(compVal);
		}

	}
}
