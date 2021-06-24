package freaking.raspberry.me.dictccbot;

import freaking.raspberry.me.dictccbot.commands.ChangeTranslationDirectionCommand;
import freaking.raspberry.me.dictccbot.commands.HelpCommand;
import freaking.raspberry.me.dictccbot.services.TranslationService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class DictCcBot extends TelegramLongPollingCommandBot {
    private final String botUsername;
    private final String botToken;

    DictCcBot(final String botUsername, final String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        registerAll(new HelpCommand(),
                new ChangeTranslationDirectionCommand());
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                String translation = TranslationService.translate(update.getMessage().getText());
                long chatId = update.getMessage().getChatId();
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText(translation);
                message.setParseMode(ParseMode.HTML);

                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                KeyboardRow keyboardRow = new KeyboardRow();
                KeyboardButton changeTranslationDirectionButton = new KeyboardButton("/changetranslationdirection");
                keyboardRow.add(changeTranslationDirectionButton);
                List<KeyboardRow> keyboardRows = new ArrayList<>();
                keyboardRows.add(keyboardRow);
                replyKeyboardMarkup.setKeyboard(keyboardRows);
                message.setReplyMarkup(replyKeyboardMarkup);

                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onRegister() {

    }
}

