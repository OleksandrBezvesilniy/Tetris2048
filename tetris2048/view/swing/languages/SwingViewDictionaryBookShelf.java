package tetris2048.view.swing.languages;

import java.util.Set;
import java.util.TreeMap;

public class SwingViewDictionaryBookShelf {

	private TreeMap<String, SwingViewDictionary> dictBookShelf = new TreeMap<>();

	private SwingViewDictionary dictEN;
	
	public SwingViewDictionaryBookShelf() {
		
		dictEN = SwingViewDictionaryEN.getDictionary();
		dictBookShelf.put(dictEN.language(), dictEN);
			
		SwingViewDictionary dictRU = SwingViewDictionaryRU.getDictionary();
		dictBookShelf.put(dictRU.language(), dictRU);

		SwingViewDictionary dictUA = SwingViewDictionaryUA.getDictionary();
			dictBookShelf.put(dictUA.language(), dictUA);
	}

	public SwingViewDictionary getDictionary(String dictionaryLanguage) {
		SwingViewDictionary dict = dictBookShelf.get(dictionaryLanguage);
		if(dict != null)
			return dict;
		else
			return SwingViewDictionaryEN.getDictionary();
	}
	
	public Set<String> getLanguageSet() {
		return dictBookShelf.keySet();
	}
}
