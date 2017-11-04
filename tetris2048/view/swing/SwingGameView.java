package tetris2048.view.swing;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import tetris2048.controller.BestScores;
import tetris2048.controller.GameController;
import tetris2048.controller.BestScores.ScoreRecord;
import tetris2048.controller.GameController.GameOptionsAccessor;
import tetris2048.controller.GameController.GameState;
import tetris2048.model.DrawGameCellListener;
import tetris2048.view.GameView;
import tetris2048.view.swing.colors.*;
import tetris2048.view.swing.languages.*;

public class SwingGameView implements GameView {

	private static final SwingViewDictionaryBookShelf dictBookShelf = new SwingViewDictionaryBookShelf();

	public static Set<String> getAvailableDictionaryLanguages() {
		return dictBookShelf.getLanguageSet();
	}

	private GameController gameController;

	private ColorScheme colorScheme;

	private SwingViewDictionary dictionary;

	private GameState gameState;

	private JFrame mainFrame;
	private GameFieldPanel gameFieldPanel;
	private JLabel scoreLabel;

	private MainMenuBuilder mainMenuBuilder;
	private CommandsMenuBuilder commandsMenuBuilder;

	OptionsDialog optionsDialog;

	private File optionsFile = new File("SwingViewOptions.saved");
	
	public SwingGameView(GameController gameController) {

		this.gameController = gameController;

		try {
			// Initialize using the saved options
			SwingViewOptions options = SwingViewOptions.readFromFile(optionsFile);
			colorScheme = ColorScheme.getColorScheme(options.getColorSchemeName());
			dictionary = dictBookShelf.getDictionary(options.getDictionaryLanguage());
		} catch (IOException e) {
			// Initialization by default
			colorScheme = ClassicColorScheme.getColorScheme();
			dictionary = SwingViewDictionaryEN.getDictionary();
		}

		setLanguageForSwingDialogs();

		mainFrame = new JFrame(dictionary.translate("Tetris 2048"));

		// Catch the user click on the "close window" button
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				performWithSaveGameConfirm(() -> gameController.exitGame());          
			}
		});

		GameOptionsAccessor gameOptionsAccessor = gameController.getGameOptionsAccessor();
		gameFieldPanel = new GameFieldPanel(gameOptionsAccessor.getNumberOfGameFieldRows(),
											gameOptionsAccessor.getNumberOfGameFieldColumns(),
											colorScheme);
		
		mainFrame.add(gameFieldPanel);

		scoreLabel = new JLabel();
		showScore(0);

		mainMenuBuilder = new MainMenuBuilder(this);
		commandsMenuBuilder = new CommandsMenuBuilder(this);

		mainFrame.setJMenuBar(createMenuBar());

		optionsDialog = new OptionsDialog(this);

		Dimension dim = new Dimension(256, 704);
		mainFrame.setMinimumSize(dim);
		mainFrame.setPreferredSize(dim);
		mainFrame.setMaximumSize(dim);
		mainFrame.setMaximizedBounds(new Rectangle(dim));
		mainFrame.setSize(dim);
		mainFrame.setResizable(false);

		mainFrame.pack();

		// Set View to Controller
		gameController.setGameView(this);

		mainFrame.setVisible(true);
	}

	private JMenuBar createMenuBar() {

		JMenuBar menuBar = new JMenuBar();

		// Main menu
		JMenu mainMenu = mainMenuBuilder.getMainMenu();
		menuBar.add(mainMenu);

		// Game commands menu
		JMenu commandsMenu = commandsMenuBuilder.getGameCommandsMenu();
		// commandsMenu.setVisible(false); // To show or hide commandsMenu
		menuBar.add(commandsMenu);

		// Score panel is placed into the menuBar!
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(scoreLabel);
		menuBar.add(Box.createRigidArea(new Dimension(5, 0)));

		return menuBar;
	}

	@Override
	public DrawGameCellListener getDrawGameCellListener() {
		return gameFieldPanel;
	}

	@Override
	public void showScore(int score) {
		scoreLabel.setText(dictionary.translate("Score: ") + score);
	}

	public void showOptionsDialog() {
		optionsDialog.setVisible(true);
	}

	@Override
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
		switch (gameState) {

		case LAUNCHED:
			mainMenuBuilder.setSaveGameMenuItemEnabled(false);
			commandsMenuBuilder.setCommandsMenuEnabled(false);
			break;

		case PLAYING:
			mainMenuBuilder.setSaveGameMenuItemEnabled(true);
			commandsMenuBuilder.setCommandsMenuEnabled(true);
			break;

		case GAMEOVER:
			mainMenuBuilder.setSaveGameMenuItemEnabled(false);
			commandsMenuBuilder.setCommandsMenuEnabled(false);
			gameOver();
			break;
		}
	}

	interface NewLoadExitCommand {
		void perform();
	}
	
	void performWithSaveGameConfirm(NewLoadExitCommand command) {

		switch (gameState) {

		case LAUNCHED:
			// The game has not been started yet, so there is nothing to save.
			command.perform();
			break;

		case PLAYING:
			if(gameController.isGameSaved()) {
				// The game has been saved recently and no changes have been made yet.
				command.perform();
			}
			else {
				int answer = JOptionPane.showConfirmDialog(mainFrame, 
						dictionary.translate("Do you want to save your current game?"),
						dictionary.translate("Save game?"),
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if (answer == JOptionPane.YES_OPTION) {
					boolean successful = new SaveGameDialog(this).show();
					if(successful) { 
						command.perform();
					} else {
						// Do nothing
					}
				} else if (answer == JOptionPane.NO_OPTION) {
					command.perform();
				} else {
					// answer is JOptionPane.CANCEL_OPTION
					// Do nothing 
				}
			}
			break;

		case GAMEOVER:
			// You cannot save the game when the game is over./
			command.perform();
			break;
		}
	}

	void gameOver() {
		
		int finalScore = gameController.getScore();
		
		BestScores bestScores = gameController.getBestScores();
		
		if(bestScores.isOneOfTheBest(finalScore)) {
			
			String playerName = JOptionPane.showInputDialog(mainFrame, 
					dictionary.translate("Congratulations!") +"\n" +
					dictionary.translate("Your final score is ")+ finalScore + "!\n" +
					dictionary.translate("You are in the Top-") + bestScores.numberOfRecords() + 
					dictionary.translate(" players!") + "\n" + 
					dictionary.translate("Please, enter your name:"), 
					dictionary.translate("Game over!"),
					JOptionPane.PLAIN_MESSAGE);
			
			if((playerName == null) || (playerName.length() == 0)) {
				playerName = dictionary.translate("Unknown");
			}
			
			ScoreRecord newScoreRecord = new ScoreRecord(playerName, finalScore);
			
			int newBestScorePosition = bestScores.addScoreRecord(newScoreRecord);
			new BestScoresDialog(this).show(newBestScorePosition);
			
		} else {
			if(JOptionPane.showConfirmDialog(mainFrame,
					dictionary.translate("Game over!") + "\n" +
					dictionary.translate("Your final score is ") + finalScore + "!\n" +
					dictionary.translate("Do you want to start a new game?"),
					dictionary.translate("Game over!"),
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

				gameController.newGame();
			}
		}
		
	}

	public ColorScheme getColorScheme() {
		return colorScheme;
	}
	
	public void setColorScheme(ColorScheme colorScheme) {
		this.colorScheme = colorScheme;
		// Apply colorScheme
		gameFieldPanel.setColorScheme(colorScheme);
		gameController.repaintGameField();
		mainFrame.repaint();
	}
	
	public SwingViewDictionary getSwingViewDictionary() {
		return dictionary;
	}
	
	public void setSwingViewDictionary(String dictionaryLanguage) {
		this.dictionary = dictBookShelf.getDictionary(dictionaryLanguage);
		// Apply dictionary
		setLanguage();
	}

	public void setSwingViewDictionary(SwingViewDictionary dictionary) {
		this.dictionary = dictionary;
		// Apply dictionary
		setLanguage();
	}
	
	private void setLanguage() {
		
		mainFrame.setTitle(dictionary.translate("Tetris 2048"));
		
		setLanguageForSwingDialogs();
		
		mainMenuBuilder.setLanguage(dictionary);
		commandsMenuBuilder.setLanguage(dictionary);
		optionsDialog.setLanguage(dictionary);
	}

	public GameState getGameState() {
		return gameState;
	}
	
	public GameController getGameController() {
		return gameController;
	}
	
	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void saveSwingViewOptions() {
		SwingViewOptions options = new SwingViewOptions(ColorScheme.getColorSchemeName(colorScheme),
														dictionary.language());
		try {
			options.saveToFile(optionsFile);
		} catch (IOException e) {
		//	e.printStackTrace();
		}
	}
	
	private void setLanguageForSwingDialogs() {
		
		UIManager.put("OptionPane.okButtonText", dictionary.translate("OK"));
		UIManager.put("OptionPane.cancelButtonText", dictionary.translate("Cancel"));
		UIManager.put("OptionPane.yesButtonText", dictionary.translate("Yes"));
		UIManager.put("OptionPane.noButtonText", dictionary.translate("No"));

		UIManager.put("FileChooser.lookInLabelText", dictionary.translate("Folder:"));
		UIManager.put("FileChooser.fileNameLabelText", dictionary.translate("File name:"));
		UIManager.put("FileChooser.filesOfTypeLabelText", dictionary.translate("Files of types:"));
		UIManager.put("FileChooser.cancelButtonText", dictionary.translate("Cancel"));
		
	}
}
