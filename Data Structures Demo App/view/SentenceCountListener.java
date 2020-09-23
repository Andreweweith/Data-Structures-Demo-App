package view;

import java.util.EventListener;

@SuppressWarnings("javadoc")
public interface SentenceCountListener extends EventListener {
	public void sentenceCountClicked(SentenceCountEventObject ev);
}
