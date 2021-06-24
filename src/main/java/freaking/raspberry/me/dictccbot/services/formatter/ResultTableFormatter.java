package freaking.raspberry.me.dictccbot.services.formatter;

import freaking.raspberry.me.dictccbot.model.TranslationDirection;
import freaking.raspberry.me.dictccbot.services.dictionary.Entry;

import java.util.List;

public class ResultTableFormatter {
    private final static int ROWS_ON_PAGE = 10;
    private static int CURRENT_PAGE = 0;

    public static String formatEntriesToTable(TranslationDirection translationDirection, List<Entry> entryList) {
        List<Entry> entriesOfPage = entryList.subList(CURRENT_PAGE * ROWS_ON_PAGE, Math.min((CURRENT_PAGE + 1) * ROWS_ON_PAGE, entryList.size()));

        StringBuilder table = new StringBuilder();
        if (!entriesOfPage.isEmpty()) {
            table.append("<pre>\n");
            table.append("|");
            table.append(translationDirection.getLanguageFrom().getLanguageName());
            table.append("|");
            table.append(translationDirection.getLanguageTo().getLanguageName());
            table.append("|\n");
            table.append("|");
            for (int i = 0; i < translationDirection.getLanguageFrom().getLanguageName().length(); i++) {
                table.append("-");
            }
            table.append("|");
            for (int i = 0; i < translationDirection.getLanguageTo().getLanguageName().length(); i++) {
                table.append("-");
            }
            table.append("|\n");
            for (Entry entry : entriesOfPage) {
                table.append("|");
                table.append(entry.getLanguage1());
                table.append("|");
                table.append(entry.getLanguage2());
                table.append("|\n");
            }
            table.append("</pre>");
        }
        return table.toString();
    }
}
