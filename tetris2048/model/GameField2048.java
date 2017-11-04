package tetris2048.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameField2048 {

	private int[][] cell;
	
	private int rows;
	private int columns;

	public int rows() { return rows; }
	public int columns() { return columns; }
	
	public GameField2048(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		cell = new int [rows][columns];
	}

	public int getCell(int i, int j) {
		return cell[i][j];
	}
	
	public void setCell(int i, int j, int value) {
		cell[i][j] = value;
	}
	
	public void clearCells() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns ; j++) {
				cell[i][j] = 0;
			}
		}
	}
	
	public int packCellsLeft() {
		int score = 0;
		
		boolean loopContinue;
		
		for(int i = 0; i < rows; i++) {
			// Shift all non-zero cells to the left
			do {
				loopContinue = false;
				for(int j = 0; j <= columns-2; j++) {
					if((cell[i][j] == 0)&&(cell[i][j+1] != 0)) {
						cell[i][j] = cell[i][j+1];
						cell[i][j+1] = 0;
						loopContinue = true;
					}
				}
			} while(loopContinue);
			
			// Sum up equal cells
			for(int j = 0; j <= columns-2; j++) {
				if((cell[i][j] != 0) && (cell[i][j] == cell[i][j+1])) {
					cell[i][j] *= 2;
					cell[i][j+1] = 0;
					score += cell[i][j];
					j++;
				}
			}

			// Shift all non-zero cells to the left
			do {
				loopContinue = false;
				for(int j = 0; j <= columns-2; j++) {
					if((cell[i][j] == 0)&&(cell[i][j+1] != 0)) {
						cell[i][j] = cell[i][j+1];
						cell[i][j+1] = 0;
						loopContinue = true;
					}
				}
			} while(loopContinue);
		}
		
		return score;
	}

	public int packCellsRight() {
		int score = 0;
		
		boolean loopContinue;
		
		for(int i = 0; i < rows; i++) {
			// Shift all non-zero cells to the right
			do {
				loopContinue = false;
				for(int j = columns-1; j >= 1; j--) {
					if((cell[i][j] == 0)&&(cell[i][j-1] != 0)) {
						cell[i][j] = cell[i][j-1];
						cell[i][j-1] = 0;
						loopContinue = true;
					}
				}
			} while(loopContinue);
			
			// Sum up equal cells
			for(int j = columns-1; j >= 1 ; j--) {
				if((cell[i][j] != 0) && (cell[i][j] == cell[i][j-1])) {
					cell[i][j] *= 2;
					cell[i][j-1] = 0;
					score += cell[i][j];
					j--;
				}
			}
			
			// Shift all non-zero cells to the right
			do {
				loopContinue = false;
				for(int j = columns-1; j >= 1; j--) {
					if((cell[i][j] == 0)&&(cell[i][j-1] != 0)) {
						cell[i][j] = cell[i][j-1];
						cell[i][j-1] = 0;
						loopContinue = true;
					}
				}
			} while(loopContinue);
		}
		
		return score;
	}

	public int packCellsDown() {
		int score = 0;
		
		boolean loopContinue;
		
		for(int j = 0; j < columns; j++) {

			// Shift down all non-zero cells
			do {
				loopContinue = false;
				for(int i = rows-1; i >= 1; i--) {
					if((cell[i][j] == 0)&&(cell[i-1][j] != 0)) {
						cell[i][j] = cell[i-1][j];
						cell[i-1][j] = 0;
						loopContinue = true;
					}
				}
			} while(loopContinue);

			// Sum up equal cells
			for(int i = rows-1; i >= 1 ; i--) {
				if((cell[i][j] != 0) && (cell[i][j] == cell[i-1][j])) {
					cell[i][j] *= 2;
					cell[i-1][j] = 0;
					score += cell[i][j];
					i--;
				}
			}

			// Shift down all non-zero cells
			do {
				loopContinue = false;
				for(int i = rows-1; i >= 1; i--) {
					if((cell[i][j] == 0)&&(cell[i-1][j] != 0)) {
						cell[i][j] = cell[i-1][j];
						cell[i-1][j] = 0;
						loopContinue = true;
					}
				}
			} while(loopContinue);
		}
		
		return score;
	}

	public void loadFromFile(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		cell = (int[][]) ois.readObject();
		rows = cell.length;
		columns = cell[0].length;
	}
	
	public void saveToFile(ObjectOutputStream oos) throws IOException {
		oos.writeObject(cell);
	}
}
