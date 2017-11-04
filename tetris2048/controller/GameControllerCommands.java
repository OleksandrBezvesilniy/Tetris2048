package tetris2048.controller;

import java.io.File;
import java.io.IOException;

import tetris2048.controller.GameController.GameOptionsAccessor;
import tetris2048.controller.GameController.GameState;
import tetris2048.model.GameCommand;

public interface GameControllerCommands {

	void newGame();
	
	void loadGame(File file) throws IOException;
	
	void saveGame(File file) throws IOException;

	boolean isGameSaved();
	
	void exitGame();

	void gameCommand(GameCommand gameCommand);

	GameOptionsAccessor getGameOptionsAccessor();
	
	GameState getGameState();

	int	getScore();

	void repaintGameField();
	
	BestScores getBestScores();
}
