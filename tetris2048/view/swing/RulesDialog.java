package tetris2048.view.swing;

import javax.swing.JOptionPane;

import tetris2048.view.swing.languages.SwingViewDictionary;

public class RulesDialog {

	SwingGameView swingGameView;
	
	public RulesDialog(SwingGameView swingGameView) {
		this.swingGameView = swingGameView;
	}

	public void show() {
		
		SwingViewDictionary dict = swingGameView.getSwingViewDictionary();

		JOptionPane.showMessageDialog(swingGameView.getMainFrame(), 
				dict.getGameRules(),
				dict.translate("Game rules"), 
				JOptionPane.INFORMATION_MESSAGE);
	}

}
