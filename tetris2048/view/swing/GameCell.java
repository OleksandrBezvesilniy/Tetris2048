package tetris2048.view.swing;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import tetris2048.model.CellType;
import tetris2048.view.swing.colors.*;

public class GameCell {

	private static ColorScheme colorScheme;
	static {
		setColorScheme(ClassicColorScheme.getColorScheme());
	}
	
	private static Border tileCellBorder;
	private static Border fieldCellBorder;
	private static Border emptyCellBorder;

	private JLabel label;
	
	public void addToGameFieldPanel(GameFieldPanel gameFieldPanel) {
		gameFieldPanel.add(label);
	}
	
	public GameCell() {
		
		label = new JLabel();
		
		label.setHorizontalAlignment(SwingConstants.CENTER);

		label.setOpaque(true);
		
		Font font = label.getFont();
		label.setFont(new Font(font.getName(), font.getStyle(), font.getSize()*2));

		// Empty field cell
		set(0, CellType.FIELD_CELL);
	}
	
	public void set(int value, CellType cellType) {
		
		Border cellBorder = emptyCellBorder;
		if(value > 0) {
			switch(cellType) {
	
			case TILE_CELL:
				cellBorder = tileCellBorder;
				break;
			case FIELD_CELL:
				cellBorder = fieldCellBorder;
				break;
			}
		}
		label.setBorder(cellBorder);

		label.setBackground(colorScheme.getCellBackgroundColor(value));
		label.setForeground(colorScheme.getCellForegroundColor(value));
		if(value > 0) {
			label.setText("" + value);
		}
		else {
			label.setText("");
		}
	}
	
	public static void setColorScheme(ColorScheme colorScheme) {
		
		GameCell.colorScheme = colorScheme;
		
		tileCellBorder = BorderFactory.createLineBorder(colorScheme.getCellBorderColor(CellType.TILE_CELL), 2);
		
	//	fieldCellBorder = BorderFactory.createLineBorder(colorScheme.getCellBorderColor(CellType.FIELD_CELL));
		fieldCellBorder = BorderFactory.createEmptyBorder();
		
		emptyCellBorder = BorderFactory.createEmptyBorder();
	}
}
