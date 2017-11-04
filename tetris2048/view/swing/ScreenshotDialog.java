package tetris2048.view.swing;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import tetris2048.view.swing.languages.SwingViewDictionary;

public class ScreenshotDialog {

	SwingGameView swingGameView;
	private JFileChooser fileChooserScreenshot;
	private FileNameExtensionFilter filterScreenshot;
	
	ScreenshotDialog(SwingGameView swingGameView) {
		this.swingGameView = swingGameView;

		SwingViewDictionary dict = swingGameView.getSwingViewDictionary();

		fileChooserScreenshot = new JFileChooser();
		fileChooserScreenshot.setMultiSelectionEnabled(false);
		fileChooserScreenshot.setAcceptAllFileFilterUsed(false);
		fileChooserScreenshot.setCurrentDirectory(new File(System.getProperty("user.dir")));
		fileChooserScreenshot.setSelectedFile(new File(System.getProperty("user.dir") + "\\" + dict.translate("player") + ".png"));
		filterScreenshot = new FileNameExtensionFilter(dict.translate("Screenshots ") + "(*.png; *.jpg)", "png", "jpg");
		fileChooserScreenshot.setFileFilter(filterScreenshot);
		fileChooserScreenshot.setDialogTitle(dict.translate("Save screenshot"));
		fileChooserScreenshot.setApproveButtonText(dict.translate("Save"));
	}

	boolean show() {
		
		SwingViewDictionary dict = swingGameView.getSwingViewDictionary();

		boolean success = false;
		if(fileChooserScreenshot.showOpenDialog(swingGameView.getMainFrame()) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooserScreenshot.getSelectedFile();
			
			String fileName = file.getName();
			String imageFormat = "";
			if(fileName.toLowerCase().endsWith(".png")) {
				imageFormat = "png";
			}
			else if(fileName.toLowerCase().endsWith(".jpg")) {
				imageFormat = "jpg";
			}
			else {
				imageFormat = "png";
				file = new File(file.getPath() + ".png");
			}
			
			BufferedImage biScreenshot = captureScreenshot(swingGameView.getMainFrame());

			try {
				ImageIO.write(biScreenshot, imageFormat, file);
				success = true;
			} catch(Exception e) {
				success = false;
				JOptionPane.showMessageDialog(swingGameView.getMainFrame(), 
						dict.translate("Cannot save the screenshot to the file") + "\n" + file.getPath(), 
						dict.translate("Save error!"), JOptionPane.ERROR_MESSAGE);
			}
		}
		return success;
	}
	
	private BufferedImage captureScreenshot(Component component) {
		BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		component.printAll(image.getGraphics()); // or component.paint(...)
		return image;
	}
}
