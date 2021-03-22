package freaking.raspberry.me.dictccbot.services.dictionary;

import freaking.raspberry.me.dictccbot.model.Language;
import freaking.raspberry.me.dictccbot.model.TranslationDirection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Dictionary {
    private static final String LOADING_DICT_ERROR_MSG = "Error loading dictionary from file. Make sure that the " +
            "directory specified in environment variable DICT_CC_BOT_DIR_PATH contains database dictionary files.";

    private final Map<String, Set<UUID>> wordMap = new HashMap<>();
    private final Map<UUID, Entry> entryMap = new HashMap<>();
    private TranslationDirection translationDirection;

    public Dictionary(String file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

            String line = br.readLine();
            while (line != null) {
                if (!line.startsWith("#")) {
                    while (line.matches(".*&#.+?;.*")) {
                        char c = (char) Integer.parseInt(line.replaceAll(".*&#(.+?);.*", "$1"));
                        line = line.replaceAll("&#.+?;", String.valueOf(c));
                    }
                    String[] dataArray = line.split("\t");
                    try {
                        Entry entry = new Entry(dataArray[0], dataArray[1]);
                        try {
                            entry.setType(dataArray[2]);
                            entry.setClassification(dataArray[3]);
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                        entryMap.put(entry.getId(), entry);
                        for (String word : entry.getAllWords()) {
                            if (!wordMap.containsKey(word)) {
                                wordMap.put(word, new HashSet<>());
                            }
                            wordMap.get(word).add(entry.getId());
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                } else {
                    if (line.matches(".*\\s\\w+?-\\w+?\\s.*")) {
                        Language languageFrom = Language.valueOf(line.replaceAll(".*\\s(\\w+?)-(\\w+?)\\s.*", "$1"));
                        Language languageTo = Language.valueOf(line.replaceAll(".*\\s(\\w+?)-(\\w+?)\\s.*", "$2"));
                        translationDirection = new TranslationDirection(languageFrom, languageTo);
                    }
                }

                line = br.readLine();
            }

            br.close();

        } catch (Exception e) {
            System.out.println(LOADING_DICT_ERROR_MSG);
        }
    }

    public ArrayList<String> getAllWords() {
        return new ArrayList<>(wordMap.keySet());
    }

    public ArrayList<Entry> getExactEntries(String query) {
        Map<UUID, Integer> ids = new HashMap<>();
        ArrayList<Entry> entries = new ArrayList<>();
        for (String word : query.split("[\\s-]")) {
            fillIdsMap(ids, word);
        }

        for (UUID id : ids.keySet()) {
            Entry entry = entryMap.get(id);
            entry.setWordHit(ids.get(id));
            entries.add(entry);
        }

        entries.sort(new EntrySorter());

        return entries;
    }

    private void fillIdsMap(Map<UUID, Integer> ids, String word) {
        Set<UUID> idList = wordMap.get(word.toLowerCase());
        if (idList != null) {
            for (UUID id : idList) {
                if (!ids.containsKey(id)) {
                    ids.put(id, 0);
                }
                ids.put(id, ids.get(id) + 1);
            }
        }
    }

    public ArrayList<Entry> getPartialMatchedEntries(String query) {
        Map<UUID, Integer> ids = new HashMap<>();
        ArrayList<Entry> entries = new ArrayList<>();

        Set<String> matchingWords = new HashSet<>();
        for (String word : query.toLowerCase().split("[\\s-]")) {
            for (String key : wordMap.keySet()) {
                if (!key.equals(word)) {
                    if (key.matches(".*" + word.toLowerCase()) || key.matches(word.toLowerCase() + ".*")) {
                        matchingWords.add(key);
                    }
                }
            }
        }

        for (String word : matchingWords) {
            fillIdsMap(ids, word);
        }

        for (UUID id : ids.keySet()) {
            Entry entry = entryMap.get(id);
            entry.setWordHit(ids.get(id));
            entries.add(entry);
        }

        entries.sort(new EntrySorter());

        return entries;
    }
}
