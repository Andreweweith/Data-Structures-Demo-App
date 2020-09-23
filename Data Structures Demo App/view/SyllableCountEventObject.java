package view;

import java.util.EventObject;

@SuppressWarnings({ "javadoc", "serial" })
public class SyllableCountEventObject extends EventObject{

	private String editorContentData;
	
	public SyllableCountEventObject(Object source, String editorContentData) {
		super(source);
		this.editorContentData = editorContentData;
	}

	public String getEditorContentData() {
		return editorContentData;
	}
}