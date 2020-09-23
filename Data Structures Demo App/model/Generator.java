package model;

@SuppressWarnings("javadoc")
public interface Generator {

	public void read(String text);
	public String generateText(int numOfWords);
}
