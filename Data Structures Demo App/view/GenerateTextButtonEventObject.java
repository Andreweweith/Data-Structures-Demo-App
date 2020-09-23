package view;

import java.util.EventObject;

@SuppressWarnings({ "javadoc", "serial" })
public class GenerateTextButtonEventObject extends EventObject{

	private int numOfWords;
	
	public GenerateTextButtonEventObject(Object source, int num) {
		super(source);
		numOfWords = num;
	}

	public int getNumOfWords() {
		return numOfWords;
	}
}
