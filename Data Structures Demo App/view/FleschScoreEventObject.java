package view;

import java.util.EventObject;

@SuppressWarnings({ "javadoc", "serial" })
public class FleschScoreEventObject extends EventObject{

	private String editorContentData;
	
	public FleschScoreEventObject(Object source, String editorContentData) {
		super(source);
		this.editorContentData = editorContentData;
	}

	public String getEditorContentData() {
		return editorContentData;
	}
}