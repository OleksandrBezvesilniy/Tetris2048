package tetris2048.view.swing;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import tetris2048.controller.GameController;
import tetris2048.view.swing.languages.SwingViewDictionary;

public class MainMenuBuilder {

//	private SwingGameView swingGameView;
	private GameController gameController;
	
	private JMenu mainMenu;
	
	private JMenuItem newGameMenuItem;
	private JMenuItem loadGameMenuItem;
	private JMenuItem saveGameMenuItem;
	private JMenuItem bestScoresMenuItem;
	private JMenuItem screenshotMenuItem;
	private JMenuItem optionsMenuItem;
	private JMenuItem rulesMenuItem;
	private JMenuItem aboutMenuItem;
	private JMenuItem exitGameMenuItem;

	public MainMenuBuilder(SwingGameView swingGameView) {
		
//		this.swingGameView = swingGameView;
		this.gameController = swingGameView.getGameController();
		
		SwingViewDictionary dict = swingGameView.getSwingViewDictionary();

		mainMenu = new JMenu(dict.translate("Menu"));
		
		// New game
		newGameMenuItem = new JMenuItem(dict.translate("New game"));
		newGameMenuItem.setActionCommand("NEW_GAME");
	//	newGameMenuItem.addActionListener((ae)->gameController.newGame());
		newGameMenuItem.addActionListener((ae) -> 
			swingGameView.performWithSaveGameConfirm(() -> gameController.newGame()));          
		
		mainMenu.add(newGameMenuItem);
		
		// Load game
		loadGameMenuItem = new JMenuItem(dict.translate("Load game"));
		loadGameMenuItem.setActionCommand("LOAD_GAME");
	//	loadGameMenuItem.addActionListener((ae)->swingGameView.loadGameDialog.show());
		loadGameMenuItem.addActionListener((ae) -> 
			swingGameView.performWithSaveGameConfirm(() -> new LoadGameDialog(swingGameView).show()));          
		mainMenu.add(loadGameMenuItem);
		
		// Save game
		saveGameMenuItem = new JMenuItem(dict.translate("Save game"));
		saveGameMenuItem.setActionCommand("SAVE_GAME");
		saveGameMenuItem.addActionListener((ae)-> new SaveGameDialog(swingGameView).show());
		mainMenu.add(saveGameMenuItem);

		mainMenu.addSeparator();
		
		// Best scores
		bestScoresMenuItem = new JMenuItem(dict.translate("Best scores!"));
		bestScoresMenuItem.addActionListener((ae)-> new BestScoresDialog(swingGameView).show());
		mainMenu.add(bestScoresMenuItem);
		
		// Screenshot
		screenshotMenuItem = new JMenuItem(dict.translate("Screenshot"));
		screenshotMenuItem.addActionListener((ae)-> new ScreenshotDialog(swingGameView).show());
		mainMenu.add(screenshotMenuItem);

		mainMenu.addSeparator();

		// Options
		optionsMenuItem = new JMenuItem(dict.translate("Options"));
		optionsMenuItem.addActionListener((ae)->swingGameView.showOptionsDialog());
		mainMenu.add(optionsMenuItem);
		
		// Rules
		rulesMenuItem = new JMenuItem(dict.translate("Game rules"));
		rulesMenuItem.addActionListener((ae)-> new RulesDialog(swingGameView).show());
		mainMenu.add(rulesMenuItem);
		
		// About
		aboutMenuItem = new JMenuItem(dict.translate("About"));
		aboutMenuItem.addActionListener((ae)-> new AboutDialog(swingGameView).show());
		mainMenu.add(aboutMenuItem);
		
		mainMenu.addSeparator();

		// Exit game
		exitGameMenuItem = new JMenuItem(dict.translate("Exit game"));
		exitGameMenuItem.setActionCommand("EXIT_GAME");
	//	exitGameMenuItem.addActionListener((ae)->swingGameView.exitGame());
		exitGameMenuItem.addActionListener((ae) -> swingGameView.performWithSaveGameConfirm(() -> gameController.exitGame()));          
		mainMenu.add(exitGameMenuItem);
	}

	public JMenu getMainMenu() {
		return mainMenu;
	}
	
	public void setLanguage(SwingViewDictionary dict) {

		mainMenu.setText(dict.translate("Menu"));
		
		newGameMenuItem.setText(dict.translate("New game"));
		loadGameMenuItem.setText(dict.translate("Load game"));
		saveGameMenuItem.setText(dict.translate("Save game"));
		bestScoresMenuItem.setText(dict.translate("Best scores!"));
		screenshotMenuItem.setText(dict.translate("Screenshot"));
		optionsMenuItem.setText(dict.translate("Options"));
		rulesMenuItem.setText(dict.translate("Game rules"));
		aboutMenuItem.setText(dict.translate("About"));
		exitGameMenuItem.setText(dict.translate("Exit game"));
	}
	
	public void setSaveGameMenuItemEnabled(boolean b) {
		saveGameMenuItem.setEnabled(b);
	}

}
