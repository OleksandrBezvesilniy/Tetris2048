package tetris2048.view;

import tetris2048.controller.GameController.GameState;
import tetris2048.model.DrawGameCellListener;

public interface GameView {

	DrawGameCellListener getDrawGameCellListener();

	void showScore(int score);

	void setGameState(GameState gameState);
}
