package view;

import java.util.EventListener;

@SuppressWarnings("javadoc")
public interface FleschScoreListener extends EventListener {
	public void fleschScoreClicked(FleschScoreEventObject ev);
}
