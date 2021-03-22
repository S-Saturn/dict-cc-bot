package freaking.raspberry.me.dictccbot;

import freaking.raspberry.me.dictccbot.services.TranslationService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;

public class DictCcBotApp {
    private static final String ENV_VARS_NOT_FOUND_MESSAGE = "Cannot run the translate bot: make sure that the " +
            "environment variables TRANSLATE_BOT_NAME, TRANSLATE_BOT_TOKEN, and DICT_CC_BOT_DIR_PATH are defined " +
            "and accessible";
    private static final String DB_DIR_NOT_FOUND = "Database file directory from the DICT_CC_BOT_DIR_PATH environment " +
            "variable is not found";
    private static final String TELEGRAM_API_NOT_ACCESSIBLE_MESSAGE = "Cannot access Telegram API: make sure that " +
            "the webhook is free";

    public static void main(String[] args) {
        String botName;
        String botToken;
        String databaseDirectoryPath;

        try {
            botName = System.getenv("DICT_CC_BOT_NAME");
            botToken = System.getenv("DICT_CC_BOT_TOKEN");
            databaseDirectoryPath = System.getenv("DICT_CC_BOT_DIR_PATH");
        } catch (Exception e) {
            System.out.println(ENV_VARS_NOT_FOUND_MESSAGE);
            e.printStackTrace();
            return;
        }

        if (botName == null || botToken == null || databaseDirectoryPath == null) {
            System.out.println(ENV_VARS_NOT_FOUND_MESSAGE);
            return;
        }

        if (new File(databaseDirectoryPath).exists()) {
            TranslationService.loadDictionary(databaseDirectoryPath);
        } else {
            System.out.println(DB_DIR_NOT_FOUND);
            return;
        }

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            DictCcBot dictCcBot = new DictCcBot(botName, botToken);
            telegramBotsApi.registerBot(dictCcBot);
        } catch (TelegramApiException e) {
            System.out.println(TELEGRAM_API_NOT_ACCESSIBLE_MESSAGE);
        }
    }
}
