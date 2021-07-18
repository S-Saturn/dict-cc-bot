package freaking.raspberry.me.dictccbot.commands.custom;

import freaking.raspberry.me.dictccbot.model.CustomCommandName;

public class AddToVocabularyCommand implements CustomCommand {
    private final static CustomCommandName customCommandName = CustomCommandName.ADD_TO_VOCABULARY;

    public String execute(int entryNumber) {

        // TODO: add word to vocabulary
        return "Wort wurde zum Wortschatz hinzugefügt";
    }

    @Override
    public CustomCommandName getCustomCommandName() {
        return customCommandName;
    }
}
