package freaking.raspberry.me.dictccbot.commands.custom;

import freaking.raspberry.me.dictccbot.model.CustomCommandName;
import freaking.raspberry.me.dictccbot.services.formatter.ResultTableFormatter;

public class PreviousPageCommand implements CustomCommand, EmojiCommand {
    private final static CustomCommandName customCommandName = CustomCommandName.PREVIOUS_PAGE;

    @Override
    public String execute() {
        ResultTableFormatter.decreasePageNumber();
        return "Previous page";
    }

    @Override
    public CustomCommandName getCustomCommandName() {
        return customCommandName;
    }
}
