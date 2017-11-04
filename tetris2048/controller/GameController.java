package tetris2048.controller;

import java.io.File;
import java.io.IOException;

import tetris2048.model.GameCommand;
import tetris2048.model.GameModel;
import tetris2048.model.GameOverException;
import tetris2048.model.TileFactory.SegmentValueDifficulty;
import tetris2048.model.TileFactory.TileSizeDifficulty;
import tetris2048.model.TileGravityFallCountdown.GravityFallSpeed;
import tetris2048.view.GameView;

public class GameController implements GameControllerCommands {

	private final GameOptionsAccessor gameOptionsAccessor;
	
	@Override
	public GameOptionsAccessor getGameOptionsAccessor() {
		return gameOptionsAccessor;
	}
	
	public class GameOptionsAccessor {
		
		private File fileToStore;

		private GameOptionsAccessor(File fileToStore) {
			this.fileToStore = fileToStore;
			loadOptions();
		}
		
		public int getNumberOfGameFieldRows() {
			return gameModel.getGameModelOptionsAccessor().getNumberOfGameFieldRows();
		}

		public int getNumberOfGameFieldColumns() {
			return gameModel.getGameModelOptionsAccessor().getNumberOfGameFieldColumns();
		}

		public TileSizeDifficulty getTileSizeDifficulty() {
			return gameModel.getGameModelOptionsAccessor().getTileSizeDifficulty();
		}

		public void setTileSizeDifficulty(TileSizeDifficulty tileSizeDifficulty) {
			gameModel.getGameModelOptionsAccessor().setTileSizeDifficulty(tileSizeDifficulty);
		}

		public SegmentValueDifficulty getSegmentValueDifficulty() {
			return gameModel.getGameModelOptionsAccessor().getSegmentValueDifficulty();
		}

		public void setSegmentValueDifficulty(SegmentValueDifficulty segmentValueDifficulty) {
			gameModel.getGameModelOptionsAccessor().setSegmentValueDifficulty(segmentValueDifficulty);
		}

		public void setGravityFallSpeed(GravityFallSpeed gravityFallSpeed) {
			gameModel.getGameModelOptionsAccessor().setGravityFallSpeed(gravityFallSpeed);
		}
		
		private void loadOptions() {
			try {
				gameModel.getGameModelOptionsAccessor().loadOptionsFromFile(fileToStore);
			} catch (IOException e) {
				// It will be initialized by default
				// System.out.println("Cannot load GameOptions from file!");
			}
		}
		
		public void saveOptions() {
			try {
				gameModel.getGameModelOptionsAccessor().saveOptionsToFile(fileToStore);
			} catch (IOException e) {
				//	System.out.println("Cannot save GameOptions to file!");
			}
		}
	}
	
	public enum GameState {
		LAUNCHED, PLAYING, GAMEOVER
	}

	private GameState gameState;

	private boolean gameSaved;
	@Override
	public boolean isGameSaved() {
		return gameSaved;
	}
	
	private GameModel gameModel;
	private GameView gameView;

	public void setGameView(GameView gameView) {
		this.gameView = gameView;

		// Set View listeners to Model
		gameModel.setDrawGameCellListener(gameView.getDrawGameCellListener());
		gameModel.setGameScoreListener((score) -> gameView.showScore(score));
		
		gameView.setGameState(gameState);
	}

	private final BestScores bestScores;

	public GameController(GameModel gameModel) {

		this.gameModel = gameModel;
		
		bestScores = new BestScores(10, new File("BestScores.saved"));
		
		gameOptionsAccessor = new GameOptionsAccessor(new File("GameOptions.saved"));

		gameState = GameState.LAUNCHED;
		gameSaved = true;
	}

	@Override
	public void gameCommand(GameCommand gameCommand) {

		if (gameState != GameState.PLAYING) {
			return;
		}

		gameSaved = false;
		
		try {
			gameModel.command(gameCommand);
		} catch (GameOverException e) {
			gameState = GameState.GAMEOVER;
			gameView.setGameState(gameState);
		}
	}

	@Override
	public GameState getGameState() {
		return gameState;
	}

	@Override
	public int getScore() {
		return gameModel.getScore();
	}

	@Override
	public BestScores getBestScores() {
		return bestScores;
	}

	@Override
	public void newGame() {
		gameModel.startNewGame();

		gameState = GameState.PLAYING;
		gameSaved = false;
		gameView.setGameState(gameState);
	}

	@Override
	public void loadGame(File file) throws IOException {
		gameModel.loadGameFromFile(file); // throws IOException!

		gameState = GameState.PLAYING;
		gameSaved = true;
		gameView.setGameState(gameState);
	}

	@Override
	public void saveGame(File file) throws IOException {
		if (gameState != GameState.PLAYING) {
			return;
		}
		gameModel.saveGameToFile(file); // throws IOException!
		gameSaved = true;
	}

	@Override
	public void exitGame() {
		System.exit(0);
	}

	@Override
	public void repaintGameField() {
		gameModel.repaintGameField();
	}
}
