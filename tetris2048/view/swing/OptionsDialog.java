package tetris2048.view.swing;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import tetris2048.controller.GameController.GameOptionsAccessor;
import tetris2048.model.TileFactory.SegmentValueDifficulty;
import tetris2048.model.TileFactory.TileSizeDifficulty;
import tetris2048.view.swing.colors.ClassicColorScheme;
import tetris2048.view.swing.colors.ColorScheme;
import tetris2048.view.swing.languages.SwingViewDictionary;

public class OptionsDialog extends JDialog {

	private static final long serialVersionUID = 2528425673099812630L;

	private SwingGameView swingGameView;

	private JLabel languagePanelLabel;
	private JComboBox<String> languageComboBox;
	
	private TitledBorder colorSchemePanelInnerBorder;
	private JRadioButton classicSchemeRadioBtn;
	private JRadioButton darkSchemeRadioBtn;

	private TitledBorder tileShapesPanelInnerBorder;
	private JRadioButton segments123RadioBtn;
	private JRadioButton segments1234RadioBtn;
	private JRadioButton segments4onlyRadioBtn;
	
	private TitledBorder tileNumbersPanelInnerBorder;
	private JRadioButton numbers24RadioBtn;
	private JRadioButton numbers248RadioBtn;
	
	private JButton okButton;
	private JButton cancelButton;
	
	OptionsDialog(SwingGameView swingGameView) {
		super(swingGameView.getMainFrame(), "Options", true);
		
		this.swingGameView = swingGameView;

		SwingViewDictionary dict = swingGameView.getSwingViewDictionary();

		setTitle(dict.translate("Options"));
		
		// Languages
		
		JPanel languagePanel = new JPanel();
	//	languagePanel.setLayout(new BoxLayout(languagePanel, BoxLayout.LINE_AXIS));
		languagePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		languagePanelLabel = new JLabel(dict.translate("Language: "));
		languagePanel.add(languagePanelLabel);
		languageComboBox = new JComboBox<String>();

		DefaultComboBoxModel<String> languageComboBoxModel = new DefaultComboBoxModel<String>();
		
		Set<String> languageSet = SwingGameView.getAvailableDictionaryLanguages();
		for(String language: languageSet) {
			languageComboBoxModel.addElement(language);
		}
		
		languageComboBox.setModel(languageComboBoxModel);
		languageComboBox.setSelectedIndex(0);
//		languageComboBox.setMaximumSize(new Dimension(110, 70));
		languagePanel.add(languageComboBox);
		
		this.add(languagePanel, BorderLayout.PAGE_START);

		// Option panel
		
		JPanel optionPanel = new JPanel();
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.PAGE_AXIS));
		this.add(optionPanel, BorderLayout.CENTER);
		
		// Color scheme
		
		JPanel colorSchemePanel = new JPanel(); 
		colorSchemePanelInnerBorder = BorderFactory.createTitledBorder(dict.translate("Game color scheme"));
		Border colorSchemePanelOuterBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		colorSchemePanel.setBorder(BorderFactory.createCompoundBorder(colorSchemePanelOuterBorder, 
																	colorSchemePanelInnerBorder));
		colorSchemePanel.setLayout(new BoxLayout(colorSchemePanel, BoxLayout.PAGE_AXIS));
		
		ButtonGroup colorSchemeGroup = new ButtonGroup();
		classicSchemeRadioBtn = new JRadioButton(dict.translate("Classic color scheme"));
		darkSchemeRadioBtn = new JRadioButton(dict.translate("Dark color scheme"));
		colorSchemeGroup.add(classicSchemeRadioBtn);
		colorSchemeGroup.add(darkSchemeRadioBtn);
		classicSchemeRadioBtn.setSelected(true);

		colorSchemePanel.add(classicSchemeRadioBtn);
		colorSchemePanel.add(darkSchemeRadioBtn);
		optionPanel.add(colorSchemePanel);

		// Tile shapes
		
		JPanel tileShapesPanel = new JPanel(); 
		tileShapesPanelInnerBorder = BorderFactory.createTitledBorder(dict.translate("Tile size difficulty"));
		Border tileShapesPanelOuterBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		tileShapesPanel.setBorder(BorderFactory.createCompoundBorder(tileShapesPanelOuterBorder,
																	tileShapesPanelInnerBorder));
		tileShapesPanel.setLayout(new BoxLayout(tileShapesPanel, BoxLayout.PAGE_AXIS));
		
		ButtonGroup tileShapeGroup = new ButtonGroup();
		segments123RadioBtn = new JRadioButton(dict.translate("1 to 3 segments (easy)"));
		segments1234RadioBtn = new JRadioButton(dict.translate("1 to 4 segments (normal)"));
		segments4onlyRadioBtn = new JRadioButton(dict.translate("4 segments all (hard)"));
		tileShapeGroup.add(segments123RadioBtn);
		tileShapeGroup.add(segments1234RadioBtn);
		tileShapeGroup.add(segments4onlyRadioBtn);
		segments1234RadioBtn.setSelected(true);

		tileShapesPanel.add(segments123RadioBtn);
		tileShapesPanel.add(segments1234RadioBtn);
		tileShapesPanel.add(segments4onlyRadioBtn);
		optionPanel.add(tileShapesPanel);

		// Tile numbers
		
		JPanel tileNumbersPanel = new JPanel();
		tileNumbersPanelInnerBorder = BorderFactory.createTitledBorder(dict.translate("Tile values difficulty"));
		Border tileNumbersPanelOuterBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		tileNumbersPanel.setBorder(BorderFactory.createCompoundBorder(tileNumbersPanelOuterBorder, 
																tileNumbersPanelInnerBorder));
		tileNumbersPanel.setLayout(new BoxLayout(tileNumbersPanel, BoxLayout.PAGE_AXIS));
		
		ButtonGroup tileNumbersGroup = new ButtonGroup();
		numbers24RadioBtn = new JRadioButton(dict.translate("Values 2, 4 (easy)"));
		numbers248RadioBtn = new JRadioButton(dict.translate("Values 2, 4, 8 (normal)"));
		tileNumbersGroup.add(numbers24RadioBtn);
		tileNumbersGroup.add(numbers248RadioBtn);
		numbers248RadioBtn.setSelected(true);

		tileNumbersPanel.add(numbers24RadioBtn);
		tileNumbersPanel.add(numbers248RadioBtn);
		optionPanel.add(tileNumbersPanel);

		// OK-Cancel buttons
		JPanel btnPanel = new JPanel();
	//	btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.LINE_AXIS));
		okButton = new JButton(dict.translate("OK"));
		cancelButton = new JButton(dict.translate("Cancel"));
		btnPanel.add(okButton);
		btnPanel.add(cancelButton);
		add(btnPanel, BorderLayout.PAGE_END);

//		Dimension dim = new Dimension(200, 500);
		pack();
		setResizable(false);

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				GameOptionsAccessor gameOptionsAccessor = swingGameView.getGameController().getGameOptionsAccessor();

				// Language
				String selectedLanguage = (String) languageComboBox.getSelectedItem();
				swingGameView.setSwingViewDictionary(selectedLanguage);
				
				// Color scheme
				String colorSchemeName = "";
				if(classicSchemeRadioBtn.isSelected()) {
					colorSchemeName = "Classic";
				}
				else {
					colorSchemeName = "Dark";
				}
				swingGameView.setColorScheme(ColorScheme.getColorScheme(colorSchemeName));
				
				// Tile numbers
				if(numbers24RadioBtn.isSelected()) {
					gameOptionsAccessor.setSegmentValueDifficulty(SegmentValueDifficulty.Segment_Values_2_4);
				}
				else {
					gameOptionsAccessor.setSegmentValueDifficulty(SegmentValueDifficulty.Segment_Values_2_4_8);
				}
				
				// Tile size difficulty
				if(segments123RadioBtn.isSelected()) {
					gameOptionsAccessor.setTileSizeDifficulty(TileSizeDifficulty.Tiles_of_1_to_3_Segments);
				}
				else if(segments1234RadioBtn.isSelected()) {
					gameOptionsAccessor.setTileSizeDifficulty(TileSizeDifficulty.Tiles_of_1_to_4_Segments);
				}
				else {
					gameOptionsAccessor.setTileSizeDifficulty(TileSizeDifficulty.Tiles_of_4_Segments);
				}
				
				gameOptionsAccessor.saveOptions();
				swingGameView.saveSwingViewOptions();
				
				// Close the dialog
				setVisible(false);
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Close the dialog
				setVisible(false);
			}
		});
		
	}

	@Override
	public void setVisible(boolean bVisible) {
		
		GameOptionsAccessor gameOptionsAccessor = swingGameView.getGameController().getGameOptionsAccessor();

		String dictionaryLanguage = swingGameView.getSwingViewDictionary().language();
		languageComboBox.setSelectedItem(dictionaryLanguage);

		if(swingGameView.getColorScheme().getClass() == ClassicColorScheme.class) {
			classicSchemeRadioBtn.setSelected(true);
		}
		else {
			darkSchemeRadioBtn.setSelected(true);
		}

		if(gameOptionsAccessor.getSegmentValueDifficulty() == SegmentValueDifficulty.Segment_Values_2_4) {
			numbers24RadioBtn.setSelected(true);
		}
		else {
			numbers248RadioBtn.setSelected(true);
		}

		if(gameOptionsAccessor.getTileSizeDifficulty() == TileSizeDifficulty.Tiles_of_1_to_3_Segments) {
			segments123RadioBtn.setSelected(true);
		}
		else if (gameOptionsAccessor.getTileSizeDifficulty() == TileSizeDifficulty.Tiles_of_1_to_4_Segments) {
			segments1234RadioBtn.setSelected(true);
		}
		else {
			segments4onlyRadioBtn.setSelected(true);
		}

		Point p = swingGameView.getMainFrame().getLocation();
		setLocation(p.x + 25, p.y + 100);
		super.setVisible(bVisible);
	}
	
	public void setLanguage(SwingViewDictionary dict) {
		
		setTitle(dict.translate("Options"));
		
		languagePanelLabel.setText(dict.translate("Language: "));
		
		colorSchemePanelInnerBorder.setTitle(dict.translate("Game color scheme"));
		
		classicSchemeRadioBtn.setText(dict.translate("Classic color scheme"));
		darkSchemeRadioBtn.setText(dict.translate("Dark color scheme"));
		
		tileShapesPanelInnerBorder.setTitle(dict.translate("Tile size difficulty"));
		segments123RadioBtn.setText(dict.translate("1 to 3 segments (easy)"));
		segments1234RadioBtn.setText(dict.translate("1 to 4 segments (normal)"));
		segments4onlyRadioBtn.setText(dict.translate("4 segments all (hard)"));

		tileNumbersPanelInnerBorder.setTitle(dict.translate("Tile values difficulty"));
		numbers24RadioBtn.setText(dict.translate("Values 2, 4 (easy)"));
		numbers248RadioBtn.setText(dict.translate("Values 2, 4, 8 (normal)"));

		okButton.setText(dict.translate("OK"));
		cancelButton.setText(dict.translate("Cancel"));
	}
}
