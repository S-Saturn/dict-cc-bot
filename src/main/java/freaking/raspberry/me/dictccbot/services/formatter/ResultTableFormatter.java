package freaking.raspberry.me.dictccbot.services.formatter;

import freaking.raspberry.me.dictccbot.services.dictionary.Entry;

import java.util.List;

public class ResultTableFormatter {
    private final static int ROWS_ON_PAGE = 10;
    private static int CURRENT_PAGE = 0;

    public static String formatEntriesToTable(List<Entry> entryList) {
        List<Entry> entriesOfPage = entryList.subList(CURRENT_PAGE * ROWS_ON_PAGE, Math.min((CURRENT_PAGE + 1) * ROWS_ON_PAGE, entryList.size()));
        if (entriesOfPage.isEmpty()) {
            return "<b>No entries found</b>";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= entriesOfPage.size(); i++) {
            Entry entry = entriesOfPage.get(i - 1);
            result.append("<b>")
                    .append(i)
                    .append(". ")
                    .append(entry.getLanguage1())
                    .append(" \u27A1 ")
                    .append(entry.getLanguage2())
                    .append("</b>\n")
                    .append(entry.getType());
            if (entry.getClassification() != null && !entry.getClassification().isEmpty()) {
                result.append(", ")
                        .append(entry.getClassification());
            }
            result.append("\n\n");
        }
        return result.toString();
    }
}
