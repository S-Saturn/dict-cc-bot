package freaking.raspberry.me.dictccbot.services;

import freaking.raspberry.me.dictccbot.model.EntryPrecision;
import freaking.raspberry.me.dictccbot.model.Language;
import freaking.raspberry.me.dictccbot.model.TranslationDirection;
import freaking.raspberry.me.dictccbot.services.dictionary.Dictionary;
import freaking.raspberry.me.dictccbot.services.dictionary.Entry;

import java.io.File;
import java.util.*;

public class TranslationService {
    private static TranslationDirection translationDirection = new TranslationDirection(Language.DE, Language.EN); // Setting default translation direction
    private static EntryPrecision entryPrecision = EntryPrecision.EXACT; // Setting default entity precision

    public static Map<TranslationDirection, Dictionary> dictionaryMap = new HashMap<>();

    public static List<Entry> translate(String input) {
        List<Entry> entries;
        switch (entryPrecision) {
            case PARTIAL:
                entries = getPartialMatchedEntries(input);
                break;
            case EXACT:
            default:
                entries = getExactEntries(input);
                break;
        }
        return entries;
    }

    public static ArrayList<Entry> getExactEntries(String word) {
        Dictionary dictionary = dictionaryMap.get(translationDirection);
        return dictionary.getExactEntries(word);
    }

    public static ArrayList<Entry> getPartialMatchedEntries(String word) {
        Dictionary dictionary = dictionaryMap.get(translationDirection);
        return dictionary.getPartialMatchedEntries(word);
    }

    public static void loadDictionaries(String databaseDirectoryPath) {
        if (!dictionaryMap.containsKey(translationDirection) &&
                !dictionaryMap.containsKey(translationDirection.getSwappedTranslationDirection())) {
            dictionaryMap.put(translationDirection, getLoadedDictionary(databaseDirectoryPath, translationDirection.getLanguageFrom(), translationDirection.getLanguageTo()));
            dictionaryMap.put(translationDirection.getSwappedTranslationDirection(), getLoadedDictionary(databaseDirectoryPath, translationDirection.getLanguageTo(), translationDirection.getLanguageFrom()));
        }
    }

    private static Dictionary getLoadedDictionary(String databaseDirectoryPath, Language languageFrom, Language languageTo) {
        String dictionaryFile = databaseDirectoryPath
                + File.separator
                + languageFrom.name().toLowerCase(Locale.ENGLISH)
                + "-"
                + languageTo.name().toLowerCase(Locale.ENGLISH)
                + ".txt";
        return new Dictionary(dictionaryFile);
    }

    public static void swapTranslationDirection() {
        TranslationService.translationDirection = TranslationService.translationDirection.getSwappedTranslationDirection();
    }

    public static void changePrecision() {
        if (entryPrecision == EntryPrecision.EXACT) {
            entryPrecision = EntryPrecision.PARTIAL;
        } else {
            entryPrecision = EntryPrecision.EXACT;
        }
    }

    public static TranslationDirection getTranslationDirection() {
        return translationDirection;
    }

    public static EntryPrecision getEntryPrecision() {
        return entryPrecision;
    }
}
