import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

public class Main { 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("a.txt"));
        String wholeString;
        String[] parts;
        Map<String, Integer> counterMap;
        try {
            wholeString = "";
            String line;
            while ((line = br.readLine()) != null) {
                wholeString += line + System.lineSeparator();
            }
            parts = wholeString.toLowerCase().split("[^A-Za-z]+");
            counterMap = new HashMap<>();
            int i;
            for (i = 0; i < parts.length; i++) {
                String word = parts[i];
                counterMap.compute(word, (key, value) -> value == null ? 1 : value + 1);
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

        int sum = 0;
        for (int f : sorted.values()) {
            sum += f;
        }

        File csvFile = new File("table.csv");
        String eol = System.lineSeparator();

        try (Writer writer = new FileWriter("table.csv")) {
            for (Map.Entry<String, Integer> entry : sorted.entrySet()) {
                writer.append(entry.getKey())
                        .append(';')
                        .append(entry.getValue().toString())
                        .append(';');
                float v = entry.getValue();
                v = v / sum * 100;
                String y = String.valueOf(v);
                writer.append(y).append(eol);

            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
