package freaking.raspberry.me.dictccbot;

import freaking.raspberry.me.dictccbot.commands.HelpCommand;
import freaking.raspberry.me.dictccbot.services.TranslationService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class DictCcBot extends TelegramLongPollingCommandBot {
    private final String botUsername;
    private final String botToken;

    DictCcBot(final String botUsername, final String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        HelpCommand helpCommand = new HelpCommand();
        register(helpCommand);
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

    @Override
    public void onUpdatesReceived(List<Update> updates) {

    }
}

