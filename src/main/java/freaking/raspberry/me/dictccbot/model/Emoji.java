package freaking.raspberry.me.dictccbot.model;

public enum Emoji {
    PLANET("\uD83C\uDF0D"),
    FLAG_DE("\uD83C\uDDE9\uD83C\uDDEA"),
    FLAG_GB("\uD83C\uDDEC\uD83C\uDDE7"),
    ARROW_RIGHT("\u27A1"),
    NEXT_TRACK("⏭"),
    PREVIOUS_TRACK("⏮");

    private final String string;

    Emoji(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
