package view;

import java.util.EventListener;

@SuppressWarnings("javadoc")
public interface WordCountListener extends EventListener {
	public void wordCountClicked(WordCountEventObject ev);
}
