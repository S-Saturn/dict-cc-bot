package freaking.raspberry.me.dictccbot;

import freaking.raspberry.me.dictccbot.commands.HelpCommand;
import freaking.raspberry.me.dictccbot.commands.custom.ChangeTranslationDirectionCommand;
import freaking.raspberry.me.dictccbot.model.CustomCommandName;
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
        registerAll(new HelpCommand());
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                long chatId = update.getMessage().getChatId();
                SendMessage message = new SendMessage();
                boolean isCustomCommand = processCustomCommand(chatId, update.getMessage().getText());
                if (!isCustomCommand) {
                    String translation = TranslationService.translate(update.getMessage().getText());
                    message.setChatId(String.valueOf(chatId));
                    message.setText(translation);
                    message.setParseMode(ParseMode.HTML);

                    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                    KeyboardRow keyboardRow = new KeyboardRow();
                    KeyboardButton changeTranslationDirectionButton = new KeyboardButton(CustomCommandName.CHANGE_TRANSLATION_DIRECTION.getEmoji() + " Change Translation Direction");
                    keyboardRow.add(changeTranslationDirectionButton);
                    List<KeyboardRow> keyboardRows = new ArrayList<>();
                    keyboardRows.add(keyboardRow);
                    replyKeyboardMarkup.setKeyboard(keyboardRows);
                    message.setReplyMarkup(replyKeyboardMarkup);

                    execute(message);
                }
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

    private boolean processCustomCommand(long chatId, String string) {
        CustomCommandName customCommandName = CustomCommandName.getCustomCommandNameByEmoji(string);
        String message = "";
        switch (customCommandName) {
            case CHANGE_TRANSLATION_DIRECTION:
                message = new ChangeTranslationDirectionCommand().execute();
                break;
            case NEXT_PAGE:
            case PREVIOUS_PAGE:
                // NOT IMPLEMENTED YET
                break;
            default:
                return false;
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return true;
    }
}

