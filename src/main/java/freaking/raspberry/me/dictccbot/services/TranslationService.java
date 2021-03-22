package freaking.raspberry.me.dictccbot.services;

import freaking.raspberry.me.dictccbot.model.Language;
import freaking.raspberry.me.dictccbot.model.TranslationDirection;
import freaking.raspberry.me.dictccbot.services.dictionary.Dictionary;
import freaking.raspberry.me.dictccbot.services.dictionary.Entry;

import java.util.ArrayList;

public class TranslationService {
    private static TranslationDirection translationDirection;
    public static Dictionary dict;

    public TranslationService() {
        translationDirection = new TranslationDirection(Language.DE, Language.EN); // Setting default translation direction
    }

    public static String translate(String input) {
        //HERE WE WILL TRANSLATE INPUT USING DATABASE FROM FILE ACCORDING TO translationDirection
        return "Translation!";
    }

    public static ArrayList<Entry> getExactEntries(String word) {
        return dict.getExactEntries(word);// dict.getExactEntries(word);
    }

    public static ArrayList<Entry> getPartialMatchedEntries(String word) {
        return dict.getPartialMatchedEntries(word);// dict.getExactEntries(word);
    }

    public static void loadDictionary(String dictFile) {
        dict = new Dictionary(dictFile);
    }

    public static void setTranslationDirection(TranslationDirection translationDirection) {
        TranslationService.translationDirection = translationDirection;
    }
}
