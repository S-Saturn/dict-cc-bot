package freaking.raspberry.me.dictccbot.model;

public enum Language {
    EN("English"),
    DE("Deutsch");

    private final String languageName;

    Language(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageName() {
        return languageName;
    }
}
