package view;

import java.util.EventListener;

@SuppressWarnings("javadoc")
public interface LoadFileListener extends EventListener {
	public void loadFileClicked(LoadFileEventObject ev);
}
