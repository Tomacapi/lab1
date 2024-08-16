import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

public class Main { 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("a.txt"));
        String[] parts;
        Map<String, Integer> counterMap = new HashMap<>();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                parts = line.toLowerCase().split("[^A-Za-z]+");
                for (String word : parts) {
                    counterMap.compute(word, (key, value) -> value == null ? 1 : value + 1);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            br.close();
        }
        Map<String, Integer> sorted = counterMap.entrySet()
                .stream()
                .sorted(reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e2, LinkedHashMap::new));

        int totalWordCount = 0;
        for (int f : sorted.values()) {
            totalWordCount += f;
        }
        
        String eol = System.lineSeparator();

        try (Writer writer = new FileWriter("table.csv")) {
            for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
                writer.append(entry.getKey())
                        .append(';')
                        .append(entry.getValue().toString())
                        .append(';');
                float v = entry.getValue();
                v = v / totalWordCount * 100;
                String y = String.valueOf(v);
                writer.append(y).append(eol);

            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
