package org.telegram.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    public static String BOT_TOKEN = "";
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    Schedule schedule;

    public Bot() {
        schedule = new Schedule();
    }

    @Override
    public String getBotUsername() {
        return "@BigDaddyy_bot";
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setText(getAnswer(update.getMessage().getText()));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getAnswer(String message) {
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        if (message.contains("Hi") || message.contains("Hello") ||
                message.contains("Здарова") || message.contains("Здаров") ||
                message.contains("Привет") || message.contains("a") || message.contains("/start")) {
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardSecondRow.clear();
            keyboardFirstRow.add("ИФСТ-21");
            keyboardFirstRow.add("ИФСТ-22");
            keyboardFirstRow.add("ИФСТ-23");
            keyboardFirstRow.add("ИФСТ-24");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Привет-привет! Чем могу помочь? :)";
        }
        if (message.equals("ИФСТ-21")) {
            schedule.url = "https://rasp.sstu.ru/group/359";
            schedule.connect();
            addDefaultKeyboard(keyboard, keyboardFirstRow, keyboardSecondRow);
        }
        if (message.equals("ИФСТ-22")) {
            schedule.url = "https://rasp.sstu.ru/group/331";
            schedule.connect();
            addDefaultKeyboard(keyboard, keyboardFirstRow, keyboardSecondRow);
        }
        if (message.equals("ИФСТ-23")) {
            schedule.url = "https://rasp.sstu.ru/group/353";
            schedule.connect();
            addDefaultKeyboard(keyboard, keyboardFirstRow, keyboardSecondRow);
        }
        if (message.equals("ИФСТ-24")) {
            schedule.url = "https://rasp.sstu.ru/group/3440";
            schedule.connect();
            addDefaultKeyboard(keyboard, keyboardFirstRow, keyboardSecondRow);
        }
        if (message.equals("Расписание на сегодня")) {
            return schedule.getTodaysSchedule();
        }
        if (message.equals("Расписание на неделю")) {
            return schedule.getWeeksSchedule();
        }
        if (message.equals("Назад")) {
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardSecondRow.clear();
            keyboardFirstRow.add("ИФСТ-21");
            keyboardFirstRow.add("ИФСТ-22");
            keyboardFirstRow.add("ИФСТ-23");
            keyboardFirstRow.add("ИФСТ-24");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
        }
        return "Выбери что-нибудь!";
    }

    private void addDefaultKeyboard(ArrayList<KeyboardRow> keyboard,
                                    KeyboardRow keyboardFirstRow,
                                    KeyboardRow keyboardSecondRow) {
        keyboardFirstRow.clear();
        keyboardSecondRow.clear();
        keyboardFirstRow.add("Расписание на сегодня");
        keyboardFirstRow.add("Расписание на неделю");
        keyboardSecondRow.add("Назад");
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}
