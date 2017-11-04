package tetris2048.view.swing;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import tetris2048.model.CellType;
import tetris2048.model.DrawGameCellListener;
import tetris2048.view.swing.colors.ColorScheme;

public class GameFieldPanel extends JPanel implements DrawGameCellListener {
	
	private static final long serialVersionUID = -8389261157868234835L;
	
	private GameCell[][] gameCells;
	
	public GameFieldPanel(int numberOfRows, int numberOfColumns, ColorScheme colorScheme) {
		
		GridLayout gridLayout = new GridLayout(numberOfRows, numberOfColumns);
		gridLayout.setHgap(5);
		gridLayout.setVgap(5);
		setLayout(gridLayout);
		
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Set colorScheme to Panel
		setBackground(colorScheme.getGameFieldGridColor());

		// Set colorScheme to GameCell
		GameCell.setColorScheme(colorScheme);
		
		gameCells = new GameCell[10][4];
		for(int row = 0; row < numberOfRows; row++) {
			for(int col = 0; col < numberOfColumns; col++) {
				gameCells[row][col] = new GameCell();
				gameCells[row][col].addToGameFieldPanel(this);
			}
		}
	}
	
	public void setColorScheme(ColorScheme colorScheme) {
		// Set colorScheme to Panel
		setBackground(colorScheme.getGameFieldGridColor());
		// Set colorScheme to GameCell
		GameCell.setColorScheme(colorScheme);
	}

	@Override
	public void drawCell(int number, int iPos, int jPos, CellType type) {
		gameCells[iPos][jPos].set(number, type);
	}
}
