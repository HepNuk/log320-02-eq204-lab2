import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Map.Entry;

public class App {
    public static void main(String[] args) throws IOException {
        //// TEMPORARY
        String toCompress = "to_compress.txt";
        String toDecompress = "to_decompress.txt";
        String outputCompressed = "compressed.txt";
        String outputDecompressed = "decompressed.txt";
        //// TEMPORARY
        Map<Character, Integer> sortedMap = sortHashMapByValues(loadCharsInHashMap(toCompress));
        printMap(sortedMap);

        System.exit(0);

        /// ------------------------ ///
        boolean validMode = false;
        while (!validMode) {
            System.out.print("Mode ? [c|d]: ");
            char mode;
            try {
                mode = (char) System.in.read();
            } catch (IOException e) {
                mode = '!';
            }

            switch (mode) {
                case 'c':
                    System.out.println("Encoding...");
                    validMode = true;
                    break;
                case 'd':
                    System.out.println("Decoding...");
                    validMode = true;
                    break;
                default:
                    System.out.println("Wrong Mode..");
                    break;
            }
        }
    }

    private static Map<Character, Integer> loadCharsInHashMap(String filename) throws FileNotFoundException {
        Map<Character, Integer> charMap = new HashMap<>();

        FileInputStream fis = new FileInputStream(filename);
        try (InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            int singleCharInt;
            char singleChar;


            while ((singleCharInt = isr.read()) != -1) {
                singleChar = (char) singleCharInt;
                if (charMap.get(singleChar) != null) {
                    charMap.replace(singleChar, charMap.get(singleChar) + 1);
                } else {
                    charMap.put(singleChar, 1);
                }
            }
        } catch (IOException e) {
            System.out.println("IO EXCEPTION, EXITING");
            System.exit(-1);
        }

        return charMap;
    }

    public static void printMap(Map<Character, Integer> map) {
        for (Entry<Character, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() +"\t"+entry.getValue());
        }
        System.out.println("\n");
    }

    public static LinkedHashMap<Character, Integer> sortHashMapByValues(Map<Character, Integer> passedMap) {
        List<Character> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        mapValues.sort(Collections.reverseOrder());
        mapKeys.sort(Collections.reverseOrder());

        LinkedHashMap<Character, Integer> sortedMap = new LinkedHashMap<>();

        for (Integer val : mapValues) {
            Iterator<Character> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Character key = keyIt.next();
                Integer comp1 = passedMap.get(key);

                if (comp1.equals(val)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }


    private static void encodeData() {}
    private static void decodeData() {}
}
