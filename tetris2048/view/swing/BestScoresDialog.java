package tetris2048.view.swing;

import javax.swing.JOptionPane;

import tetris2048.controller.BestScores;
import tetris2048.view.swing.languages.SwingViewDictionary;

public class BestScoresDialog {

	SwingGameView swingGameView;

	BestScoresDialog(SwingGameView swingGameView) {
		this.swingGameView = swingGameView;
	}

	boolean show() {
		show(-1);
		return true;
	}
	
	boolean show(int newBestScorePosition) {
		
		SwingViewDictionary dict = swingGameView.getSwingViewDictionary();

		BestScores bestScores = swingGameView.getGameController().getBestScores();
		
		String message = "";
		for(int n = 0; n < bestScores.numberOfRecords(); n++) {
			if(n == newBestScorePosition) {
				message += String.format(">>> %2d. %s <<<\n", (n+1), bestScores.getScoreRecord(n));
			}
			else {
				message += String.format("   %2d. %s\n", (n+1), bestScores.getScoreRecord(n));
			}
		}

		JOptionPane.showMessageDialog(swingGameView.getMainFrame(), message,
				dict.translate("Best scores!"),
				JOptionPane.PLAIN_MESSAGE);

		return true;
	}
}
