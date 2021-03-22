package freaking.raspberry.me.dictccbot.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class HelpCommand extends BotCommand {
    private static final String COMMAND_ID = "help";
    private static final String DESCRIPTION = "show help message";
    private static final String MESSAGE_EN= "Send any English text message to quickly get its Russian translation. " +
            "Send any Russian text message to quickly get its English translation.";
    private static final String MESSAGE_RU= "Отправь любое сообщение на английском языке, чтобы быстро получить его перевод на русский язык. " +
            "Отправь любое сообщение на русском языке, чтобы быстро получить его перевод на английский язык.";
    private static final String MESSAGE_YT = "Powered by Yandex.Translate";

    public HelpCommand() {
        super(COMMAND_ID, DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {


        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        String stringBuilder = "\uD83C\uDDEC\uD83C\uDDE7" +
                MESSAGE_EN +
                "\r\n\n" +
                "\uD83C\uDDF7\uD83C\uDDFA" +
                MESSAGE_RU +
                "\r\n\n" +
                MESSAGE_YT;
        helpMessage.setText(stringBuilder);

        try {
            absSender.execute(helpMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
