package freaking.raspberry.me.dictccbot.model;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TranslationDirection {
    @NotNull
    private final Language languageFrom;
    @NotNull
    private final Language languageTo;

    public TranslationDirection(Language languageFrom, Language languageTo) {
        this.languageFrom = languageFrom;
        this.languageTo = languageTo;
    }

    public TranslationDirection getSwappedTranslationDirection() {
        return new TranslationDirection(languageTo, languageFrom);
    }

    public Language getLanguageFrom() {
        return languageFrom;
    }

    public Language getLanguageTo() {
        return languageTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslationDirection that = (TranslationDirection) o;
        return languageFrom == that.languageFrom && languageTo == that.languageTo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(languageFrom, languageTo);
    }
}
