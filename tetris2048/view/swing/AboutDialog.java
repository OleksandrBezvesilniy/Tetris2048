package tetris2048.view.swing;

import javax.swing.JOptionPane;

import tetris2048.view.swing.languages.SwingViewDictionary;

public class AboutDialog {
	
	SwingGameView swingGameView;
	
	public AboutDialog(SwingGameView swingGameView) {
		this.swingGameView = swingGameView;
	}
	
	public void show() {
		SwingViewDictionary dictionary = swingGameView.getSwingViewDictionary();
		JOptionPane.showMessageDialog(swingGameView.getMainFrame(),
				dictionary.translate("Tetris 2048") + "\n" + "2017",
				dictionary.translate("About"),
				JOptionPane.INFORMATION_MESSAGE);
	}
	
}
