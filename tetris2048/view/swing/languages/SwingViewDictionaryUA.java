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
		return "Українська";
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
		
		dictMap.put("Tetris 2048", "Тетріс 2048");
		
		dictMap.put("OK", "ОК");
		dictMap.put("Cancel", "Скасувати");
		dictMap.put("Yes", "Так");
		dictMap.put("No", "Ні");
		
		dictMap.put("Folder:", "Папка:");
		dictMap.put("File name:", "Ім'я файла:");
		dictMap.put("Files of types:", "Типи файлів:");

		dictMap.put("Score: ", "Очки: ");
		
		dictMap.put("Do you want to save your current game?", "Бажаєте зберегти поточну гру?");
		dictMap.put("Save game?", "Зберегти гру?");
		
		dictMap.put("Congratulations!", "Вітаємо!");
		dictMap.put("Your final score is ", "Ваші фінальні очки: ");
		dictMap.put("You are in the Top-", "Ви потрапили у Топ-");
		dictMap.put(" players!", " гравців!");
		dictMap.put("Please, enter your name:", "Будь ласка, введіть ваше ім'я:");
		dictMap.put("Game over!", "Кінець гри!");
		
		dictMap.put("Unknown", "Невідомий");
		
		dictMap.put("Do you want to start a new game?", "Бажаєте почати нову гру?");
		
		// MainMenuBuilder.java
		
		dictMap.put("Menu", "Меню");
		dictMap.put("New game", "Нова гра");
		dictMap.put("Load game", "Завантажити гру");
		dictMap.put("Save game", "Зберегти гру");
		dictMap.put("Best scores!", "Рекорди!");
		dictMap.put("Screenshot", "Скріншот");
		dictMap.put("Options", "Опції гри");
		dictMap.put("Game rules", "Привила гри");
		dictMap.put("About", "Про програму");
		dictMap.put("Exit game", "Вихід");
		
		// CommandsMenuBuilder.java
		
		dictMap.put("Commands", "Команди");
		
		dictMap.put("Tile", "Фігурка");

		dictMap.put("Shift left", "Посунути вліво");
		dictMap.put("Shift right", "Посунути вправо");
		dictMap.put("Shift down", "Посунути вниз");
		dictMap.put("Drop to bottom", "Скинути вниз");
		dictMap.put("Rotate", "Повернути");
		
		dictMap.put("2048", "2048");
		
		dictMap.put("Pack left", "Пакувати вліво");
		dictMap.put("Pack right", "Пакувати вправо");
		dictMap.put("Pack down", "Пакувати вниз");

		// LoadGameDialog.java
		
		dictMap.put("player", "гравець");
		dictMap.put("Game files ", "Файли гри ");
		
		dictMap.put("Load", "Завантажити");
		dictMap.put("Cannot load the game from the file", "Не виходить завантажити гру з файла");
		dictMap.put("Load error!", "Помилка завантаження!");
		
		// SaveGameDialog.java
		
		dictMap.put("Save", "Завантажити");
		dictMap.put("Cannot save the game to the file", "Не виходить зберегти гру в файл");
		dictMap.put("Save error!", "Помилка зберігання!");
		
		// ScreenshotDialog.java
		
		dictMap.put("Screenshots ", "Скріншоти ");
		dictMap.put("Save screenshot", "Зберігти скріншот");
		dictMap.put("Cannot save the screenshot to the file", "Не виходить зберегти скріншот в файл");

		// OptionsDialog.java
		
		dictMap.put("Language: ", "Мова: ");
		
		dictMap.put("Game color scheme", "Кольорова палітра");
		dictMap.put("Classic color scheme", "Класична палітра");
		dictMap.put("Dark color scheme", "Темна палітра");
		
		dictMap.put("Tile size difficulty", "Складність фігурок");
		dictMap.put("1 to 3 segments (easy)", "до 3 сегментів (легко)");
		dictMap.put("1 to 4 segments (normal)", "до 4 сегментів (норма)");
		dictMap.put("4 segments all (hard)", "по 4 сегмента (складно)");
		
		dictMap.put("Tile values difficulty", "Складність значень");
		dictMap.put("Values 2, 4 (easy)", "значення 2, 4 (легко)");
		dictMap.put("Values 2, 4, 8 (normal)", "значення 2, 4, 8 (норма)");
		
		return dictMap;
	}

	@Override
	public String getGameRules() {

		String rules = 
		
			"Для керування фігуркою \n" +
			"використовуйте стрілки на клавіатурі:\n\n" +
			
			"Посунути вліво - стрілка вліво\n" +
			"Посунути вправо - стрілка вправо\n" +
			"Посунути вниз - стрілка вниз\n" +
			"Повернути - стрілка вгору\n" +
			"Скинути вниз - пробіл\n\n" +
			
			"Поворот фігурки не буде виконано, \n" +
			"якщо для повернутої фігурки \n" +
			"недостатньо вільного місця.\n\n" +
			
			"Для керування клітинками за правилами гри 2048 \n" +
			"використовуйте Shift зі стрілками на клавіатурі:\n\n" +
			
			"Пакувати вліво - Shift + стрілка вліво\n" +
			"Пакувати вправо - Shift + стрілка вправо\n" +
			"Пакувати вниз - Shift + стрілка вниз\n\n" +
			
			"Операцію пакування не буде виконано, \n" +
			"якщо фігурка спустилася достатньо низько, \n" +
			"щоб стати перешкодою в напрямку пакування.\n\n" +
			
			"Фігурка падає вниз на одну клітинку \n" +
			"після кожної ігрової команди.\n\n" +
	
			"Очки - це загальна сума \n" +
			"всіх пакованих клітинок.\n\n" +
			
			"Ви можете змінити опції гри \n" +
			"без перезапуску поточної гри.\n\n" +
			
			"Приємної гри!";
		
		return rules;
	}

}
