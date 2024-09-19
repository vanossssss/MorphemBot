package com.morpembot.MorphemBot.config;

public class BotCommandText {
    public static final String START_TEXT =
            "Привет, это бот, с помощью которого можно посмотреть морфологический разбор слов.\n" +
                    "Для того, чтобы узнать его функционал введи /help";

    public static final String HELP_TEXT =
            "Список команд:\n\n" +
                    "/start - перезапуск бота" +
                    "/parse - разобрать слово";

    public static final String PARSE_TEXT =
            "Введите слово...";

    public static final String UNKNOWN_TEXT =
            "Неизвестная команда, введите /help для полного списка команд.";
}
