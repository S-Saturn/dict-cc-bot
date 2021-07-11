package freaking.raspberry.me.dictccbot.commands.custom;

import freaking.raspberry.me.dictccbot.model.CustomCommandName;
import freaking.raspberry.me.dictccbot.services.formatter.ResultTableFormatter;

public class NextPageCommand implements CustomCommand {
    private final static CustomCommandName customCommandName = CustomCommandName.NEXT_PAGE;

    @Override
    public String execute() {
        ResultTableFormatter.increasePageNumber();
        return "Next page";
    }

    @Override
    public CustomCommandName getCustomCommandName() {
        return customCommandName;
    }
}
