package freaking.raspberry.me.dictccbot.services.formatter;

import freaking.raspberry.me.dictccbot.services.dictionary.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ResultTableFormatter {
    private static final int ENTRIES_COUNT_TO_DISPLAY = 5;

    private static int pageNumber = 1;
    private static final List<Entry> currentRequestEntries = new ArrayList<>();
    private static final TreeMap<Integer, String> currentPageEntries = new TreeMap<>();

    public static String formatExistingEntriesToTable() {
        return formatEntriesToTable(currentRequestEntries);
    }

    public static String formatNewEntriesToTable(List<Entry> entryList) {
        currentRequestEntries.clear();
        currentRequestEntries.addAll(entryList);
        pageNumber = 1;

        return formatEntriesToTable(entryList);
    }

    public static String formatEntriesToTable(List<Entry> entryList) {
        TreeMap<Integer, String> resultPageEntriesMap = new TreeMap<>();
        List<Entry> entriesOfPage = entryList.subList((pageNumber - 1) * ENTRIES_COUNT_TO_DISPLAY, Math.min(pageNumber * ENTRIES_COUNT_TO_DISPLAY, entryList.size()));
        if (entriesOfPage.isEmpty()) {
            currentRequestEntries.clear();
            currentPageEntries.clear();
            return "*No entries found*";
        }
        StringBuilder resultMessageBuilder = new StringBuilder();
        for (int i = 1; i <= entriesOfPage.size(); i++) {
            Entry entry = entriesOfPage.get(i - 1);
            StringBuilder pageEntryStringBuilder = new StringBuilder();
            pageEntryStringBuilder.append((pageNumber - 1) * ENTRIES_COUNT_TO_DISPLAY + i)
                    .append(". ")
                    .append(entry.getLanguage1())
                    .append(" \u27A1 ")
                    .append(entry.getLanguage2());
            StringBuilder markdownFormattedstringBuilder = new StringBuilder();
            markdownFormattedstringBuilder.append("*")
                    .append(pageEntryStringBuilder)
                    .append("*\n");
            boolean typeOrClassificationPresent = false;
            if (entry.getType() != null && entry.getType() != null) {
                typeOrClassificationPresent = true;
                markdownFormattedstringBuilder.append(entry.getType());
                if (entry.getClassification() != null && !entry.getClassification().isEmpty()) {
                    markdownFormattedstringBuilder.append(", ");
                }
            }
            if (entry.getClassification() != null && !entry.getClassification().isEmpty()) {
                typeOrClassificationPresent = true;
                markdownFormattedstringBuilder.append(entry.getClassification());
            }
            if (typeOrClassificationPresent) {
                markdownFormattedstringBuilder.append("\n");
            }
            markdownFormattedstringBuilder.append("\n");
            resultPageEntriesMap.put((pageNumber - 1) * ENTRIES_COUNT_TO_DISPLAY + i, pageEntryStringBuilder.toString());
            resultMessageBuilder.append(markdownFormattedstringBuilder);
        }
        currentPageEntries.clear();
        currentPageEntries.putAll(resultPageEntriesMap);
        return resultMessageBuilder.toString();
    }

    public static TreeMap<Integer, String> getCurrentPageEntries() {
        return currentPageEntries;
    }

    public static void increasePageNumber() {
        pageNumber++;
    }

    public static void decreasePageNumber() {
        pageNumber--;
    }

    public static int getPageNumber() {
        return pageNumber;
    }

    public static int getTotalPagesNumber() {
        int div = currentRequestEntries.size() / ENTRIES_COUNT_TO_DISPLAY;
        if (currentRequestEntries.size() % ENTRIES_COUNT_TO_DISPLAY == 0) {
            return div;
        } else {
            return div + 1;
        }
    }

}
