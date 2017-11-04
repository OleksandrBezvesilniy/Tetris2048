package tetris2048.view.swing;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import tetris2048.view.swing.languages.SwingViewDictionary;

public class LoadGameDialog {

	SwingGameView swingGameView;
	private JFileChooser fileChooserGame;
	private FileNameExtensionFilter fileExtFilterGame;
	
	LoadGameDialog(SwingGameView swingGameView) {
		this.swingGameView = swingGameView;

		SwingViewDictionary dict = swingGameView.getSwingViewDictionary();

		fileChooserGame = new JFileChooser();
		fileChooserGame.setMultiSelectionEnabled(false);
		fileChooserGame.setAcceptAllFileFilterUsed(false);
		fileChooserGame.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fileChooserGame.setSelectedFile(new File(System.getProperty("user.dir") + "\\" + dict.translate("player") + ".t2048"));
		fileExtFilterGame = new FileNameExtensionFilter(dict.translate("Game files ") + "(*.t2048)", "t2048");
		fileChooserGame.setFileFilter(fileExtFilterGame);
		fileChooserGame.setDialogTitle(dict.translate("Load game"));
		fileChooserGame.setApproveButtonText(dict.translate("Load"));
	}

	void show() {
		
		SwingViewDictionary dict = swingGameView.getSwingViewDictionary();

		if(fileChooserGame.showOpenDialog(swingGameView.getMainFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooserGame.getSelectedFile();
			if(!file.getName().toLowerCase().endsWith(".t2048")) {
				file = new File(file.getPath() + ".t2048");
			}
			try {
				swingGameView.getGameController().loadGame(file);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(swingGameView.getMainFrame(), 
						dict.translate("Cannot load the game from the file") + "\n" + file.getPath(), 
						dict.translate("Load error!"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
