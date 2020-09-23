package view;

import java.util.EventObject;

@SuppressWarnings({ "javadoc", "serial" })
public class WordCountEventObject extends EventObject{

	private String editorContentData;
	
	public WordCountEventObject(Object source, String editorContentData) {
		super(source);
		this.editorContentData = editorContentData;
	}

	public String getEditorContentData() {
		return editorContentData;
	}
}