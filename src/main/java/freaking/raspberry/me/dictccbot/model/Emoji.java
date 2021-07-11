package freaking.raspberry.me.dictccbot.model;

public enum Emoji {
    GLOBE_WITH_MERIGIANS("\uD83C\uDF10"),
    FLAG_DE("\uD83C\uDDE9\uD83C\uDDEA"),
    FLAG_GB("\uD83C\uDDEC\uD83C\uDDE7"),
    ARROW_RIGHT("\u27A1"),
    NEXT_TRACK("\u23ED"),
    PREVIOUS_TRACK("\u23EE"),
    WHITE_SQUARE_BUTTON("\uD83D\uDD33"),
    BLACK_SQUARE_LARGE("\u2B1B");

    private final String string;

    Emoji(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
