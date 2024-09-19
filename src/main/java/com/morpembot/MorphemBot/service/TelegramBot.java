package com.morpembot.MorphemBot.service;

import com.morpembot.MorphemBot.config.BotConfig;
import com.morpembot.MorphemBot.dataBase.User;
import com.morpembot.MorphemBot.dataBase.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.morpembot.MorphemBot.config.BotCommandText.*;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private UserRepository userRepository;
    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();

        if(update.hasMessage()&&update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

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
                default:
                    sendMessage(chatId, UNKNOWN_TEXT);
                    break;
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
        User user = userRepository.findByUserId(chatId);
        user.setParseCheck(true);
        sendMessage(chatId, PARSE_TEXT);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
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
