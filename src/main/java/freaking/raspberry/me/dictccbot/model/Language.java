package freaking.raspberry.me.dictccbot.model;

public enum Language {
    EN("English", Emoji.FLAG_GB),
    DE("Deutsch", Emoji.FLAG_DE);

    private final String languageName;
    private final Emoji emoji;

    Language(String languageName, Emoji emoji) {
        this.languageName = languageName;
        this.emoji = emoji;
    }

    public String getLanguageName() {
        return languageName;
    }

    public Emoji getEmoji() {
        return emoji;
    }
}
