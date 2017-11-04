package tetris2048.view.swing.colors;

import java.awt.Color;

import tetris2048.model.CellType;

public interface ColorScheme {
	
	Color getCellBackgroundColor(int cellNumber);
	Color getCellForegroundColor(int cellNumber);
	Color getCellBorderColor(CellType cellType);
	Color getGameFieldGridColor();
	Color getBoundaryLineColor();

	public static ColorScheme getColorScheme(String colorSchemeName) {
		switch(colorSchemeName) {
		case "Dark":
			return DarkColorScheme.getColorScheme();

		case "Classic":
		default:
			return ClassicColorScheme.getColorScheme();
		}
	}

	public static String getColorSchemeName(ColorScheme colorScheme) {
		if(colorScheme.getClass() == DarkColorScheme.class)
			return "Dark";
		else if(colorScheme.getClass() == ClassicColorScheme.class)
			return "Classic";
		else
			return "Classic";
	}
}
