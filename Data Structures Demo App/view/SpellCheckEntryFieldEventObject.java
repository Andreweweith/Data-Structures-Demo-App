package view;

import java.util.EventObject;

@SuppressWarnings({ "javadoc", "serial" })
public class SpellCheckEntryFieldEventObject extends EventObject {

	private String newValue;
	
	public SpellCheckEntryFieldEventObject(Object source, String newVal) {
		super(source);
		this.newValue = newVal;
	}

	public String getNewValue() {
		return this.newValue;
	}
}
