package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jfree.chart.fx.ChartViewer;

import com.jfoenix.controls.JFXDrawersStack;

import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import model.*;
import view.*;

/**
 * Controller class for the application.
 * <p>
 * This controller handles events that are passed to it from the 
 * MainWindow through use of the WindowFacade facade class.
 * It also manages the processing of data to be passed to the view,
 * through the model classes. The model classes are Tasks, with 
 * corresponding Service classes to assist in the management of 
 * the multithreaded operations - such as binding progress properties
 * of the tasks to progress indicators in the view, or ensuring that
 * the data accessed by each thread does not overlap and become corrupted.
 * 
 * @author Andrew
 */
public class Controller {

	private DowJonesDataService djDataService;
	private DictionaryLoaderService dictionaryLoaderService;
	private SpellingSuggestLoaderService spellingSuggestLoaderService;
	private MarkovTrainerService markovTrainerService;
	private MarkovGeneratorService markovGeneratorService;
	private WindowFacade windowFacade;
	
	private Helper textAnalyzerOneLoop;
	
	// Graph Data 
	private ChartViewer graphViewer;
	private ObservableList<DowJonesData> djDataList;
	private ArrayList<DowJonesData> shuffledDowJonesDataList;
	private BST dataBinarySearchTree;
	private double targetValue;
	private Long totalRunTime;
	
	// Spell-Check Data
	private MyHashMap<String, String> dictionary;
	private MyHashMap<String, Integer> probabilityDB;
	private String suggestion;
	
	// Generator Data
	// ** Analyzer Data **
	private String editorContentData;
	private Integer wordCount;
	private Integer sentenceCount;
	private Integer syllableCount;
	// ** Generator Data **
	private MyLinkedList<ListNode> wordList;
	private String starterString;
	
	/**
	 * Controller object constructor to start the application properly
	 * and control it further after it has launched.
	 */
	public Controller(DowJonesDataService dowJonesDataService, 
			DictionaryLoaderService dictLoaderService,
			SpellingSuggestLoaderService spellSuggestLoaderService,
			WindowFacade facade) {
		this.djDataService = dowJonesDataService;
		this.dictionaryLoaderService = dictLoaderService;
		this.spellingSuggestLoaderService = spellSuggestLoaderService;
		this.windowFacade = facade;
		
		// ****************** GRAPH APPLICATION LISTENERS ********************* //
		// GENERATE Graph Button Listener
		this.windowFacade.getMainWindow().setGenerateGraphButtonEventListener(new GenerateGraphButtonEventListener() {
			@Override
			public void generateGraphButtonClicked(GenerateGraphButtonEventObject ev) {
				// Bind properties for Graph Status Bar
				windowFacade.getMainWindow().setGraphStatusBarProgress(
					djDataService.progressProperty(), 
					djDataService.runningProperty(),
					djDataService.messageProperty());
				// Set on service succeeded
				djDataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					@SuppressWarnings("unchecked")
					@Override
					public void handle(WorkerStateEvent t) {
						// Definitely checked this.
						djDataList = (ObservableList<DowJonesData>) t.getSource().getValue();
						
						graphViewer = windowFacade.generateGraphViewer(djDataList);
						
						windowFacade.setMainWindowGraphTabContent(graphViewer);
						
						//******* FOR BINARY SEARCH TREE *******//
						// Store datalist as arraylist of data in the new shuffed list variable
						shuffledDowJonesDataList = new ArrayList<DowJonesData>(djDataList);
						// Shuffle the list
						Collections.shuffle(shuffledDowJonesDataList);
						// For every item in the shuffled list, put date/value pair into BST
						dataBinarySearchTree = new BST();
						for (DowJonesData items : shuffledDowJonesDataList) {
							dataBinarySearchTree.put(items.getDate(), items.getValue());
						}
					}
				});
				// Start service
				djDataService.start();
			}
		});
		// Graph RESET Button Listener
		this.windowFacade.getMainWindow().setGraphResetButtonEventListener(new GraphResetButtonEventListener() {
			@Override
			public void graphResetButtonClicked(GraphResetButtonEventObject ev) {
				djDataService.restart();
				
				ChartViewer newGraphViewer = windowFacade.generateGraphViewer(djDataList);
				
				windowFacade.setMainWindowGraphTabContent(newGraphViewer);
			}
		});
		// SEARCH Graph Button Listener
		this.windowFacade.getMainWindow().setSearchGraphButtonEventListener(new SearchGraphButtonEventListener() {
			@Override
			public void searchGraphButtonClicked(SearchGraphButtonEventObject ev) {
				try {
					int day = Integer.parseInt(ev.getDay());
					int month = Integer.parseInt(ev.getMonth());
					int year = Integer.parseInt(ev.getYear());
					GregorianCalendar calendar = new GregorianCalendar(year, (month - 1), day);
					Date targetDate = calendar.getTime();
						
					DowJonesDataSearchTask searchTask = new DowJonesDataSearchTask(
							dataBinarySearchTree, targetDate);
						
					searchTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						@Override
						public void handle(WorkerStateEvent t) {
							try {
								targetValue = (double) t.getSource().getValue();
									
								getWindowFacade().getMainWindow().setGraphSearchResults(targetValue, totalRunTime);
							} catch (Exception e) {
								getWindowFacade().getMainWindow().setGraphSearchResultsError();
							}
						}
					});
						
					ExecutorService executor = Executors.newFixedThreadPool(1);
						
					Long startTime = System.currentTimeMillis(); // Start time of task
						
					// FIND TARGET VALUE 
					executor.execute(searchTask); // Execute task
					executor.shutdown(); // Shut down executor
					while(!executor.isTerminated()) {} // Wait for task to finish
						
					Long endTime = System.currentTimeMillis(); // End time of task
						
					totalRunTime = endTime - startTime; // Total Run Time of task
				} catch (NumberFormatException e) {
					getWindowFacade().getMainWindow().setGraphSearchResultsError();
				}
			}
		});
		
		// ****************** SPELL CHECKER APPLICATION LISTENERS ********************* //
		// SpellCheckWindowButton Listener
		this.windowFacade.getMainWindow().setSpellCheckWindowButtonEventListner(new SpellCheckWindowButtonEventListener() {
			@Override
			public void spellCheckWindowButtonClicked(SpellCheckWindowButtonEventObject ev) {
				// Bind properties for Spell-Check Status Bar
				windowFacade.getMainWindow().setSpellCheckStatusBarProgress(
					dictionaryLoaderService.progressProperty(), 
					dictionaryLoaderService.runningProperty(),
					dictionaryLoaderService.messageProperty());
				// Set on service succeeded
				dictionaryLoaderService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					@SuppressWarnings("unchecked")
					@Override
					public void handle(WorkerStateEvent t) {
						// Definitely checked this.
						dictionary = (MyHashMap<String, String>) t.getSource().getValue();

						windowFacade.setMainWindowSpellCheckTabContent();
					}
				});
				if (dictionary == null) {
					dictionaryLoaderService.start();
				}
			}
		});
		// SpellingSuggestToggleButton Listener
		this.windowFacade.getMainWindow().setSpellingSuggestToggleButtonEventListener(new SpellingSuggestToggleButtonEventListener() {
			@Override
			public void spellingSuggestToggleButtonClicked(SpellingSuggestToggleButtonEventObject ev) {
				windowFacade.getMainWindow().setSpellCheckStatusBarProgressSpellingSuggest(
						spellingSuggestLoaderService.progressProperty(), 
						spellingSuggestLoaderService.runningProperty(),
						spellingSuggestLoaderService.messageProperty());
				spellingSuggestLoaderService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					@SuppressWarnings("unchecked")
					@Override
					public void handle(WorkerStateEvent t) {
						// Definitely checked this.
						probabilityDB = (MyHashMap<String, Integer>) t.getSource().getValue();
						
						getWindowFacade().getMainWindow().setSpellCheckWindowVisible();
					}
				});
				
				// ONLY START SERVICE IF DATABASE IS EMPTY
				if (probabilityDB == null) {
					spellingSuggestLoaderService.start();
				}
			}
		});
		// SpellCheckEntryField Listener
		this.windowFacade.getMainWindow().setSpellCheckEntryFieldEventListener(new SpellCheckEntryFieldEventListener() {
			@Override
			public void spellCheckEntryFieldChanged(SpellCheckEntryFieldEventObject ev) {
				String newTextEntry = ev.getNewValue();
				
				DictionaryCheckerTask checkerTask = new DictionaryCheckerTask(dictionary,
						probabilityDB, newTextEntry);
				
				// Assume empty to begin?
				getWindowFacade().getMainWindow().setSpellCheckEmptySpelling();
				
				checkerTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					@Override
					public void handle(WorkerStateEvent t) {
						
						suggestion = (String) t.getSource().getValue();
							
						// INCORRECT SPELLING
						if(suggestion != null) {
							getWindowFacade().getMainWindow().setSpellCheckDetailContent(suggestion);
							getWindowFacade().getMainWindow().setSpellCheckIncorrectSpelling();
						}
							
						// CORRECT SPELLING
						if(suggestion == null) {
							getWindowFacade().getMainWindow().setSpellCheckCorrectSpelling();
						}
					}
				});
				
				ExecutorService executor = Executors.newFixedThreadPool(1);
				
				// Check if spelling is correct 
				executor.execute(checkerTask); // Execute task
				executor.shutdown(); // Shut down executor
				while(!executor.isTerminated()) {} // Wait for task to finish
			}
		});
		
		// ****************** GENERATOR / TEXT-EDITOR APPLICATION LISTENERS ********************* //
		// Generator Window Button Listener ---- Places a new TextEditorDrawersStack in the tab contents
		this.windowFacade.getMainWindow().setGeneratorWindowButtonEventListener(new GeneratorWindowButtonEventListener() {
			@Override
			public void generatorWindowButtonClicked(GeneratorWindowButtonEventObject ev) {
				JFXDrawersStack textEditorDrawersStack = getWindowFacade().generateTextEditorWindow();
				
				getWindowFacade().getMainWindow().setGeneratorTabContent(textEditorDrawersStack);
			}
		});
		// LoadFile Listener
		this.windowFacade.getTextEditorWindow().setLoadFileListener(new LoadFileListener() {
			@Override
			public void loadFileClicked(LoadFileEventObject ev) {
				getWindowFacade().getTextEditorWindow().setTextArea(editorContentData = getDisplayText(
						ev.getFileData()));
				
				// After file is loaded, enable the trainer
				windowFacade.getMainWindow().enableTrainerButton();
				windowFacade.getMainWindow().disableGeneratorButton();
			}
		});
		// WordCount Listener
		this.windowFacade.getTextEditorWindow().setWordCountListener(new WordCountListener() {
			@Override
			public void wordCountClicked(WordCountEventObject ev) {
				setHelper(ev.getEditorContentData());
				getWindowFacade().getTextEditorWindow().setWordCount(
						(wordCount = getHelper().getNumberOfWords()));
				if(wordCount != null && sentenceCount != null && syllableCount != null) {
					getWindowFacade().getTextEditorWindow().enableFleschScore();
				}
			}
		});
		// SentenceCount Listener
		this.windowFacade.getTextEditorWindow().setSentenceCountListener(new SentenceCountListener() {
			@Override
			public void sentenceCountClicked(SentenceCountEventObject ev) {
				setHelper(ev.getEditorContentData());
				getWindowFacade().getTextEditorWindow().setSentenceCount(
						(sentenceCount = getHelper().getNumberOfSentences()));
				if(wordCount != null && sentenceCount != null && syllableCount != null) {
					getWindowFacade().getTextEditorWindow().enableFleschScore();
				}
			}
		});
		// SyllableCount Listener
		this.windowFacade.getTextEditorWindow().setSyllableCountListener(new SyllableCountListener() {
			@Override
			public void syllableCountClicked(SyllableCountEventObject ev) {
				setHelper(ev.getEditorContentData());
				getWindowFacade().getTextEditorWindow().setSyllableCount(
						(syllableCount = getHelper().getNumberOfSyllables()));
				if(wordCount != null && sentenceCount != null && syllableCount != null) {
					getWindowFacade().getTextEditorWindow().enableFleschScore();
				}
			}
		});
		// FleschScore Listener
		this.windowFacade.getTextEditorWindow().setFleschScoreListener(new FleschScoreListener() {
			@Override
			public void fleschScoreClicked(FleschScoreEventObject ev) {
				double words = wordCount;
				double sentences = sentenceCount;
				double syllables = syllableCount;
				
				setHelper(ev.getEditorContentData());
				getWindowFacade().getTextEditorWindow().setFleschScore(
						getHelper().getFleschScore(words, sentences, syllables));
			}
		});
		this.windowFacade.getMainWindow().setTrainGeneratorButtonEventListener(new TrainGeneratorButtonEventListener() {
			@Override
			public void trainGeneratorButtonClicked(TrainGeneratorButtonEventObject ev) {
				markovTrainerService = new MarkovTrainerService(editorContentData);
				
				windowFacade.getMainWindow().setGeneratorStatusBarProgress(
						markovTrainerService.progressProperty(),
						markovTrainerService.runningProperty(),
						markovTrainerService.messageProperty());
				
				markovTrainerService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					@Override
					public void handle(WorkerStateEvent t) {
						TrainerDataPackage data = (TrainerDataPackage) t.getSource().getValue();
						
						wordList = data.getWordList();
						starterString = data.getStarterString();
						
						windowFacade.getMainWindow().enableGeneratorButton();
					}
				});
				
				markovTrainerService.start();
			}
		});
		this.windowFacade.getMainWindow().setGenerateTextButtonEventListener(new GenerateTextButtonEventListener() {
			@Override
			public void generateTextButtonClicked(GenerateTextButtonEventObject ev) {
				markovGeneratorService = new MarkovGeneratorService(
						wordList, starterString, ev.getNumOfWords());
				
				windowFacade.getMainWindow().setGeneratorStatusBarProgress(
						markovGeneratorService.progressProperty(),
						markovGeneratorService.runningProperty(),
						markovGeneratorService.messageProperty());
				
				markovGeneratorService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					@Override
					public void handle(WorkerStateEvent t) {
						String generatedText = (String) t.getSource().getValue();
						
						windowFacade.getTextEditorWindow().setTextArea(generatedText);
					}
				});
				// Clear text area before starting
				windowFacade.getTextEditorWindow().clearTextArea();
				
				markovGeneratorService.start();
				
			}
		});
	}
	
	/**
	 * Constructs the main window
	 */
	public void constructMainWindow() {
		this.windowFacade.constructMainWindow();
	}
	
	@SuppressWarnings("javadoc")
	public WindowFacade getWindowFacade() {
		return this.windowFacade;
	}
	
	@SuppressWarnings("javadoc")
	public Helper getHelper() {
		return this.textAnalyzerOneLoop;
	}
	@SuppressWarnings("javadoc")
	public void setHelper(String editorContentData) {
		this.textAnalyzerOneLoop = new TextAnalyzer(editorContentData);
	}
	
	/**
	 * Reads a text file and assembles a string,
	 * this string is then used for display 
	 * purposes in the view.
	 * 
	 * @param file			Input file to be read
	 * @return newString	Newly formed string consisting of data from
	 * 						the input file
	 */
	public String getDisplayText(File file) {
		StringBuilder stringBuilder = new StringBuilder("");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
			reader.close();
			
		} catch (IOException e) {
			System.out.println("Error reading File: " + file.getName());
		}
		String newString = stringBuilder.toString();
		
		return newString;
	}
}
