package freaking.raspberry.me.dictccbot.model;

import java.util.Arrays;
import java.util.List;

public enum CustomCommandName {
    CHANGE_TRANSLATION_DIRECTION(Emoji.GLOBE_WITH_MERIGIANS),
    CHANGE_PRECISION(Emoji.BLACK_SQUARE_LARGE, Emoji.WHITE_SQUARE_BUTTON),
    NEXT_PAGE(Emoji.NEXT_TRACK),
    PREVIOUS_PAGE(Emoji.PREVIOUS_TRACK);

    private final List<Emoji> emojiList;

    CustomCommandName(Emoji... emojis) {
        this.emojiList = Arrays.asList(emojis);
    }

    public static CustomCommandName getCustomCommandNameByEmoji(String emojiString) {
        for (CustomCommandName value : CustomCommandName.values()) {
            for (Emoji emoji : value.emojiList) {
                if (emoji.getString().equals(emojiString)) {
                    return value;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    public List<Emoji> getEmojiList() {
        return emojiList;
    }
}
