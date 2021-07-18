package freaking.raspberry.me.dictccbot.commands.custom;

import freaking.raspberry.me.dictccbot.model.CustomCommandName;
import freaking.raspberry.me.dictccbot.model.Emoji;
import freaking.raspberry.me.dictccbot.model.TranslationDirection;
import freaking.raspberry.me.dictccbot.services.TranslationService;

public class ChangeTranslationDirectionCommand implements CustomCommand, EmojiCommand {
    private final static CustomCommandName customCommandName = CustomCommandName.CHANGE_TRANSLATION_DIRECTION;

    @Override
    public String execute() {
        TranslationService.swapTranslationDirection();
        TranslationDirection newTranslationDirection = TranslationService.getTranslationDirection();

        return "Translation direction changed to "
                + newTranslationDirection.getLanguageFrom().getEmoji().getString()
                + " " + Emoji.ARROW_RIGHT.getString() + " "
                + newTranslationDirection.getLanguageTo().getEmoji().getString();
    }

    public CustomCommandName getCustomCommandName() {
        return customCommandName;
    }
}
