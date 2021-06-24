package freaking.raspberry.me.dictccbot.commands;

import freaking.raspberry.me.dictccbot.model.Language;
import freaking.raspberry.me.dictccbot.model.TranslationDirection;
import freaking.raspberry.me.dictccbot.services.TranslationService;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Locale;

public class ChangeTranslationDirectionCommand extends BotCommand {
    private static final String COMMAND_ID = "changeTranslationDirection";
    private static final String DESCRIPTION = "change translation direction";

    public ChangeTranslationDirectionCommand() {
        super(COMMAND_ID, DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage sendMessage = new SendMessage();
        String message;
        if (strings.length != 2) {
            message = "Specify two language codes, like this: `/changetranslationdirection de en`";
        } else {
            Language languageFrom = Language.valueOf(strings[0].toUpperCase(Locale.ENGLISH));
            Language languageTo = Language.valueOf(strings[1].toUpperCase(Locale.ENGLISH));
            TranslationService.setTranslationDirection(new TranslationDirection(languageFrom, languageTo));
            message = "Languages changed!";
        }
        sendMessage.setChatId(chat.getId().toString());
        sendMessage.setText(message);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
