package freaking.raspberry.me.dictccbot.model;

public enum CustomCommandName {
    CHANGE_TRANSLATION_DIRECTION(Emoji.PLANET),
    NEXT_PAGE(Emoji.NEXT_TRACK),
    PREVIOUS_PAGE(Emoji.PREVIOUS_TRACK);

    private final Emoji emoji;

    CustomCommandName(Emoji emoji) {
        this.emoji = emoji;
    }

    public static CustomCommandName getCustomCommandNameByEmoji(String emojiString) {
        for (CustomCommandName value : CustomCommandName.values()) {
            if (value.emoji.getString().equals(emojiString)) {
                return value;
            }
        }
        throw new IllegalArgumentException();
    }

    public Emoji getEmoji() {
        return emoji;
    }
}
