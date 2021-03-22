package freaking.raspberry.me.dictccbot.model;

public class TranslationDirection {
    private final Language languageFrom;
    private final Language languageTo;

    public TranslationDirection(Language languageFrom, Language languageTo) {
        this.languageFrom = languageFrom;
        this.languageTo = languageTo;
    }

    public Language getLanguageFrom() {
        return languageFrom;
    }

    public Language getLanguageTo() {
        return languageTo;
    }
}
