package tetris2048.view.swing.languages;

public class SwingViewDictionaryEN implements SwingViewDictionary {

	private static SwingViewDictionaryEN dictEN = new SwingViewDictionaryEN();
	
	private SwingViewDictionaryEN() { }

	public static SwingViewDictionary getDictionary() {
		return dictEN;
	}

	@Override
	public String language() {
		return "English";
	}

	@Override
	public String translate(String text) {
		return text;
	}

	@Override
	public String getGameRules() {
		
		String rules =
				
			"For the tile control \n" +
			"use the arrow keys:\n\n" +
		
			"Shift left - Left arrow\n" +
			"Shift right - Right arrow\n" +
			"Shift down - Down arrow\n" +
			"Rotate - Up arrow\n" +
			"Drop to bottom - Space\n\n" +
		
			"The tile rotation will not be performed \n" +
			"if the rotated tile does not fit into \n" +
			"the free game field space.\n\n" +
		
			"For the 2048 game cell control \n" +
			"use the Shift + arrow keys:\n\n" +
		
			"Pack left - Shift + Left arrow\n" +
			"Pack right - Shift + Right arrow\n" +
			"Pack down - Shift + Down arrow\n\n" +
		
			"The pack operation will not be performed \n" +
			"if the tile falls low enough to become \n" +
			"an obstacle in a packing direction.\n\n" +
		
			"The tile falls one cell down \n" +
			"after each game command.\n\n" +
		
			"The score is a total sum \n" +
			"of all packed cells.\n\n" +
		
			"You can adjust the game options \n" +
			"without restarting the current game.\n\n" +
		
			"Enjoy the game!";
		
		return rules;
	}

}
