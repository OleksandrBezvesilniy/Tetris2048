package tetris2048.view.swing;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import tetris2048.view.swing.languages.SwingViewDictionary;

public class SaveGameDialog {

	SwingGameView swingGameView;
	private JFileChooser fileChooserGame;
	private FileNameExtensionFilter fileExtFilterGame;
	
	SaveGameDialog(SwingGameView swingGameView) {
		this.swingGameView = swingGameView;
		
		SwingViewDictionary dict = swingGameView.getSwingViewDictionary();

		fileChooserGame = new JFileChooser();
		fileChooserGame.setMultiSelectionEnabled(false);
		fileChooserGame.setAcceptAllFileFilterUsed(false);
		fileChooserGame.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fileChooserGame.setSelectedFile(new File(System.getProperty("user.dir") + "\\" + dict.translate("player") + ".t2048"));
		fileExtFilterGame = new FileNameExtensionFilter(dict.translate("Game files ") + "(*.t2048)", "t2048");
		fileChooserGame.setFileFilter(fileExtFilterGame);
		fileChooserGame.setDialogTitle(dict.translate("Save game"));
		fileChooserGame.setApproveButtonText(dict.translate("Save"));
	}
	
	boolean show() {

		SwingViewDictionary dict = swingGameView.getSwingViewDictionary();
		
		boolean successful = false;
		if(fileChooserGame.showOpenDialog(swingGameView.getMainFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooserGame.getSelectedFile();
			if(!file.getName().toLowerCase().endsWith(".t2048")) {
				file = new File(file.getPath() + ".t2048");
			}
			try {
				swingGameView.getGameController().saveGame(file);
				
				if(!swingGameView.getGameController().isGameSaved())
					successful = false;
				else
					successful = true;
				
			} catch (IOException e) {
				successful = false;
			}
			
			if(!successful) {
				JOptionPane.showMessageDialog(swingGameView.getMainFrame(), 
						dict.translate("Cannot save the game to the file") + "\n" + file.getPath(), 
						dict.translate("Save error!"), JOptionPane.ERROR_MESSAGE);
			}
		}
		return successful;
	}

}
