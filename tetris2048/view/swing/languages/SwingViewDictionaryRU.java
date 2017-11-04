package tetris2048.view.swing.languages;

import java.util.Map;
import java.util.TreeMap;

public class SwingViewDictionaryRU implements SwingViewDictionary {

	private static SwingViewDictionaryRU dictRU = new SwingViewDictionaryRU();

	private SwingViewDictionaryRU() { }

	public static SwingViewDictionary getDictionary() {
		return dictRU;
	}

	@Override
	public String language() {
		return "Русский";
	}
	
	private static Map<String, String> dictMap = buildDict();

	@Override
	public String translate(String text) {
		String textRU = dictMap.get(text);
		if(textRU != null)
			return textRU;
		else
			return text;
	}

	private static Map<String, String> buildDict() {
		
		dictMap = new TreeMap<String, String>();

		// SwingGameView.java
		
		dictMap.put("Tetris 2048", "Тетрис 2048");
		
		dictMap.put("OK", "ОК");
		dictMap.put("Cancel", "Отмена");
		dictMap.put("Yes", "Да");
		dictMap.put("No", "Нет");

		dictMap.put("Folder:", "Папка:");
		dictMap.put("File name:", "Имя файла:");
		dictMap.put("Files of types:", "Типы файлов:");

		dictMap.put("Score: ", "Очки: ");
		
		dictMap.put("Do you want to save your current game?", "Хотите сохранить текущую игру?");
		dictMap.put("Save game?", "Сохранить игру?");
		
		dictMap.put("Congratulations!", "Поздравляем!");
		dictMap.put("Your final score is ", "Ваши финальные очки: ");
		dictMap.put("You are in the Top-", "Вы вошли в Топ-");
		dictMap.put(" players!", " игроков!");
		dictMap.put("Please, enter your name:", "Пожалуйста, введите ваше имя:");
		dictMap.put("Game over!", "Конец игры!");
		
		dictMap.put("Unknown", "Неизвестный");
		
		dictMap.put("Do you want to start a new game?", "Хотите начать новую игру?");
		
		// MainMenuBuilder.java
		
		dictMap.put("Menu", "Меню");
		dictMap.put("New game", "Новая игра");
		dictMap.put("Load game", "Загрузить игру");
		dictMap.put("Save game", "Сохранить игру");
		dictMap.put("Best scores!", "Рекорды!");
		dictMap.put("Screenshot", "Скриншот");
		dictMap.put("Options", "Опции игры");
		dictMap.put("Game rules", "Правила игры");
		dictMap.put("About", "О программе");
		dictMap.put("Exit game", "Выход");
		
		// CommandsMenuBuilder.java
		
		dictMap.put("Commands", "Команды");
		
		dictMap.put("Tile", "Фигурка");

		dictMap.put("Shift left", "Подвинуть влево");
		dictMap.put("Shift right", "Подвинуть вправо");
		dictMap.put("Shift down", "Подвинуть вниз");
		dictMap.put("Drop to bottom", "Сбросить вниз");
		dictMap.put("Rotate", "Повернуть");
		
		dictMap.put("2048", "2048");
		
		dictMap.put("Pack left", "Паковать влево");
		dictMap.put("Pack right", "Паковать вправо");
		dictMap.put("Pack down", "Паковать вниз");

		// LoadGameDialog.java
		
		dictMap.put("player", "игрок");
		dictMap.put("Game files ", "Файлы игры ");
		
		dictMap.put("Load", "Загрузить");
		dictMap.put("Cannot load the game from the file", "Не удается загрузить игру из файла");
		dictMap.put("Load error!", "Ошибка загрузки!");
		
		// SaveGameDialog.java
		
		dictMap.put("Save", "Сохранить");
		dictMap.put("Cannot save the game to the file", "Не удается сохранить игру в файл");
		dictMap.put("Save error!", "Ошибка сохранения!");
		
		// ScreenshotDialog.java
		
		dictMap.put("Screenshots ", "Скриншоты ");
		dictMap.put("Save screenshot", "Сохранить скриншот");
		dictMap.put("Cannot save the screenshot to the file", "Не удается сохранить скриншот в файл");

		// OptionsDialog.java
		
		dictMap.put("Language: ", "Язык: ");
		
		dictMap.put("Game color scheme", "Цветовая палитра");
		dictMap.put("Classic color scheme", "Классическая палитра");
		dictMap.put("Dark color scheme", "Темная палитра");
		
		dictMap.put("Tile size difficulty", "Сложность фигурок");
		dictMap.put("1 to 3 segments (easy)", "до 3 сегментов (легко)");
		dictMap.put("1 to 4 segments (normal)", "до 4 сегментов (норма)");
		dictMap.put("4 segments all (hard)", "по 4 сегмента (сложно)");
		
		dictMap.put("Tile values difficulty", "Сложность значений");
		dictMap.put("Values 2, 4 (easy)", "значения 2, 4 (легко)");
		dictMap.put("Values 2, 4, 8 (normal)", "значения 2, 4, 8 (норма)");
		
		return dictMap;
	}

	@Override
	public String getGameRules() {
		
		String rules = 
		
			"Для управления фигуркой \n" +
			"используйте стрелки на клавиатуре:\n\n" +
			
			"Подвинуть влево - стрелка влево\n" +
			"Подвинуть вправо - стрелка вправо\n" +
			"Подвинуть вниз - стрелка вниз\n" +
			"Повернуть - стрелка вверх\n" +
			"Сбросить вниз - пробел\n\n" +
			
			"Поворот фигурки не будет выполнен, \n" +
			"если для повернутой фигурки \n" +
			"недостаточно свободного места.\n\n" +
			
			"Для управления клеточками по правилам игры 2048 \n" +
			"используйте Shift со стрелками на клавиатуре:\n\n" +
			
			"Паковать влево - Shift + стрелка влево\n" +
			"Паковать вправо - Shift + стрелка вправо\n" +
			"Паковать вниз - Shift + стрелка вниз\n\n" +
			
			"Операция упаковки не будет выполнена, \n" +
			"если фигурка опустилась достаточно низко, \n" +
			"чтобы стать препятствием в направлении упаковки.\n\n" +
			
			"Фигурка падает вниз на одну клеточку \n" +
			"после каждой игровой команды.\n\n" +
	
			"Очки - это общая сумма \n" +
			"всех упакованных клеточек.\n\n" +
			
			"Вы можете изменить опции игры \n" +
			"без перезапуска текущей игры.\n\n" +
			
			"Приятной игры!";

		return rules;
	}

}
