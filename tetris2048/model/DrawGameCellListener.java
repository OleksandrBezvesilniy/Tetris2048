package tetris2048.model;

public interface DrawGameCellListener {
	void drawCell(int value, int iPos, int jPos, CellType type);
}
