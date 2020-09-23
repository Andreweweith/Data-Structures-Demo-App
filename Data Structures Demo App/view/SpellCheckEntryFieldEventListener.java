package view;

import java.util.EventListener;

@SuppressWarnings("javadoc")
public interface SpellCheckEntryFieldEventListener extends EventListener{
	
	public void spellCheckEntryFieldChanged(SpellCheckEntryFieldEventObject ev);
}
