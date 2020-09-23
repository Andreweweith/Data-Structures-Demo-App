package view;

import java.util.EventListener;

/**
 * @author Andrew
 *
 */
public interface GenerateGraphButtonEventListener extends EventListener {

	/**
	 * @param ev
	 */
	public void generateGraphButtonClicked(GenerateGraphButtonEventObject ev);
}
