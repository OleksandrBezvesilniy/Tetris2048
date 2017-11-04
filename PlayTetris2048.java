import javax.swing.SwingUtilities;

import tetris2048.controller.GameController;
import tetris2048.model.GameModel;
import tetris2048.view.swing.SwingGameView;

public class PlayTetris2048 {

	private static boolean launched = false;

	private PlayTetris2048() {
	}

	private static void swingLauncher() {

		GameModel gameModel = new GameModel();

		GameController gameController = new GameController(gameModel);
		
		new SwingGameView(gameController);
	}

	public static void playSwing() {
		if (launched) {
			return;
		}
		else {
			launched = true;
			SwingUtilities.invokeLater(() -> swingLauncher());
		}
	}

	public static void main(String[] args) {

		PlayTetris2048.playSwing();
	}
}
