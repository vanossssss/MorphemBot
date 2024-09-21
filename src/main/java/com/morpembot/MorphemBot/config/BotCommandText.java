package com.morpembot.MorphemBot.config;

public class BotCommandText {
    public static final String START_TEXT =
            "Привет, это бот, с помощью которого можно посмотреть морфологический разбор слов.\n" +
                    "Для того, чтобы узнать его функционал введи /help";

    public static final String HELP_TEXT =
            "Список команд:\n\n" +
                    "/start - перезапуск бота\n" +
                    "/parse - разобрать слово\n" +
                    "/cancel - отмена разбора\n";

    public static final String PARSE_TEXT =
            "Введите слово...";

    public static final String UNKNOWN_TEXT =
            "Неизвестная команда, введите /help для полного списка команд.";

    public static final String CANCEL_TEXT =
            "Отмена произошла успешно.";

    public static final String CANCEL_NOT_DURING_PARSE =
            "Вам нечего отменять.";

    public static final String WORD_NOT_FOUND =
            "Слово не найдено.";
}
