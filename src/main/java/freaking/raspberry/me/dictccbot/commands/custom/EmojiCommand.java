package freaking.raspberry.me.dictccbot.commands.custom;

import freaking.raspberry.me.dictccbot.model.Emoji;

public interface EmojiCommand extends CustomCommand {
    String execute();

    default Emoji getEmoji() {
        return getCustomCommandName().getEmojiList().get(0);
    }
}
