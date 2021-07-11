package freaking.raspberry.me.dictccbot;

import freaking.raspberry.me.dictccbot.commands.HelpCommand;
import freaking.raspberry.me.dictccbot.commands.custom.ChangePrecisionCommand;
import freaking.raspberry.me.dictccbot.commands.custom.ChangeTranslationDirectionCommand;
import freaking.raspberry.me.dictccbot.commands.custom.NextPageCommand;
import freaking.raspberry.me.dictccbot.commands.custom.PreviousPageCommand;
import freaking.raspberry.me.dictccbot.model.CustomCommandName;
import freaking.raspberry.me.dictccbot.services.TranslationService;
import freaking.raspberry.me.dictccbot.services.dictionary.Entry;
import freaking.raspberry.me.dictccbot.services.formatter.ResultTableFormatter;
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

    private String currentRequest = "";

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
                boolean isCustomCommand = processCustomCommand(chatId, update.getMessage().getText());
                String result;
                if (!isCustomCommand) {
                    String input = update.getMessage().getText();
                    if (!currentRequest.equals(input)) {
                        List<Entry> translation = TranslationService.translate(input);
                        result = ResultTableFormatter.formatNewEntriesToTable(translation);
                        currentRequest = input;
                    } else {
                        result = ResultTableFormatter.formatExistingEntriesToTable();
                    }
                    sendMessageWithKeyboard(result, chatId);
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

    private void sendMessageWithKeyboard(String text, long chatId) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setParseMode(ParseMode.MARKDOWN);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton changeTranslationDirectionButton = new KeyboardButton(CustomCommandName.CHANGE_TRANSLATION_DIRECTION.getEmojiList().get(0).getString());
        KeyboardButton changeEntryPrecisionButton = new KeyboardButton(TranslationService.getEntryPrecision().getEmoji().getString());
        keyboardRow.add(changeTranslationDirectionButton);
        keyboardRow.add(changeEntryPrecisionButton);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(replyKeyboardMarkup);

        execute(message);
    }

    private boolean processCustomCommand(long chatId, String string) throws TelegramApiException {
        try {
            CustomCommandName customCommandName = CustomCommandName.getCustomCommandNameByEmoji(string);
            String message;
            switch (customCommandName) {
                case CHANGE_TRANSLATION_DIRECTION:
                    message = new ChangeTranslationDirectionCommand().execute();
                    break;
                case CHANGE_PRECISION:
                    message = new ChangePrecisionCommand().execute();
                    break;
                case NEXT_PAGE:
                    message = new NextPageCommand().execute();
                    break;
                case PREVIOUS_PAGE:
                    message = new PreviousPageCommand().execute();
                    break;
                default:
                    return false;
            }
            sendMessageWithKeyboard(message, chatId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

