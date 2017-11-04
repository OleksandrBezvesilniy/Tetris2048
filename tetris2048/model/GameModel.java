package tetris2048.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import tetris2048.model.CellType;
import tetris2048.model.GameOverException;
import tetris2048.model.Tile;
import tetris2048.model.Tile.TileSegment;
import tetris2048.model.TileFactory.SegmentValueDifficulty;
import tetris2048.model.TileFactory.TileSizeDifficulty;
import tetris2048.model.TileGravityFallCountdown.GravityFallSpeed;

public class GameModel implements GameCommandListener {

	private final GameModelOptionsAccessor gameModelOptionsAccessor;
	
	public GameModelOptionsAccessor getGameModelOptionsAccessor() {
		return gameModelOptionsAccessor;
	}

	public class GameModelOptionsAccessor {

		private GameModelOptionsAccessor() { }
		
		public int getNumberOfGameFieldRows() {
			return gameField.rows();
		}
		public int getNumberOfGameFieldColumns() {
			return gameField.columns();
		}

		public TileSizeDifficulty getTileSizeDifficulty() {
			return tileFactory.getTileSizeDifficulty();
		}
		public void setTileSizeDifficulty(TileSizeDifficulty tileSizeDifficulty) {
			tileFactory.setTileSizeDifficulty(tileSizeDifficulty);
		}

		public SegmentValueDifficulty getSegmentValueDifficulty() {
			return tileFactory.getSegmentValueDifficulty();
		}
		public void setSegmentValueDifficulty(SegmentValueDifficulty segmentValueDifficulty) {
			tileFactory.setSegmentValueDifficulty(segmentValueDifficulty);
		}
		
		public GravityFallSpeed getGravityFallSpeed() {
			return tileGravityFallCountdown.getGravityFallSpeed();
		}
		
		public void setGravityFallSpeed(GravityFallSpeed gravityFallSpeed) {
			tileGravityFallCountdown.setGravityFallSpeed(gravityFallSpeed);
		}
		
		private void loadOptionsFromFile(ObjectInputStream ois) throws IOException, ClassNotFoundException {
			setTileSizeDifficulty((TileSizeDifficulty) ois.readObject());
			setSegmentValueDifficulty((SegmentValueDifficulty) ois.readObject());
			setGravityFallSpeed((GravityFallSpeed) ois.readObject());
		}
		
		private void saveOptionsToFile(ObjectOutputStream oos) throws IOException {
			oos.writeObject(getTileSizeDifficulty());
			oos.writeObject(getSegmentValueDifficulty());
			oos.writeObject(getGravityFallSpeed());
		}
		
		public void loadOptionsFromFile(File file) throws FileNotFoundException, IOException {

			try(FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
				
				loadOptionsFromFile(ois);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		public void saveOptionsToFile(File file) throws FileNotFoundException, IOException {

			try(FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				
				saveOptionsToFile(oos);
			}
		}
		
	}
	
	private Random rand = new Random();

	private GameField2048 gameField;
	
	private TileFactory tileFactory;
	private Tile tile;
	// Position of the tile top left corner on the game field
	private int iPos;
	private int jPos;
	private int[] jPosAcceptable; // Acceptable positions for a new tile to be created
	
	private TilePositionTests tilePositionTests;

	private TileGravityFallCountdown tileGravityFallCountdown;
	
	private int score;
	public int getScore() { return score; }
	
	private DrawGameCellListener drawGameCellListener;
	
	public void setDrawGameCellListener(DrawGameCellListener drawGameCellListener) {
		this.drawGameCellListener = drawGameCellListener;
	}

	private GameScoreListener gameScoreListener;
	
	public void setGameScoreListener(GameScoreListener gameScoreListener) {
		this.gameScoreListener = gameScoreListener;
	}
	
	public GameModel() {
		// The final game field dimensions
		final int numberOfRows = 10;
		final int numberOfColumns = 4;
		gameField = new GameField2048(numberOfRows, numberOfColumns);
		
		tileFactory = new TileFactory();
		tile = null;
		// tile is checked on null if necessary in the public methods.
		// tile is null if the game has not been started yet or the game is over.
		
		jPosAcceptable = new int[numberOfColumns];
		
		tilePositionTests = new TilePositionTests();

		tileGravityFallCountdown = new TileGravityFallCountdown();
		
		gameModelOptionsAccessor = new GameModelOptionsAccessor();
		
		// A stub listener to be replaced later via calling setGameScoreListener(...) method 
		gameScoreListener = new GameScoreListener() {
			@Override
			public void showScore(int score) {
				// Do nothing
			}
		};
		// A stub listener to be replaced later via calling setDrawGameCellListener(...) method 
		drawGameCellListener = new DrawGameCellListener() {
			@Override
			public void drawCell(int value, int iPos, int jPos, CellType type) {
				// Do nothing
			}
		};
	}
	
	private void convertTileSegmentsToFieldCells() {
		
		for(int n = 0; n < tile.length(); n++) {
			TileSegment tileSegment = tile.segment(n);
			int i = iPos + tileSegment.i();
			int j = jPos + tileSegment.j();
			if((i >= 0) && (i < gameField.rows()) && (j >= 0) && (j < gameField.columns())) {
				gameField.setCell(i, j, tileSegment.v());
			}
		}
	}
	
	private void drawGameField() {

		for(int i = 0; i < gameField.rows(); i++) {
			for(int j = 0; j < gameField.columns(); j++) {
				drawGameCellListener.drawCell(gameField.getCell(i, j), i, j, CellType.FIELD_CELL);
			}
		}
		
		if(tile != null) {
			for(int n = 0; n < tile.length(); n++) {
				TileSegment tileSegment = tile.segment(n);
				int i = iPos + tileSegment.i();
				int j = jPos + tileSegment.j();
				if((i >= 0) && (i < gameField.rows()) && (j >= 0) && (j < gameField.columns())) {
					drawGameCellListener.drawCell(tileSegment.v(), i, j, CellType.TILE_CELL);
				}
			}
		}
	}
		
	private boolean packCellsLeft() {
		if(tilePositionTests.isOccupiedCellOnTheRight()) {
			return false;
		}
		score += gameField.packCellsLeft();
		return true;
	}
	
	private boolean packCellsRight() {
		if(tilePositionTests.isOccupiedCellOnTheLeft()) {
			return false;
		}
		score += gameField.packCellsRight();
		return true;
	}
	
	private boolean packCellsDown() {
		if(tilePositionTests.isOccupiedCellAboveTheTile()) {
			return false;
		}
		score += gameField.packCellsDown();
		return true;
	}
	
	private void createNewTile() throws GameOverException {

		tile = tileFactory.getNextTile();
		iPos = 0;
		
		int jPosAcceptableCounter = 0;
		for(jPos = 0; jPos < gameField.columns(); jPos++) {
			if(tilePositionTests.isAcceptable()) {
				jPosAcceptable[jPosAcceptableCounter++] = jPos;
			}
		}
		
		if(jPosAcceptableCounter > 0) {
			jPos = jPosAcceptable[rand.nextInt(jPosAcceptableCounter)];
		}
		else {
			// Prepare the game field to "Game over!"
			
			// Chose any jPos
			jPos = rand.nextInt(gameField.columns());
			while(tilePositionTests.isBeyondFieldRightBorder()) {
				jPos--;
			}
			// Shift the tile up
			while(tilePositionTests.isOverOccupiedCell()) {
				iPos--;
			}
			convertTileSegmentsToFieldCells();
			tile = null;
			throw new GameOverException();
		}
	}

	private boolean shiftTileLeft() {
		jPos--;
		if(!tilePositionTests.isAcceptable()) {
			jPos++;
			return false;
		}
		return true;
	}
	
	private boolean shiftTileRight() {
		jPos++;
		if(!tilePositionTests.isAcceptable()) {
			jPos--;
			return false;
		}
		return true;
	}
	
	private boolean shiftTileDown() {
		iPos++;
		if(!tilePositionTests.isAcceptable()) {
			iPos--;
			return false;
		}
		return true;
	}
	
	private boolean dropTileToBottom() {
		do {
			shiftTileDown();
		}
		while(!tilePositionTests.isTouchDown());
		
		return true;
	}
	
	private boolean rotateTileLeft() {

		tile.rotateLeft();

		int leftShiftCounter = 0;
		while(tilePositionTests.isBeyondFieldRightBorder()) {
			jPos--;
			leftShiftCounter++;
		}
		int rightShiftCounter = 0;
		while(tilePositionTests.isBeyondFieldLeftBorder()) {
			jPos++;
			rightShiftCounter++;
		}
		if(!tilePositionTests.isAcceptable()) {
			jPos += leftShiftCounter - rightShiftCounter;
			tile.rotateRight();
			return false;
		}
		return true;
	}
	
	private boolean rotateTileRight() {
		
		tile.rotateRight();
		
		int leftShiftCounter = 0;
		while(tilePositionTests.isBeyondFieldRightBorder()) {
			jPos--;
			leftShiftCounter++;
		}
		int rightShiftCounter = 0;
		while(tilePositionTests.isBeyondFieldLeftBorder()) {
			jPos++;
			rightShiftCounter++;
		}
		if(!tilePositionTests.isAcceptable()) {
			jPos += leftShiftCounter - rightShiftCounter;
			tile.rotateLeft();
			return false;
		}
		return true;
	}
	
	private class TilePositionTests {
		
		public boolean isAcceptable() {

			if(isAboveFieldTopBorder()) return false;
			if(isBelowFieldBottomBorder()) return false;
			if(isBeyondFieldLeftBorder()) return false;
			if(isBeyondFieldRightBorder()) return false;
			if(isOverOccupiedCell()) return false;

			return true;
		}

		public boolean isAboveFieldTopBorder() {
			for(int n = 0; n < tile.length(); n++) {
				TileSegment tileSegment = tile.segment(n);
				int i = iPos + tileSegment.i();
			//	int j = jPos + tileSegment.j();
				
				if(i < 0) return true;
			}
			return false;
		}

		public boolean isBelowFieldBottomBorder() {
			for(int n = 0; n < tile.length(); n++) {
				TileSegment tileSegment = tile.segment(n);
				int i = iPos + tileSegment.i();
			//	int j = jPos + tileSegment.j();
				
				if(i >= gameField.rows()) return true;
			}
			return false;
		}

		public boolean isBeyondFieldLeftBorder() {
			for(int n = 0; n < tile.length(); n++) {
				TileSegment tileSegment = tile.segment(n);
			//	int i = iPos + tileSegment.i();
				int j = jPos + tileSegment.j();
				
				if(j < 0) return true;
			}
			return false;
		}

		public boolean isBeyondFieldRightBorder() {
			for(int n = 0; n < tile.length(); n++) {
				TileSegment tileSegment = tile.segment(n);
			//	int i = iPos + tileSegment.i();
				int j = jPos + tileSegment.j();
				
				if(j >= gameField.columns()) return true;
			}
			return false;
		}

		public boolean isOverOccupiedCell() {
			for(int n = 0; n < tile.length(); n++) {
				TileSegment tileSegment = tile.segment(n);
				int i = iPos + tileSegment.i();
				int j = jPos + tileSegment.j();
				
				if((i >= 0) && (i < gameField.rows()) && 
					(j >= 0) && (j < gameField.columns())) {
					if(gameField.getCell(i, j) != 0) {
						return true;
					}
				}
			}
			return false;
		}
		
		public boolean isTouchDown() {
			if(!isAcceptable()) {
				return true; // Should never happened, but for protection returns true here.
				// The test isTouchDown() should never be called for an unacceptable tile position. 
			}
			
			for(int n = 0; n < tile.length(); n++) {
				TileSegment tileSegment = tile.segment(n);
				int i = iPos + tileSegment.i();
				int j = jPos + tileSegment.j();
				
				if(i == gameField.rows()-1) return true;
				if(gameField.getCell(i+1, j) != 0) return true;
			}
			return false;
		}

		public boolean isOccupiedCellOnTheLeft() {
			if(!isAcceptable()) return true; // Should never happened

			//	Test if there are occupied cells on the left from the tile
			for(int n = 0; n < tile.length(); n++) {
				TileSegment tileSegment = tile.segment(n);
				int i = iPos + tileSegment.i();
				int j = jPos + tileSegment.j();
				
				for(int jLeft = 0; jLeft < j; jLeft++) {
					if(gameField.getCell(i, jLeft) != 0) {
						return true;
					}
				}
			}
			return false;
		}
		
		public boolean isOccupiedCellOnTheRight() {
			if(!isAcceptable()) return true; // Should never happened
			
			//	Test if there are occupied cells on the right from the tile
			for(int n = 0; n < tile.length(); n++) {
				TileSegment tileSegment = tile.segment(n);
				int i = iPos + tileSegment.i();
				int j = jPos + tileSegment.j();
				for(int jRight = j+1; jRight < gameField.columns() ; jRight++) {
					if(gameField.getCell(i, jRight) != 0) {
						return true;
					}
				}
			}
			return false;
		}

		public boolean isOccupiedCellAboveTheTile() {
			if(!isAcceptable()) return true; // Should never happened 

			//	Test if there are occupied cells above the tile
			for(int n = 0; n < tile.length(); n++) {
				TileSegment tileSegment = tile.segment(n);
				int i = iPos + tileSegment.i();
				int j = jPos + tileSegment.j();
				for(int iAbove = 0; iAbove < i ; iAbove++) {
					if(gameField.getCell(iAbove, j) != 0) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public void saveGameToFile(File file) throws IOException {
		
		try(FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			
			gameModelOptionsAccessor.saveOptionsToFile(oos);
			
			gameField.saveToFile(oos);
			
			tile.saveToFile(oos);
			
			tileGravityFallCountdown.saveToFile(oos);
			
			oos.writeObject(new Integer(iPos));
			oos.writeObject(new Integer(jPos));
			
			oos.writeObject(new Integer(score));
		}
	}
	
	public void loadGameFromFile(File file) throws IOException {
		
		try(FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis)) {
			
			gameModelOptionsAccessor.loadOptionsFromFile(ois);
			
			gameField.loadFromFile(ois);
			
			tile = Tile.loadFromFile(ois);
			
			tileGravityFallCountdown.loadFromFile(ois);
			
			iPos = (Integer) ois.readObject();
			jPos = (Integer) ois.readObject();
			
			score = (Integer) ois.readObject();
			
			// Update game view
			gameScoreListener.showScore(score);
			drawGameField();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void command(GameCommand gameCommand) throws GameOverException {
		
		if(tile == null) return;
		
		switch (gameCommand) {

		case SHIFT_TILE_LEFT:
			shiftTileLeft();
			tileGravityFallCountdown.tick();
			break;
		case SHIFT_TILE_RIGHT:
			shiftTileRight();
			tileGravityFallCountdown.tick();
			break;
		case SHIFT_TILE_DOWN:
			shiftTileDown();
			// tileGravityFallCountdown.tick(); // No need for gravity fall here
			break;
		case DROP_TILE_TO_BOTTOM:
			dropTileToBottom();
			// tileGravityFallCountdown.tick(); // No need for gravity fall here
			break;

		case ROTATE_TILE_LEFT:
			rotateTileLeft();
			tileGravityFallCountdown.tick();
			break;
		case ROTATE_TILE_RIGHT:
			rotateTileRight();
			tileGravityFallCountdown.tick();
			break;

		case PACK_CELLS_LEFT:
			packCellsLeft();
			tileGravityFallCountdown.tick();
			break;
		case PACK_CELLS_RIGHT:
			packCellsRight();
			tileGravityFallCountdown.tick();
			break;
		case PACK_CELLS_DOWN:
			packCellsDown();
			tileGravityFallCountdown.tick();
			break;

		case NULL_COMMAND:
			// Do nothing
			break;
		}

		try {
			// Test for touch down
			if (tilePositionTests.isTouchDown()) {
				// It is time to create a new tile!
				convertTileSegmentsToFieldCells();
				createNewTile(); // It may throw GameOverException!
				tileGravityFallCountdown.restart();
			}
			else {
				if (tileGravityFallCountdown.isExpired()) {
					// Perform the gravity fall move
					shiftTileDown();
					// Restart the gravity fall counter
					tileGravityFallCountdown.restart();
					
					// Test again for touch down
					if(tilePositionTests.isTouchDown()) {
						convertTileSegmentsToFieldCells();
						createNewTile(); // It may throw GameOverException!
					}
				}
			}
			
		} catch(GameOverException e) {
			// GameOverException may be thrown from the method if_isTileTouchDown_CreateNewTile()
			// by the method createNewTile() at the moment when a new tile is born, 
			// but there is no free space left in the game field to place it.
			
			throw e;
		}
		finally {
			repaintGameField();
		}
	}

	public void repaintGameField() {
		gameScoreListener.showScore(score);
		drawGameField();
	}

	public void startNewGame() {

		score = 0;

		gameField.clearCells();

		try {
			createNewTile();
		}
		catch (GameOverException e) {
			e.printStackTrace(); // Should never happened
		}
		
		repaintGameField();
	}

}
