package view;

import java.util.EventObject;

@SuppressWarnings({ "javadoc", "serial" })
public class SentenceCountEventObject extends EventObject{

	private String editorContentData;
	
	public SentenceCountEventObject(Object source, String editorContentData) {
		super(source);
		this.editorContentData = editorContentData;
	}

	public String getEditorContentData() {
		return editorContentData;
	}
}