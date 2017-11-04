package tetris2048.view.swing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SwingViewOptions {

	private String colorSchemeName;
	private String dictionaryLanguage;
	
	public SwingViewOptions(String colorSchemeName, String dictionaryLanguage) {
		this.colorSchemeName = colorSchemeName;
		this.dictionaryLanguage = dictionaryLanguage;
	}
	
	public String getColorSchemeName() {
		return colorSchemeName;
	}

	public String getDictionaryLanguage() {
		return dictionaryLanguage;
	}
	
	public static SwingViewOptions readFromFile(File file) throws FileNotFoundException, IOException {
		
		SwingViewOptions options = null;
		
		try(FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis)) {
				
				String colorSchemeName = (String) ois.readObject();
				String dictionaryLanguage = (String) ois.readObject();
				options = new SwingViewOptions(colorSchemeName, dictionaryLanguage);
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return options;
	}
	
	 public void saveToFile(File file) throws FileNotFoundException, IOException {

		try(FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos)) {

			oos.writeObject(colorSchemeName);
			oos.writeObject(dictionaryLanguage);
		}
	}
	
}
