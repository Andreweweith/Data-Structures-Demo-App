package view;

import java.util.HashMap;
import java.util.Map;

import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.control.HyperlinkLabel;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.StatusBar;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;
import org.jfree.chart.fx.ChartViewer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Main Window class for the application.
 * <p>
 * This class acts as the primary medium to display
 * either the graph window, spell check window, 
 * or the generator window.
 * 
 * @author Andrew
 */
public class MainWindow {

	private Scene mainWindowScene;
	
	private BorderPane mainWindowBorderPane;
	
	// ** Graph Listeners **
	private GenerateGraphButtonEventListener generateGraphButtonEventListener;
	private GraphResetButtonEventListener graphResetButtonEventListener;
	private SearchGraphButtonEventListener searchGraphButtonEventListener;
	// ** Spell-Check Listeners **
	private SpellCheckWindowButtonEventListener spellCheckWindowButtonEventListener;
	private SpellingSuggestToggleButtonEventListener spellingSuggestToggleButtonEventListener;
	private SpellCheckEntryFieldEventListener spellCheckEntryFieldEventListener;
	// ** Generator Listeners **
	private GeneratorWindowButtonEventListener generatorWindowButtonEventListener;
	private TrainGeneratorButtonEventListener trainGeneratorButtonEventListener;
	private GenerateTextButtonEventListener generateTextButtonEventListener;
	
	private StatusBar graphStatusBar;
	private StatusBar spellCheckStatusBar;
	private StatusBar generatorStatusBar;
	
	private MaskerPane graphMaskerPane;
	private MaskerPane spellCheckMaskerPane;
	private MaskerPane spellCheckSpellingSuggestMaskerPane;
	private MaskerPane generatorMaskerPane;
	
	private StackPane centerStackPane;
	private TabPane windowTabPane;
	private Tab selectionTab;
	private StackPane selectionTabStackPane;
	private Tab graphTab;
	private StackPane graphTabStackPane; // holds the graph window content, and MaskerPane - Initialized by method
	private BorderPane graphTabBorderPane; // holds the stack pane in the center
	private Tab spellCheckTab;
	private StackPane spellCheckTabStackPane; // holds the spell check window content, and MaskerPane
	private BorderPane spellCheckTabBorderPane; // holds the stack pane in the center
	private Tab generatorTab;
	private StackPane generatorTabStackPane; // holds the generator window content, and MaskerPane
	private BorderPane generatorTabBorderPane; // holds the stack pane in the center
	
	// Selection Tab
	private VBox windowSelectionBox;
	// Selection Title Text
	private StackPane selectionTextStackPane;
	private Text selectionText;
	// Buttons for windowButtonVBox 
	private VBox primaryButtonBox;
	private JFXButton graphWindowButton;
	private JFXButton spellCheckWindowButton;
	private JFXButton generatorWindowButton;
	// Secondary Buttons
	private JFXButton exitButton;
	
	// RETURN Buttons - Returns the tab pane to the Selection Tab
	private JFXButton graphWindowReturnButton;
	private JFXButton spellCheckWindowReturnButton;
	private JFXButton generatorWindowReturnButton;
	
	// ** Graph Tab **
	private JFXTabPane graphSearchTabPane;
	private Tab graphSearchContentTab;
	private Tab graphSearchResultsTab;
	private StackPane graphSearchStackPane;
	private HBox graphSearchContentHBox;
	private Text graphSearchText;
	private HBox graphSearchDateFieldHBox;
	private JFXTextField graphSearchDayField;
	private JFXTextField graphSearchMonthField;
	private JFXTextField graphSearchYearField;
	private StackPane graphSearchPaneButtonStackPane;
	private JFXButton graphSearchButton;
	private StackPane graphSearchResultsStackPane;
	private HBox graphSearchResultsHBox;
	private JFXButton graphSearchBackButton;
	private HBox graphSearchResultsTextHBox;
	private Text graphSearchResultsText;
	private Text graphSearchResultsValue;
	private Text graphSearchTimeText;
	private Text graphSearchTimeValue;
	private HBox graphButtonBox;
	private JFXButton generateGraphButton;
	private JFXButton graphResetButton;
	
	// ** Spell-Check Tab **
	private ToggleButton spellingSuggestToggleButton;
	private HBox spellCheckButtonBox;
	private StackPane spellCheckSuggestionsStackPane;
	private Text spellCheckSuggestionsContentText;
	private HBox spellCheckSuggestionsHBox;
	private MasterDetailPane spellCheckMasterDetailPane;
	private StackPane spellCheckEntryStackPane;
	private TextField spellCheckEntryField;
	
	// ** Generator Tab **
	private HBox generatorButtonsBox;
	private HBox generatorTrainAndGenerateBox;
	private Button trainGeneratorButton;
	private JFXButton generateTextButton;
	private TextField generateWordsField;
	private HBox generateTextButtonBox;
	
	// GLYPHS
	private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");
	private Glyph homeGlyphGraph = fontAwesome.create(FontAwesome.Glyph.HOME).size(24).color(Color.LIGHTGREY);
	private Glyph homeGlyphSpellCheck = fontAwesome.create(FontAwesome.Glyph.HOME).size(24).color(Color.LIGHTGREY);
	private Glyph homeGlyphGenerator = fontAwesome.create(FontAwesome.Glyph.HOME).size(24).color(Color.LIGHTGREY);
	private Glyph backArrowGlyph = fontAwesome.create(FontAwesome.Glyph.ARROW_LEFT).size(14).color(Color.LIGHTGREY);
	
	/**
	 * Constructs the main window, which will provide 
	 * functionality allowing the user to decide which 
	 * aspects of the application to view. The primary
	 * components of the window are the tab pane that 
	 * controls the main button selection tab, and the 
	 * tabs containing the graph window or spell check window.
	 */
	public MainWindow() {
		/********************************
		*        Main BorderPane        *
		********************************/
		// Main Window Border Pane - Primary border pane controlling all operations of the application
		mainWindowBorderPane = new BorderPane();
		
		/********************************
		*          Status Bars          *
		********************************/
		// StatusBar provides an organized way of displaying the progress of a 
		// long running thread, while displaying a message at the same time.
		graphStatusBar = new StatusBar();
		graphStatusBar.setText("");
		graphStatusBar.getStyleClass().add("dark-status-bar");
		spellCheckStatusBar = new StatusBar();
		spellCheckStatusBar.setText("");
		spellCheckStatusBar.getStyleClass().add("dark-status-bar");
		generatorStatusBar = new StatusBar();
		generatorStatusBar.setText("");
		generatorStatusBar.getStyleClass().add("dark-status-bar");
		
		/********************************
		*          MaskerPanes          *
		********************************/
		// MaskerPane provides a utility to visually mask controls over
		// certain controls. It must be placed last in a stack pane in
		// order to properly mask them while it is visible. The visibility
		// property is managed through methods in this class, to be called
		// by the controller. After the controller sets the content of a tab,
		// the method will place the content within a stack pane, placing the
		// content followed by the MaskerPane, and binding the property.
		// **Graph**
		graphMaskerPane = new MaskerPane();
		graphMaskerPane.setText("Waiting To Display Graph...");
		graphMaskerPane.setProgress(-1);
		// **SpellCheck**
		spellCheckMaskerPane = new MaskerPane();
		spellCheckMaskerPane.setText("Reading Dictionary Data...");
		spellCheckMaskerPane.setProgress(-1);
		spellCheckSpellingSuggestMaskerPane = new MaskerPane();
		spellCheckSpellingSuggestMaskerPane.setText("Waiting to Enable Spelling Suggest...");
		spellCheckSpellingSuggestMaskerPane.setProgress(-1);
		// **Generator**
		generatorMaskerPane = new MaskerPane();
		generatorMaskerPane.setProgressVisible(true);
		
		/********************************
		*        Window Tab Pane        *
		********************************/
		// Center Stack Pane, placed in the center of the MainWindowBorderPane
		centerStackPane = new StackPane();
		// Tab Pane to control which application window is currently viewed.
		// It is placed within the centerStackPane
		windowTabPane = new TabPane();
		windowTabPane.setTranslateY(-7);
		windowTabPane.getStyleClass().add("animated-tab-pane");
		// Tabs
		selectionTab = new Tab("Selection");
		selectionTabStackPane = new StackPane();
		selectionTabStackPane.setAlignment(Pos.CENTER);
		graphTab = new Tab("DJIA Graph");
		graphTabBorderPane = new BorderPane();
		spellCheckTab = new Tab("Spell Checker");
		spellCheckTabStackPane = new StackPane();
		spellCheckTabBorderPane = new BorderPane();
		generatorTab = new Tab("Markov Generator");
		generatorTabStackPane = new StackPane();
		generatorTabBorderPane = new BorderPane();
		
		windowTabPane.getTabs().addAll(selectionTab, graphTab, spellCheckTab, generatorTab);
		
		/*********************************
		*  Home(Selection) Tab Contents  *
		*********************************/
		// Window Selection Buttons (Selection Tab)
		windowSelectionBox = new VBox(25); // container for buttons
		windowSelectionBox.setAlignment(Pos.CENTER);
		// Selection Text
		selectionTextStackPane = new StackPane();
		selectionTextStackPane.setAlignment(Pos.CENTER);
		selectionTextStackPane.setPadding(new Insets(0, 0, 20, 0));
		selectionText = new Text("Select Application");
		selectionText.getStyleClass().add("nightmode-title-text");
		selectionTextStackPane.getChildren().add(selectionText);
		// Primary Buttons
		primaryButtonBox = new VBox(-2);
		primaryButtonBox.setAlignment(Pos.CENTER);
		graphWindowButton = new JFXButton("Dow Jones Industrial Average Graph");
		graphWindowButton.getStyleClass().add("blue-flat-button");
		spellCheckWindowButton = new JFXButton("Spell Checker Application");
		spellCheckWindowButton.getStyleClass().add("teal-flat-button");
		generatorWindowButton = new JFXButton("Markov Text Generator");
		generatorWindowButton.getStyleClass().add("green-flat-button");
		primaryButtonBox.getChildren().addAll(graphWindowButton, spellCheckWindowButton, generatorWindowButton);
		// Secondary Buttons
		exitButton = new JFXButton("Exit");
		exitButton.getStyleClass().add("nightmode-secondary-button");
		exitButton.setOnAction(e -> {
			System.exit(0);
		});
		// Add Primary and Secondary Buttons to Window Selection Box
		windowSelectionBox.getChildren().addAll(selectionTextStackPane, primaryButtonBox, exitButton);
		
		selectionTabStackPane.getChildren().add(windowSelectionBox);
		
		/********************************
		*   Tab Pane Mapping/Animation  *
		********************************/
		// Place tab contents in a map
		Map<Tab, Node> tabContentMap = new HashMap<>();
		tabContentMap.put(selectionTab, selectionTabStackPane);
		tabContentMap.put(graphTab, graphTabBorderPane);
		tabContentMap.put(spellCheckTab, spellCheckTabBorderPane);
		tabContentMap.put(generatorTab, generatorTabBorderPane);
		
		// Set initial state
		selectionTab.setContent(tabContentMap.get(selectionTab));
		graphTab.setContent(tabContentMap.get(graphTab));
		spellCheckTab.setContent(tabContentMap.get(spellCheckTab));
		generatorTab.setContent(tabContentMap.get(generatorTab));
		
		windowTabPane.getSelectionModel().select(selectionTab);
		
		windowTabPane.getSelectionModel()
			.selectedItemProperty()
			.addListener(
				(obs, oldTab, newTab) -> {
					 oldTab.setContent(null);
                     Node oldContent = tabContentMap.get(oldTab);
                     Node newContent = tabContentMap.get(newTab);

                     newTab.setContent(oldContent);
                     ScaleTransition fadeOut = new ScaleTransition(
                             Duration.seconds(0.5), oldContent);
                     fadeOut.setFromX(1);
                     fadeOut.setFromY(1);
                     fadeOut.setToX(0);
                     fadeOut.setToY(0);

                     ScaleTransition fadeIn = new ScaleTransition(
                             Duration.seconds(0.5), newContent);
                     fadeIn.setFromX(0);
                     fadeIn.setFromY(0);
                     fadeIn.setToX(1);
                     fadeIn.setToY(1);

                     fadeOut.setOnFinished(event -> {
                         newTab.setContent(newContent);
                     });

                     SequentialTransition crossFade = new SequentialTransition(
                             fadeOut, fadeIn);
                     crossFade.play();
		});
		
		// Add Window Tab Pane to Center Stack
		centerStackPane.getChildren().add(windowTabPane);

		
		/********************************
		*       Graph Tab Contents      *
		********************************/
		// Graph WINDOW Button
		graphWindowButton.setOnAction(e -> {
			windowTabPane.getSelectionModel().select(graphTab);
			graphMaskerPane.setProgressVisible(true);
		});
		// Graph Window RETURN Button
		graphWindowReturnButton = new JFXButton("", homeGlyphGraph);
		graphWindowReturnButton.getStyleClass().add("main-menu-button");
		graphWindowReturnButton.setOnAction(e -> {
			graphMaskerPane.setProgressVisible(false);
			windowTabPane.getSelectionModel().select(selectionTab);
		});
		
		// Graph Search Panel -- Tab Pane
		// -- The purpose of this is to provide separation between search query and result
		graphSearchTabPane = new JFXTabPane();
		graphSearchTabPane.getStyleClass().add("animated-tab-pane");
		graphSearchContentTab = new Tab("Graph Search Content");
		graphSearchResultsTab = new Tab("Graph Search Results");
		
		graphSearchTabPane.getTabs().addAll(graphSearchContentTab, graphSearchResultsTab);
		graphSearchTabPane.getSelectionModel().select(graphSearchContentTab);
		
		// Graph Search Panel -- Query Pane
		graphSearchStackPane = new StackPane();
		graphSearchStackPane.getStyleClass().add("dark-status-bar");
		graphSearchContentHBox = new HBox(65);
		graphSearchContentHBox.setPadding(new Insets(5, 0, 10, 0));
		graphSearchContentHBox.setAlignment(Pos.CENTER);
		graphSearchContentHBox.setVisible(false); // set false until graph generated
		
		graphSearchText = new Text("Enter Date to obtain respective value:");
		graphSearchText.getStyleClass().add("important-grey-text");
		
		// Graph Search FIELDS -> Placed within HBox 
		graphSearchDateFieldHBox = new HBox(10);
		graphSearchDateFieldHBox.setAlignment(Pos.CENTER);
		graphSearchDateFieldHBox.setPadding(new Insets(15, 0, 0, 0));
		graphSearchDayField = new JFXTextField();
		graphSearchDayField.setPromptText("Day (##)");
		graphSearchDayField.setLabelFloat(true);
		graphSearchMonthField = new JFXTextField();
		graphSearchMonthField.setPromptText("Month (##)");
		graphSearchMonthField.setLabelFloat(true);
		graphSearchYearField = new JFXTextField();
		graphSearchYearField.setPromptText("Year (####)");
		graphSearchYearField.setLabelFloat(true);
		graphSearchDateFieldHBox.getChildren().addAll(graphSearchDayField,
				graphSearchMonthField, graphSearchYearField);
		
		graphSearchPaneButtonStackPane = new StackPane();
		graphSearchPaneButtonStackPane.setPadding(new Insets(5, 0, 10, 0));
		graphSearchButton = new JFXButton("Search by Date");
		graphSearchButton.getStyleClass().add("blue-flat-button-tiny");
		graphSearchButton.setOnAction(e -> {
			SearchGraphButtonEventObject ev = new SearchGraphButtonEventObject(this, 
					graphSearchDayField.getText(), graphSearchMonthField.getText(), graphSearchYearField.getText());
			if (searchGraphButtonEventListener != null) {
				searchGraphButtonEventListener.searchGraphButtonClicked(ev);
			}
		});
		
		// Graph Search Panel -- Results Pane
		graphSearchResultsStackPane = new StackPane();
		graphSearchResultsHBox = new HBox(325);
		graphSearchResultsHBox.setAlignment(Pos.CENTER_LEFT);
		graphSearchResultsHBox.setPadding(new Insets(0, 0, 10, 10));
		graphSearchBackButton = new JFXButton("   Search Again", backArrowGlyph);
		graphSearchBackButton.getStyleClass().add("main-menu-button");
		graphSearchBackButton.setOnAction(e -> {
			graphSearchTabPane.getSelectionModel().select(graphSearchContentTab);
		});
		graphSearchResultsTextHBox = new HBox(10);
		graphSearchResultsTextHBox.setAlignment(Pos.CENTER);
		graphSearchResultsText = new Text("The value of the entered date is: ");
		graphSearchResultsText.getStyleClass().add("important-grey-text");
		graphSearchResultsValue = new Text();
		graphSearchResultsValue.getStyleClass().add("data-value-text-green");
		graphSearchTimeText = new Text("Retrieved in: ");
		graphSearchTimeText.getStyleClass().add("important-grey-text");
		graphSearchTimeValue = new Text();
		graphSearchTimeValue.getStyleClass().add("data-value-text-blue");
		
		graphSearchResultsTextHBox.getChildren().addAll(graphSearchResultsText, graphSearchResultsValue,
				graphSearchTimeText, graphSearchTimeValue);
		graphSearchResultsHBox.getChildren().addAll(graphSearchBackButton, graphSearchResultsTextHBox);
		graphSearchResultsStackPane.getChildren().add(graphSearchResultsHBox);
		
		// add button to stack pane --- for padding purposes
		graphSearchPaneButtonStackPane.getChildren().add(graphSearchButton);
		// Add text and button to box
		graphSearchContentHBox.getChildren().addAll(graphSearchText, graphSearchDateFieldHBox, graphSearchButton);
		// Add box to stack pane
		graphSearchStackPane.getChildren().add(graphSearchContentHBox);
		

		// Add content to tabs
		graphSearchContentTab.setContent(graphSearchStackPane);
		graphSearchResultsTab.setContent(graphSearchResultsStackPane);
		
		
		// Graph Tab Management Buttons
		graphButtonBox = new HBox(10);
		graphButtonBox.setAlignment(Pos.CENTER);
		graphButtonBox.setPadding(new Insets(0, 0, 0, 10));
		
		// GENERATE Graph Button
		generateGraphButton = new JFXButton("Generate Dow Jones Graph");
		generateGraphButton.getStyleClass().add("green-flat-button-tiny");
		generateGraphButton.setOnAction(e -> {
			GenerateGraphButtonEventObject ev = new GenerateGraphButtonEventObject(this);
			if (generateGraphButtonEventListener != null) {
				generateGraphButtonEventListener.generateGraphButtonClicked(ev);
			}
		});
		// RESET Graph Button
		graphResetButton = new JFXButton("Reset Graph");
		graphResetButton.getStyleClass().add("blue-flat-button-tiny");
		graphResetButton.setOnAction(e -> {
			GraphResetButtonEventObject ev = new GraphResetButtonEventObject(this);
			if(graphResetButtonEventListener != null) {
				graphResetButtonEventListener.graphResetButtonClicked(ev);
			}
		});
		
		// *** ONLY add the RETURN and GENERATE buttons, the RESET button 
		// is added below in the setGraphTabContent method after removing GENERATE
		graphButtonBox.getChildren().add(graphWindowReturnButton);
		graphButtonBox.getChildren().add(generateGraphButton);
		// Add button box to status bar
		graphStatusBar.getLeftItems().add(graphButtonBox);
		
		// Set contents of the tab's BorderPane
		graphTabBorderPane.setTop(graphSearchTabPane);
		graphTabBorderPane.setCenter(graphMaskerPane);
		graphTabBorderPane.setBottom(graphStatusBar);
		
		// Set BorderPane as the content of the Tab
		graphTab.setContent(graphTabBorderPane);
		
		
		/********************************
		*   Spell-Check Tab Contents    *
		********************************/
		// SpellCheck WINDOW Button
		spellCheckWindowButton.setOnAction(e -> {
			SpellCheckWindowButtonEventObject ev = new SpellCheckWindowButtonEventObject(this);
			if(spellCheckWindowButtonEventListener != null) {
				spellCheckWindowButtonEventListener.spellCheckWindowButtonClicked(ev);
				
				windowTabPane.getSelectionModel().select(spellCheckTab);
				spellCheckMaskerPane.setProgressVisible(true);
			}
		});
		// SpellCheck Window RETURN Button
		spellCheckWindowReturnButton = new JFXButton("", homeGlyphSpellCheck);
		spellCheckWindowReturnButton.getStyleClass().add("main-menu-button");
		spellCheckWindowReturnButton.setOnAction(e -> {
			windowTabPane.getSelectionModel().select(selectionTab);
		});
		
		// SpellingSuggest Toggle Button --- Loads Spelling Suggest, Disables after toggled ON
		spellingSuggestToggleButton = new ToggleButton("Enable Spelling Suggest");
		spellingSuggestToggleButton.setOnAction(e -> {
			if(spellingSuggestToggleButton.isSelected()) {
				SpellingSuggestToggleButtonEventObject ev = new SpellingSuggestToggleButtonEventObject(this);
				if(spellingSuggestToggleButtonEventListener != null) {
					spellingSuggestToggleButtonEventListener.spellingSuggestToggleButtonClicked(ev);
					
					spellingSuggestToggleButton.setDisable(true);
					spellingSuggestToggleButton.setText("Spelling Suggest Enabled!");
				}
			}
		});

		// Holds Buttons for the Status Bar -- Return, and SpellingSuggest Buttons
		spellCheckButtonBox = new HBox(15);
		spellCheckButtonBox.setAlignment(Pos.CENTER);
		spellCheckButtonBox.setPadding(new Insets(0, 0, 0, 10));
		spellCheckButtonBox.getChildren().add(spellCheckWindowReturnButton);
		spellCheckButtonBox.getChildren().add(spellingSuggestToggleButton);
		
		spellCheckStatusBar.getLeftItems().add(spellCheckButtonBox);
		
		// * PRIMARY FUNCTION OF THIS WINDOW *
		// This textfield is a custom textfield, it contains a button to clear its contents
		// As well as changing styleclasses according to the text that is being entered
		spellCheckEntryField = TextFields.createClearableTextField();
		spellCheckEntryField.setPromptText("Check for spelling...");
		spellCheckEntryField.getStyleClass().add("empty-dark-text-field");
		// Add listener to the spell check text field, this will limit the total number of characters allowed
		spellCheckEntryField.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                	int LIMIT = 22;
                    if (spellCheckEntryField.getText().length() >= LIMIT) {
                    	spellCheckEntryField.setText(spellCheckEntryField.getText().substring(0, LIMIT));
                    }
                }
            }
        });
		
		// Set contents of the window's BorderPane. This will hold the main contents of the application
		spellCheckTabBorderPane.setTop(spellCheckStatusBar);
		spellCheckTabBorderPane.setCenter(spellCheckMaskerPane);
		
		// Set the tabs content as the BorderPane for the tab
		spellCheckTab.setContent(spellCheckTabBorderPane);
		
		
		/********************************
		*    Generator Tab Contents     *
		********************************/
		// Generator WINDOW Button
		generatorWindowButton.setOnAction(e -> {
			GeneratorWindowButtonEventObject ev = new GeneratorWindowButtonEventObject(this);
			if(generatorWindowButtonEventListener != null) {
				generatorWindowButtonEventListener.generatorWindowButtonClicked(ev);
				
				windowTabPane.getSelectionModel().select(generatorTab);
			}
		});
		// Generator Window RETURN Button
		generatorWindowReturnButton = new JFXButton("", homeGlyphGenerator);
		generatorWindowReturnButton.getStyleClass().add("main-menu-button");
		generatorWindowReturnButton.setOnAction(e -> {
			windowTabPane.getSelectionModel().select(selectionTab);
		});
		// HBox for ALL Generator Buttons (Home, Generate/Train Box)
		generatorButtonsBox = new HBox(10);
		generatorButtonsBox.setAlignment(Pos.CENTER);
		generatorButtonsBox.setPadding(new Insets(0, 0, 0, 10));
		// HBox for GENERATE and TRAIN Buttons -- added later through methods
		generatorTrainAndGenerateBox = new HBox(10);
		generatorTrainAndGenerateBox.setAlignment(Pos.CENTER);
		
		// GENERATE and TRAIN Buttons --- Added to their button box through later method calling
		// Train Button
		trainGeneratorButton = new Button("Train Generator");
		trainGeneratorButton.getStyleClass().add("toggle-button");
		trainGeneratorButton.setOnAction(e -> {
			TrainGeneratorButtonEventObject ev = new TrainGeneratorButtonEventObject(this);
			if(trainGeneratorButtonEventListener != null) {
				trainGeneratorButtonEventListener.trainGeneratorButtonClicked(ev);
				
				trainGeneratorButton.setDisable(true);
			}
		});
		// Generate Button and Number of Words Field
		generateWordsField = new TextField();
		generateWordsField.setPromptText("Num of Words to Generate...");
		generateWordsField.getStyleClass().add("empty-dark-text-field-small");
		
		generateTextButton = new JFXButton("Generate Text");
		generateTextButton.setOnAction(e -> {
			try {
				if (!generateWordsField.getText().isEmpty()) {
					int numOfWords = Integer.parseInt(generateWordsField.getText());
					GenerateTextButtonEventObject ev = new GenerateTextButtonEventObject(this, numOfWords);
					if (generateTextButtonEventListener != null) {
						generateTextButtonEventListener.generateTextButtonClicked(ev);
						
						if(generateWordsField.getStyleClass().contains("incorrect-text-field-small")) {
							generateWordsField.getStyleClass().remove("incorrect-text-field-small");
						}
					} 
				}
				else {
					generateWordsField.getStyleClass().add("incorrect-text-field-small");
				}
			} catch (Exception e1) {
				generateWordsField.getStyleClass().add("incorrect-text-field-small");
			}
		});
		generateTextButton.getStyleClass().add("blue-flat-button-tiny");
		// Box to holds the GENERATE BUTTON and the NUMBEROFWORDS FIELD
		generateTextButtonBox = new HBox(20);
		generateTextButtonBox.setAlignment(Pos.CENTER);
		generateTextButtonBox.getChildren().addAll(generateTextButton, generateWordsField);
		
		
		// Add home button and generate/train box to main button box
		generatorButtonsBox.getChildren().addAll(generatorWindowReturnButton,
				generatorTrainAndGenerateBox);
		
		// Add box to status bar
		generatorStatusBar.getLeftItems().add(generatorButtonsBox);

		// Set contents of the tab's BorderPane
		generatorTabBorderPane.setCenter(generatorMaskerPane);
		generatorTabBorderPane.setBottom(generatorStatusBar);
		
		// Set BorderPane as the content of the Tab	
		generatorTab.setContent(generatorTabBorderPane);
		
		
		
		/********************************
		*  Manage BorderPane and Scene  *
		********************************/
		// Sets the center of the MainWindowBorderPane as the stack containing 
		// Window Tab Pane
		mainWindowBorderPane.setCenter(centerStackPane);
		
		// Set the scene's contents as the MainWindowBorderPane
		mainWindowScene = new Scene(mainWindowBorderPane, 1400, 800);
		
		/********************************
		*        CSS / StyleSheets      *
		********************************/
		
		// CSS STYLE SHEET - MAIN WINDOW SCENE
		String jfoenixComponents = 
				getClass().getResource("jfoenixComponents.css").toExternalForm();
		String jfoenixDesign = 
				getClass().getResource("jfoenixDesign.css").toExternalForm();
						
		mainWindowScene.getStylesheets().addAll(jfoenixComponents, jfoenixDesign);
	}
	
	/********************************
	*  TAB CONTENT SETTER METHODS   *
	********************************/
	/**
	 * Allows the facade class to set the content of the tabs for this window.
	 * It is expected that a pane be provided from the GraphWindow.
	 * 
	 * @param graphPane
	 */
	public void setGraphTabContent(ChartViewer graphPane) {
		graphTabStackPane = new StackPane();
		graphTabStackPane.getChildren().addAll(graphPane, graphMaskerPane);
		
		graphTabBorderPane.setCenter(graphTabStackPane);
		
		if (graphButtonBox.getChildren().contains(generateGraphButton)) {
			// Remove only the GENERATE button, replace with the RESET button
			graphButtonBox.getChildren().remove(generateGraphButton);
			graphButtonBox.getChildren().add(graphResetButton);
			// Add SEARCH PANE button STACK PANE (not just button -- for padding) to the right side
			graphStatusBar.getRightItems().add(graphSearchPaneButtonStackPane);
		}
		
		// Make the search bar content visible
		graphSearchContentHBox.setVisible(true);
	}
	/**
	 * Allows the facade class to set the content of the tabs for this window.
	 * 
	 * @param spellCheckPane
	 */
	public void setSpellCheckTabContent() {
		spellCheckTabStackPane = new StackPane();
		
		spellCheckMasterDetailPane = new MasterDetailPane();
		spellCheckMasterDetailPane.setAnimated(true);
		spellCheckMasterDetailPane.setDetailSide(Side.BOTTOM);
		
		spellCheckEntryStackPane = new StackPane();
		spellCheckEntryStackPane.setAlignment(Pos.CENTER);
		spellCheckEntryStackPane.getChildren().add(spellCheckEntryField);
		
		
		spellCheckEntryField.textProperty().addListener((observable, oldValue, newValue) -> {
			SpellCheckEntryFieldEventObject ev = new SpellCheckEntryFieldEventObject(this, newValue);
			if(spellCheckEntryFieldEventListener != null) {
				spellCheckEntryFieldEventListener.spellCheckEntryFieldChanged(ev);
			}
		});
		
		spellCheckMasterDetailPane.setMasterNode(spellCheckEntryStackPane);
		spellCheckMasterDetailPane.setShowDetailNode(false);
		
		spellCheckSpellingSuggestMaskerPane.setVisible(true);
		
		spellCheckTabStackPane.getChildren().addAll(spellCheckMasterDetailPane, spellCheckMaskerPane,
				spellCheckSpellingSuggestMaskerPane);
		
		spellCheckTabBorderPane.setCenter(spellCheckTabStackPane);
		
		
		// ***** NOT VISIBLE UNTIL THE SPELLCHECK WINDOW VISIBLE METHOD IS CALLED *****
		spellCheckMasterDetailPane.getMasterNode().setVisible(false);
	}
	/**
	 * Given a suggestion, creates a VBox of Text and a HyperlinkLabel 
	 * that when pressed, will replace the misspelled word inside of the 
	 * spellCheckEntryField with the correct (suggested) word.
	 * 
	 * @param suggestion
	 */
	public void setSpellCheckDetailContent(String suggestion) {
		spellCheckSuggestionsStackPane = new StackPane();
		spellCheckSuggestionsStackPane.setAlignment(Pos.CENTER);
		spellCheckSuggestionsHBox = new HBox(20);
		spellCheckSuggestionsHBox.setAlignment(Pos.CENTER);
		
		// HYPERLINKLABEL -- Acts as button to replace text with suggested text
		StackPane linkLabelStackPane = new StackPane();
		linkLabelStackPane.setAlignment(Pos.CENTER);
		linkLabelStackPane.setPadding(new Insets(18, 0, 0, 0));
		HyperlinkLabel linkLabel = new HyperlinkLabel();
		linkLabel.getStyleClass().add("custom-hyper-link-label");
		linkLabel.setText("[" + suggestion + "] ?");
		linkLabel.setOnAction(e -> {
			Hyperlink link = (Hyperlink) e.getSource();
			final String str = link == null ? "" : link.getText();
				
			// After clicking suggestion, remove suggestion pane > set correct spelling > change text
			spellCheckMasterDetailPane.setShowDetailNode(false);
			setSpellCheckCorrectSpelling();
			spellCheckEntryField.setText(str);
		});
		linkLabelStackPane.getChildren().add(linkLabel);

		spellCheckSuggestionsContentText = new Text("Did you mean...");
		spellCheckSuggestionsContentText.getStyleClass().add("large-grey-text");
		spellCheckSuggestionsHBox.getChildren().addAll(spellCheckSuggestionsContentText, linkLabelStackPane);
		
		spellCheckSuggestionsStackPane.getChildren().add(spellCheckSuggestionsHBox);
		
		spellCheckMasterDetailPane.setDetailNode(spellCheckSuggestionsStackPane);
		spellCheckMasterDetailPane.setDividerPosition(0.9);
		spellCheckMasterDetailPane.setShowDetailNode(true);
	}
	/**
	 * Allows the facade class to set the content of the tabs for this window.
	 * It is expected that a pane be provided from the GeneratorWindow.
	 * 
	 * @param generatorPane
	 */
	public void setGeneratorTabContent(JFXDrawersStack textEditorWindow) {
		generatorTabStackPane = new StackPane();
		generatorTabStackPane.setAlignment(Pos.CENTER);
		generatorTabStackPane.getChildren().addAll(textEditorWindow, generatorMaskerPane);
		
		if (generatorMaskerPane.isVisible()) {
			generatorMaskerPane.setVisible(false);
		}
		generatorTabBorderPane.setCenter(generatorTabStackPane);
	}
	
	/********************************
	*  STATUS BAR BINDING METHODS   *
	********************************/
	/**
	 * Allows the controller to bind the progressProperty of the graph status bar's
	 * progress bar to the progress property passed from the controller. This will 
	 * usually be the progress of a long running thread.
	 * <p>
	 * Also provides a property to bind regarding if the service is running or not.
	 * <p>
	 * *Note* This method must be called before the setTabContent method.
	 * 
	 * @param progressProperty
	 * @param runningProperty
	 */
	public void setGraphStatusBarProgress(ReadOnlyDoubleProperty progressProperty, 
			ReadOnlyBooleanProperty runningProperty, ReadOnlyStringProperty stringProperty) {
		this.graphStatusBar.progressProperty().bind(progressProperty);
		this.graphStatusBar.textProperty().bind(stringProperty);
		this.graphMaskerPane.visibleProperty().bind(runningProperty);
	}
	/**
	 * Allows the controller to bind the progressProperty of the spell check status bar's
	 * progress bar to the progress property passed from the controller. This will 
	 * usually be the progress of a long running thread.
	 * <p>
	 * Also provides a property to bind regarding if the service is running or not.
	 * <p>
	 * *Note* This method must be called before the setTabContent method.
	 * 
	 * @param progressProperty
	 * @param runningProperty
	 */
	public void setSpellCheckStatusBarProgress(ReadOnlyDoubleProperty progressProperty, 
			ReadOnlyBooleanProperty runningProperty, ReadOnlyStringProperty stringProperty) {
		this.spellCheckStatusBar.progressProperty().bind(progressProperty);
		this.spellCheckStatusBar.textProperty().bind(stringProperty);
		this.spellCheckMaskerPane.visibleProperty().bind(runningProperty);
		this.spellCheckMaskerPane.progressProperty().bind(progressProperty);
	}
	/** Same as other spell check method, except handles a different MaskerPane, and provides text binding for it */
	public void setSpellCheckStatusBarProgressSpellingSuggest(ReadOnlyDoubleProperty progressProperty, 
			ReadOnlyBooleanProperty runningProperty, ReadOnlyStringProperty stringProperty) {
		this.spellCheckStatusBar.progressProperty().bind(progressProperty);
		this.spellCheckStatusBar.textProperty().bind(stringProperty);
		this.spellCheckSpellingSuggestMaskerPane.visibleProperty().bind(runningProperty);
		this.spellCheckSpellingSuggestMaskerPane.progressProperty().bind(progressProperty);
		this.spellCheckSpellingSuggestMaskerPane.textProperty().bind(stringProperty);
	}
	/**
	 * Allows the controller to bind the progressProperty of the generator status bar's
	 * progress bar to the progress property passed from the controller. This will 
	 * usually be the progress of a long running thread.
	 * <p>
	 * Also provides a property to bind regarding if the service is running or not.
	 * <p>
	 * *Note* This method must be called before the setTabContent method.
	 * 
	 * @param progressProperty
	 * @param runningProperty
	 */
	public void setGeneratorStatusBarProgress(ReadOnlyDoubleProperty progressProperty, 
			ReadOnlyBooleanProperty runningProperty, ReadOnlyStringProperty stringProperty) {
		this.generatorStatusBar.progressProperty().bind(progressProperty);
		this.generatorStatusBar.textProperty().bind(stringProperty);
		this.generatorMaskerPane.visibleProperty().bind(runningProperty);
		this.generatorMaskerPane.progressProperty().bind(progressProperty);
		this.generatorMaskerPane.textProperty().bind(stringProperty);
		this.generateTextButton.disableProperty().bind(runningProperty);
		this.generateWordsField.disableProperty().bind(runningProperty);
	}
	
	/**************************************
	*  WINDOW MANAGEMENT/CONTROL METHODS  *
	**************************************/
	// Miscellaneous Graph Search Control Methods
	/**
	 * Allows the controller to set the results of the binary search, 
	 * by specifying the value retrieved, and the time to retrieve it.
	 * <p>
	 * It also switches the tab pane to the proper tab, and if incorrect
	 * input has been entered previously, will correct the style class to
	 * show that a valid input has been entered.
	 * 
	 * @param targetValue
	 * @param runTime
	 */
	public void setGraphSearchResults(double targetValue, long runTime) {
		this.graphSearchResultsValue.setText(Double.toString(targetValue) + ";      ");
		this.graphSearchTimeValue.setText(runTime + " Millisecond(s)");
		this.graphSearchTabPane.getSelectionModel().select(graphSearchResultsTab);
		
		if (graphSearchDayField.getStyleClass().contains("error-text-field")) {
			graphSearchDayField.getStyleClass().remove("error-text-field");
			graphSearchMonthField.getStyleClass().remove("error-text-field");
			graphSearchYearField.getStyleClass().remove("error-text-field");
		}
	}
	/** Adds a style class to the search fields portraying incorrect input */
	public void setGraphSearchResultsError() {
		if (!graphSearchDayField.getStyleClass().contains("error-text-field")) {
			graphSearchDayField.getStyleClass().add("error-text-field");
			graphSearchMonthField.getStyleClass().add("error-text-field");
			graphSearchYearField.getStyleClass().add("error-text-field");
		}
	}
	
	// Miscellaneous Spell Checker Control Methods
	/**
	 * Handles the exception in the controller, that will determine if a word
	 * has been found in the dictionary by styling it with a green border glow.
	 * Otherwise, if it is found to be incorrect, it will have a red border glow.
	 */
	public void setSpellCheckCorrectSpelling() {
		if (spellCheckEntryField.getStyleClass().contains("incorrect-text-field")) {
			spellCheckEntryField.getStyleClass().remove("incorrect-text-field");
		}
		spellCheckEntryField.getStyleClass().add("correct-text-field");
	}
	/**
	 * Handles the exception in the controller, that will determine if a word
	 * has been found in the dictionary by styling it with a green border glow.
	 * Otherwise, if it is found to be incorrect, it will have a red border glow.
	 */
	public void setSpellCheckIncorrectSpelling() {
		if (spellCheckEntryField.getStyleClass().contains("correct-text-field")) {
			spellCheckEntryField.getStyleClass().remove("correct-text-field");
		}
		spellCheckEntryField.getStyleClass().add("incorrect-text-field");
	}
	/** Returns text field to original style */
	public void setSpellCheckEmptySpelling() {
		if (spellCheckEntryField.getStyleClass().contains("correct-text-field")) {
			spellCheckEntryField.getStyleClass().remove("correct-text-field");
		}
		if (spellCheckEntryField.getStyleClass().contains("incorrect-text-field")) {
			spellCheckEntryField.getStyleClass().remove("incorrect-text-field");
		}
	}
	/** Allow the window contents to be visible */
	public void setSpellCheckWindowVisible() {
		spellCheckMasterDetailPane.getMasterNode().setVisible(true);
	}
	
	// Miscellaneous Generator Control Methods
	/** Re-enables the trainer if it is disabled; attempts to add button to box if it is not already */
	public void enableTrainerButton() {
		if (!generatorTrainAndGenerateBox.getChildren().contains(trainGeneratorButton)) {
			generatorTrainAndGenerateBox.getChildren().add(trainGeneratorButton);
		}
		
		trainGeneratorButton.setDisable(false);
	}
	/** Enables the use of the GeneratorButton, and the GeneratorWordsField for text generation */
	public void enableGeneratorButton() {
		if (!generatorTrainAndGenerateBox.getChildren().contains(generateTextButtonBox)) {
			generatorTrainAndGenerateBox.getChildren().add(generateTextButtonBox);
		}
	}
	/** Disables the Generator functions, as a new file has been initialized and must be trained */
	public void disableGeneratorButton() {
		if (generatorTrainAndGenerateBox.getChildren().contains(generateTextButtonBox)) {
			generatorTrainAndGenerateBox.getChildren().remove(generateTextButtonBox);
		}
	}
	
	/********************************
	*    MAIN WINDOW SCENE GETTER   *
	********************************/
	// GET MAIN WINDOW SCENE METHOD
	/** @return mainWindowScene */
	public Scene getMainWindowScene() {
		return mainWindowScene;
	}
	
	
	//********************************
	//*    EVENT LISTENER SETTERS    *
	//********************************
	
	
	// ****** Graph Listeners ******
	@SuppressWarnings("javadoc")
	public void setGenerateGraphButtonEventListener(GenerateGraphButtonEventListener listener) {
		this.generateGraphButtonEventListener = listener;
	}
	@SuppressWarnings("javadoc")
	public void setGraphResetButtonEventListener(GraphResetButtonEventListener listener) {
		this.graphResetButtonEventListener = listener;
	}
	@SuppressWarnings("javadoc")
	public void setSearchGraphButtonEventListener(SearchGraphButtonEventListener listener) {
		this.searchGraphButtonEventListener = listener;
	}
	
	// ****** Spell-Check Listeners ******
	@SuppressWarnings("javadoc")
	public void setSpellCheckWindowButtonEventListner(SpellCheckWindowButtonEventListener listener) {
		this.spellCheckWindowButtonEventListener = listener;
	}
	@SuppressWarnings("javadoc")
	public void setSpellingSuggestToggleButtonEventListener(SpellingSuggestToggleButtonEventListener listener) {
		this.spellingSuggestToggleButtonEventListener = listener;
	}
	@SuppressWarnings("javadoc")
	public void setSpellCheckEntryFieldEventListener(SpellCheckEntryFieldEventListener listener) {
		this.spellCheckEntryFieldEventListener = listener;
	}
	
	// ****** Generator Listeners ******
	@SuppressWarnings("javadoc")
	public void setGeneratorWindowButtonEventListener(GeneratorWindowButtonEventListener listener) {
		this.generatorWindowButtonEventListener = listener;
	}
	@SuppressWarnings("javadoc")
	public void setTrainGeneratorButtonEventListener(TrainGeneratorButtonEventListener listener) {
		this.trainGeneratorButtonEventListener = listener;
	}
	@SuppressWarnings("javadoc")
	public void setGenerateTextButtonEventListener(GenerateTextButtonEventListener listener) {
		this.generateTextButtonEventListener = listener;
	}
	
}
