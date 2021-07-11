package freaking.raspberry.me.dictccbot.model;

public enum EntryPrecision {
    EXACT(Emoji.BLACK_SQUARE_LARGE),
    PARTIAL(Emoji.WHITE_SQUARE_BUTTON);

    private final Emoji emoji;

    EntryPrecision(Emoji emoji) {
        this.emoji = emoji;
    }

    public Emoji getEmoji() {
        return emoji;
    }
}
