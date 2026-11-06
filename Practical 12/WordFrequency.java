import java.util.*;

public class WordFrequency {
    public static void main(String[] args) {
        String paragraph = "Java is a popular programming language. Java is object oriented, and Java is powerful and fast!";

        System.out.println("Paragraph:\n\"" + paragraph + "\"\n");

        // Normalize text and split into words
        String cleanedText = paragraph.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] words = cleanedText.split("\\s+");

        // Count frequency using HashMap
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
            }
        }

        // Convert map entries to list for sorting
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(freqMap.entrySet());

        // Sort by frequency (highest first) using Comparator
        entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                // Descending frequency order; if equal, alphabetical order
                int comp = e2.getValue().compareTo(e1.getValue());
                if (comp == 0) {
                    return e1.getKey().compareTo(e2.getKey());
                }
                return comp;
            }
        });

        System.out.println("--- Words Sorted by Frequency (Highest First) ---");
        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.printf("%-15s : %d%n", entry.getKey(), entry.getValue());
        }
    }
}

