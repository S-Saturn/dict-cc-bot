package freaking.raspberry.me.dictccbot.services;

import freaking.raspberry.me.dictccbot.model.Language;
import freaking.raspberry.me.dictccbot.model.TranslationDirection;
import freaking.raspberry.me.dictccbot.services.dictionary.Dictionary;
import freaking.raspberry.me.dictccbot.services.dictionary.Entry;
import freaking.raspberry.me.dictccbot.services.formatter.ResultTableFormatter;

import java.io.File;
import java.util.*;

public class TranslationService {
    private static TranslationDirection translationDirection = new TranslationDirection(Language.DE, Language.EN); // Setting default translation direction
    public static Map<TranslationDirection, Dictionary> dictionaryMap = new HashMap<>();

    public static String translate(String input) {
        List<Entry> exactEntries = getExactEntries(input);
        List<Entry> partialEntries = getPartialMatchedEntries(input);

        StringBuilder response = new StringBuilder();
        int counter = 1;
        int topEntryLimit = 10;
        counter = fillResponseWithEntries(exactEntries, response, counter, topEntryLimit);
        response.append("-----------------------\n");
        counter = fillResponseWithEntries(partialEntries, response, counter, topEntryLimit);
        return ResultTableFormatter.formatEntriesToTable(translationDirection, exactEntries); // TODO: add switch for exact/partial entries
    }

    private static int fillResponseWithEntries(List<Entry> entries, StringBuilder response, int counter, int topEntryLimit) {
        for (int i = 0; i < Math.min(entries.size(), topEntryLimit); i++) {
            Entry exactEntry = entries.get(i);
            response.append(counter)
                    .append(". ")
                    .append(exactEntry.getLanguage1())
                    .append(" - ")
                    .append(exactEntry.getLanguage2())
                    .append("\n");
            counter++;
        }
        return counter;
    }

    public static ArrayList<Entry> getExactEntries(String word) {
        Dictionary dictionary = dictionaryMap.get(translationDirection);
        List<Entry> exactEntries = new ArrayList<>(dictionary.getExactEntries(word));
        if (exactEntries.isEmpty()) {
            exactEntries.add(new Entry("Kein Eintrag gefunden", "No entry found"));
        }
        List<Entry> partialEntries = new ArrayList<>(dictionary.getPartialMatchedEntries(word));
        if (partialEntries.isEmpty()) {
            partialEntries.add(new Entry("Kein Eintrag gefunden", "No entry found"));
        }
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

    public static void setTranslationDirection(TranslationDirection translationDirection) {
        TranslationService.translationDirection = translationDirection;
    }
}
