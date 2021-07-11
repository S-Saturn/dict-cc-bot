package freaking.raspberry.me.dictccbot.services.formatter;

import freaking.raspberry.me.dictccbot.services.dictionary.Entry;

import java.util.ArrayList;
import java.util.List;

public class ResultTableFormatter {
    private static final int ENTRIES_COUNT_TO_DISPLAY = 10;

    private static int pageNumber = 0;
    private static final List<Entry> currentRequestEntries = new ArrayList<>();

    public static String formatExistingEntriesToTable() {
        return formatEntriesToTable(currentRequestEntries);
    }

    public static String formatNewEntriesToTable(List<Entry> entryList) {
        currentRequestEntries.clear();
        currentRequestEntries.addAll(entryList);
        pageNumber = 0;

        return formatEntriesToTable(entryList);
    }

    public static String formatEntriesToTable(List<Entry> entryList) {
        List<Entry> entriesOfPage = entryList.subList(pageNumber * ENTRIES_COUNT_TO_DISPLAY, Math.min((pageNumber + 1) * ENTRIES_COUNT_TO_DISPLAY, entryList.size()));
        if (entriesOfPage.isEmpty()) {
            return "*No entries found*";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= entriesOfPage.size(); i++) {
            Entry entry = entriesOfPage.get(i - 1);
            result.append("*")
                    .append(i)
                    .append(". ")
                    .append(entry.getLanguage1())
                    .append(" \u27A1 ")
                    .append(entry.getLanguage2())
                    .append("*\n");
            boolean typeOrClassificationPresent = false;
            if (entry.getType() != null && entry.getType() != null) {
                typeOrClassificationPresent = true;
                result.append(entry.getType());
                if (entry.getClassification() != null && !entry.getClassification().isEmpty()) {
                    result.append(", ");
                }
            }
            if (entry.getClassification() != null && !entry.getClassification().isEmpty()) {
                typeOrClassificationPresent = true;
                result.append(entry.getClassification());
            }
            if (typeOrClassificationPresent) {
                result.append("\n");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static void increasePageNumber() {
        pageNumber++;
    }

    public static void decreasePageNumber() {
        pageNumber--;
    }
}
