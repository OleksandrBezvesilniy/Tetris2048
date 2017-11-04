package tetris2048.view.swing.languages;

import java.util.Map;
import java.util.TreeMap;

public class SwingViewDictionaryUA implements SwingViewDictionary {

	private static SwingViewDictionaryUA dictUA = new SwingViewDictionaryUA();

	private SwingViewDictionaryUA() { }

	public static SwingViewDictionary getDictionary() {
		return dictUA;
	}
	
	@Override
	public String language() {
		return "���������";
	}
	
	private static Map<String, String> dictMap = buildDict();

	@Override
	public String translate(String text) {
		String textUA = dictMap.get(text);
		if(textUA != null)
			return textUA;
		else
			return text;
	}

	private static Map<String, String> buildDict() {
		
		dictMap = new TreeMap<String, String>();

		// SwingGameView.java
		
		dictMap.put("Tetris 2048", "����� 2048");
		
		dictMap.put("OK", "��");
		dictMap.put("Cancel", "���������");
		dictMap.put("Yes", "���");
		dictMap.put("No", "ͳ");
		
		dictMap.put("Folder:", "�����:");
		dictMap.put("File name:", "��'� �����:");
		dictMap.put("Files of types:", "���� �����:");

		dictMap.put("Score: ", "����: ");
		
		dictMap.put("Do you want to save your current game?", "������ �������� ������� ���?");
		dictMap.put("Save game?", "�������� ���?");
		
		dictMap.put("Congratulations!", "³����!");
		dictMap.put("Your final score is ", "���� ������� ����: ");
		dictMap.put("You are in the Top-", "�� ��������� � ���-");
		dictMap.put(" players!", " �������!");
		dictMap.put("Please, enter your name:", "���� �����, ������ ���� ��'�:");
		dictMap.put("Game over!", "ʳ���� ���!");
		
		dictMap.put("Unknown", "��������");
		
		dictMap.put("Do you want to start a new game?", "������ ������ ���� ���?");
		
		// MainMenuBuilder.java
		
		dictMap.put("Menu", "����");
		dictMap.put("New game", "���� ���");
		dictMap.put("Load game", "����������� ���");
		dictMap.put("Save game", "�������� ���");
		dictMap.put("Best scores!", "�������!");
		dictMap.put("Screenshot", "�������");
		dictMap.put("Options", "����� ���");
		dictMap.put("Game rules", "������� ���");
		dictMap.put("About", "��� ��������");
		dictMap.put("Exit game", "�����");
		
		// CommandsMenuBuilder.java
		
		dictMap.put("Commands", "�������");
		
		dictMap.put("Tile", "Գ�����");

		dictMap.put("Shift left", "�������� ����");
		dictMap.put("Shift right", "�������� ������");
		dictMap.put("Shift down", "�������� ����");
		dictMap.put("Drop to bottom", "������� ����");
		dictMap.put("Rotate", "���������");
		
		dictMap.put("2048", "2048");
		
		dictMap.put("Pack left", "�������� ����");
		dictMap.put("Pack right", "�������� ������");
		dictMap.put("Pack down", "�������� ����");

		// LoadGameDialog.java
		
		dictMap.put("player", "�������");
		dictMap.put("Game files ", "����� ��� ");
		
		dictMap.put("Load", "�����������");
		dictMap.put("Cannot load the game from the file", "�� �������� ����������� ��� � �����");
		dictMap.put("Load error!", "������� ������������!");
		
		// SaveGameDialog.java
		
		dictMap.put("Save", "�����������");
		dictMap.put("Cannot save the game to the file", "�� �������� �������� ��� � ����");
		dictMap.put("Save error!", "������� ���������!");
		
		// ScreenshotDialog.java
		
		dictMap.put("Screenshots ", "�������� ");
		dictMap.put("Save screenshot", "������� �������");
		dictMap.put("Cannot save the screenshot to the file", "�� �������� �������� ������� � ����");

		// OptionsDialog.java
		
		dictMap.put("Language: ", "����: ");
		
		dictMap.put("Game color scheme", "��������� ������");
		dictMap.put("Classic color scheme", "�������� ������");
		dictMap.put("Dark color scheme", "����� ������");
		
		dictMap.put("Tile size difficulty", "��������� �������");
		dictMap.put("1 to 3 segments (easy)", "�� 3 �������� (�����)");
		dictMap.put("1 to 4 segments (normal)", "�� 4 �������� (�����)");
		dictMap.put("4 segments all (hard)", "�� 4 �������� (�������)");
		
		dictMap.put("Tile values difficulty", "��������� �������");
		dictMap.put("Values 2, 4 (easy)", "�������� 2, 4 (�����)");
		dictMap.put("Values 2, 4, 8 (normal)", "�������� 2, 4, 8 (�����)");
		
		return dictMap;
	}

	@Override
	public String getGameRules() {

		String rules = 
		
			"��� ��������� �������� \n" +
			"�������������� ������ �� ��������:\n\n" +
			
			"�������� ���� - ������ ����\n" +
			"�������� ������ - ������ ������\n" +
			"�������� ���� - ������ ����\n" +
			"��������� - ������ �����\n" +
			"������� ���� - �����\n\n" +
			
			"������� ������� �� ���� ��������, \n" +
			"���� ��� ��������� ������� \n" +
			"����������� ������� ����.\n\n" +
			
			"��� ��������� ��������� �� ��������� ��� 2048 \n" +
			"�������������� Shift � �������� �� ��������:\n\n" +
			
			"�������� ���� - Shift + ������ ����\n" +
			"�������� ������ - Shift + ������ ������\n" +
			"�������� ���� - Shift + ������ ����\n\n" +
			
			"�������� ��������� �� ���� ��������, \n" +
			"���� ������� ���������� ��������� ������, \n" +
			"��� ����� ���������� � �������� ���������.\n\n" +
			
			"Գ����� ���� ���� �� ���� ������� \n" +
			"���� ����� ������ �������.\n\n" +
	
			"���� - �� �������� ���� \n" +
			"��� ��������� �������.\n\n" +
			
			"�� ������ ������ ����� ��� \n" +
			"��� ����������� ������� ���.\n\n" +
			
			"������ ���!";
		
		return rules;
	}

}
