package freaking.raspberry.me.dictccbot.commands.custom;

import freaking.raspberry.me.dictccbot.model.CustomCommandName;

public interface CustomCommand {
    String execute();

    CustomCommandName getCustomCommandName();
}
