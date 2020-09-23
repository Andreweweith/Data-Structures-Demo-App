package view;

import java.io.File;
import java.util.EventObject;

@SuppressWarnings({ "javadoc", "serial" })
public class LoadFileEventObject extends EventObject{

	private File fileData;
	
	public LoadFileEventObject(Object source, File fileData) {
		super(source);
		this.fileData = fileData;
	}

	public File getFileData() {
		return fileData;
	}
}
