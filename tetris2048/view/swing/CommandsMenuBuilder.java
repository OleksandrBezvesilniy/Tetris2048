package tetris2048.view.swing;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import tetris2048.controller.GameController;
import tetris2048.model.GameCommand;
import tetris2048.view.swing.languages.SwingViewDictionary;

public class CommandsMenuBuilder {

	private JMenu commandsMenu;

	private JMenu tileCommandsMenu;

	private JMenuItem shiftTileLeftMenuItem;
	private JMenuItem shiftTileRightMenuItem;
	private JMenuItem shiftTileDownMenuItem;
	private JMenuItem dropTileToBottomMenuItem;
	private JMenuItem rotateTileLeftMenuItem;

	private JMenu g2048CommandsMenu;

	private JMenuItem packCellsLeftMenuItem;
	private JMenuItem packCellsRightMenuItem;
	private JMenuItem packCellsDownMenuItem;

//	private SwingGameView swingGameView;
	
	public CommandsMenuBuilder(SwingGameView swingGameView) {

//		this.swingGameView = swingGameView;
		
		GameController gameController = swingGameView.getGameController();
		
		SwingViewDictionary dict = swingGameView.getSwingViewDictionary();

		commandsMenu = new JMenu(dict.translate("Commands"));

		tileCommandsMenu = new JMenu(dict.translate("Tile"));
		commandsMenu.add(tileCommandsMenu);

		shiftTileLeftMenuItem = new JMenuItem(dict.translate("Shift left"));
		shiftTileRightMenuItem = new JMenuItem(dict.translate("Shift right"));
		shiftTileDownMenuItem = new JMenuItem(dict.translate("Shift down"));
		dropTileToBottomMenuItem = new JMenuItem(dict.translate("Drop to bottom"));
		rotateTileLeftMenuItem = new JMenuItem(dict.translate("Rotate"));

		tileCommandsMenu.add(shiftTileLeftMenuItem);
		tileCommandsMenu.add(shiftTileRightMenuItem);
		tileCommandsMenu.add(shiftTileDownMenuItem);
		tileCommandsMenu.add(dropTileToBottomMenuItem);
		tileCommandsMenu.add(rotateTileLeftMenuItem);

		shiftTileLeftMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
		shiftTileRightMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
		shiftTileDownMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		dropTileToBottomMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
		rotateTileLeftMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));

		shiftTileLeftMenuItem.setActionCommand("SHIFT_TILE_LEFT");
		shiftTileRightMenuItem.setActionCommand("SHIFT_TILE_RIGHT");
		shiftTileDownMenuItem.setActionCommand("SHIFT_TILE_DOWN");
		dropTileToBottomMenuItem.setActionCommand("DROP_TILE_TO_BOTTOM");
		rotateTileLeftMenuItem.setActionCommand("ROTATE_TILE_LEFT");

		shiftTileLeftMenuItem.addActionListener((ae) -> gameController.gameCommand(GameCommand.SHIFT_TILE_LEFT));
		shiftTileRightMenuItem.addActionListener((ae) -> gameController.gameCommand(GameCommand.SHIFT_TILE_RIGHT));
		shiftTileDownMenuItem.addActionListener((ae) -> gameController.gameCommand(GameCommand.SHIFT_TILE_DOWN));
		dropTileToBottomMenuItem.addActionListener((ae) -> gameController.gameCommand(GameCommand.DROP_TILE_TO_BOTTOM));
		rotateTileLeftMenuItem.addActionListener((ae) -> gameController.gameCommand(GameCommand.ROTATE_TILE_LEFT));

		g2048CommandsMenu = new JMenu(dict.translate("2048"));
		commandsMenu.add(g2048CommandsMenu);

		packCellsLeftMenuItem = new JMenuItem(dict.translate("Pack left"));
		packCellsRightMenuItem = new JMenuItem(dict.translate("Pack right"));
		packCellsDownMenuItem = new JMenuItem(dict.translate("Pack down"));

		g2048CommandsMenu.add(packCellsLeftMenuItem);
		g2048CommandsMenu.add(packCellsRightMenuItem);
		g2048CommandsMenu.add(packCellsDownMenuItem);

		packCellsLeftMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.SHIFT_DOWN_MASK));
		packCellsRightMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.SHIFT_DOWN_MASK));
		packCellsDownMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_DOWN_MASK));

		packCellsLeftMenuItem.setActionCommand("PACK_CELLS_LEFT");
		packCellsRightMenuItem.setActionCommand("PACK_CELLS_RIGHT");
		packCellsDownMenuItem.setActionCommand("PACK_CELLS_DOWN");

		packCellsLeftMenuItem.addActionListener((ae) -> gameController.gameCommand(GameCommand.PACK_CELLS_LEFT));
		packCellsRightMenuItem.addActionListener((ae) -> gameController.gameCommand(GameCommand.PACK_CELLS_RIGHT));
		packCellsDownMenuItem.addActionListener((ae) -> gameController.gameCommand(GameCommand.PACK_CELLS_DOWN));
	}

	public JMenu getGameCommandsMenu() {
		return commandsMenu;
	}

	public void setLanguage(SwingViewDictionary dict) {

		commandsMenu.setText(dict.translate("Commands"));

		tileCommandsMenu.setText(dict.translate("Tile"));

		shiftTileLeftMenuItem.setText(dict.translate("Shift left"));
		shiftTileRightMenuItem.setText(dict.translate("Shift right"));
		shiftTileDownMenuItem.setText(dict.translate("Shift down"));
		dropTileToBottomMenuItem.setText(dict.translate("Drop to bottom"));
		rotateTileLeftMenuItem.setText(dict.translate("Rotate"));

		g2048CommandsMenu.setText(dict.translate("2048"));

		packCellsLeftMenuItem.setText(dict.translate("Pack left"));
		packCellsRightMenuItem.setText(dict.translate("Pack right"));
		packCellsDownMenuItem.setText(dict.translate("Pack down"));
	}

	public void setCommandsMenuEnabled(boolean b) {
		commandsMenu.setEnabled(b);
	}
	
	public void setCommandsMenuVisible(boolean aFlag) {
		commandsMenu.setVisible(aFlag);
	}
}
