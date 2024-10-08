package com.morpembot.MorphemBot.service;

import com.morpembot.MorphemBot.config.BotConfig;
import com.morpembot.MorphemBot.dataBase.User;
import com.morpembot.MorphemBot.dataBase.UserRepository;
import com.morpembot.MorphemBot.words.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.morpembot.MorphemBot.config.BotCommandText.*;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private UserRepository userRepository;
    private final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();

        if(update.hasMessage()&&update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            if(!userRepository.existsById(chatId)||userRepository.existsById(chatId)&&!userRepository.findByUserId(chatId).isParseCheck()) {

                switch (messageText) {
                    case "/start":
                        startCommand(chatId);
                        break;
                    case "/help":
                        helpCommand(chatId);
                        break;
                    case "/parse":
                        parseCommand(chatId);
                        break;
                    case "/cancel":
                        sendMessage(chatId, CANCEL_NOT_DURING_PARSE);
                        break;
                    default:
                        sendMessage(chatId, UNKNOWN_TEXT);
                        break;
                }
            } else {
                if (messageText.equals("/cancel")) {
                    cancelCommand(chatId);
                } else {
                    findWordByEntered(chatId, messageText);
                }
            }
        }
    }

    private void startCommand(long chatId) {
        registerUser(chatId);
        sendMessage(chatId, START_TEXT);
    }

    private void registerUser(long chatId) {
        if(!userRepository.existsById(chatId)) {
            User user = new User();
            user.setUserId(chatId);
            user.setParseCheck(false);
            userRepository.save(user);
        }
    }

    private void helpCommand(long chatId) {
        sendMessage(chatId, HELP_TEXT);
    }

    private void parseCommand(long chatId) {
        saveParseCheck(chatId, true);
        sendMessage(chatId, PARSE_TEXT);
    }

    private void cancelCommand(long chatId) {
        saveParseCheck(chatId, false);
        sendMessage(chatId, CANCEL_TEXT);
    }

    private void findWordByEntered(long chatId, String entered) {
        try {

            String base;
            String prefix = "";
            String root = "";
            String linking = "";
            String suffix = "";
            String ending = "";
            String text;

            ArrayList<WordPart> wordList = new ArrayList<>();

            Document doc = Jsoup.connect("https://sinonim.org/mo/" + entered.toLowerCase()).get();

            Elements baseEnding = doc.select("div.m_mor0 > span");
            HashSet<String> checkSet = new HashSet<>();

            Elements morphemClasses = doc.select("div.m_mor0 > span.m_m6 > span");
            base = doc.select("div.m_mor0").get(0).text();

            for (Element s : morphemClasses) {
                switch (s.className()) {
                    case "m_m0":
                        if(!checkSet.contains(s.text())) {
                            wordList.add(new Prefix(s.text()));
                            checkSet.add(s.text());
                        }
                        break;
                    case "m_m1":
                        if(!checkSet.contains(s.text())) {
                            wordList.add(new Root(s.text()));
                            checkSet.add(s.text());
                        }
                        break;
                    case "m_m2":
                        if(!checkSet.contains(s.text())) {
                            wordList.add(new Suffix(s.text()));
                            checkSet.add(s.text());
                        }
                        break;
                    case "m_m3":
                        if(!checkSet.contains(s.text())) {
                            wordList.add(new Ending(s.text()));
                            checkSet.add(s.text());
                        }
                        break;
                    case "m_m5":
                        if(!checkSet.contains(s.text())) {
                            wordList.add(new Linking(s.text()));
                            checkSet.add(s.text());
                        }
                        break;
                }
            }

            for (Element s : baseEnding) {
                switch (s.className()) {
                    case "m_m2":
                        if (!checkSet.contains(s.text())) {
                            wordList.add(new Suffix(s.text()));
                            checkSet.add(s.text());
                        }
                        break;
                    case "m_m3":
                        if (!checkSet.contains(s.text())) {
                            wordList.add(new Ending(s.text()));
                            checkSet.add(s.text());
                        }
                        break;
                }
            }

            for(WordPart wordPart: wordList) {
                if (wordPart instanceof Prefix) {
                    prefix += wordPart.getWordPart() + ", ";
                } else if (wordPart instanceof Root) {
                    root += wordPart.getWordPart() + ", ";
                } else if (wordPart instanceof Linking) {
                    linking += wordPart.getWordPart() + ", ";
                } else if (wordPart instanceof Suffix) {
                    suffix += wordPart.getWordPart() + ", ";
                } else if (wordPart instanceof Ending) {
                    ending += wordPart.getWordPart() + ", ";
                }
            }

            Word word = new Word(wordList);

            text = "Основа слова: " + base + "\n" +
                    "Приставка: " + prefix + "\n" +
                    "Корень: " + root + "\n" +
                    "Соединительная гласная: " + linking + "\n" +
                    "Суффикс: " + suffix + "\n" +
                    "Окончание: " + ending + "\n";

            sendMessage(chatId, text);
        } catch (IOException e) {
            System.out.println("IOException");
            sendMessage(chatId, WORD_NOT_FOUND);
        }
        saveParseCheck(chatId, false);
    }

    private void saveParseCheck(long chatId, boolean bool) {
        User user = userRepository.findByUserId(chatId);
        user.setParseCheck(bool);
        userRepository.save(user);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("/start");
        row.add("/help");
        row.add("/parse");
        row.add("/cancel");

        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);
        executeMessage(message);
    }

    private void executeMessage(SendMessage message) {
        try{
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Error occurred");
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken(){
        return config.getBotKey();
    }
}
