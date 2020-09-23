package view;

import java.util.EventListener;

@SuppressWarnings("javadoc")
public interface SyllableCountListener extends EventListener {
	public void syllableCountClicked(SyllableCountEventObject ev);
}
