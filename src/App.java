import javax.imageio.IIOException;
import java.io.*;
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
        char mode = 'c'; // char mode = args[0]
        //// TEMPORARY


        switch (mode) {
            case 'c':
                System.out.println("Encoding... TODO:");

                FileInputStream fis = new FileInputStream(toCompress);
                Map<Character, Integer> orderedFrequencyTable = sortHashMapByValues(loadFileAsCharHashMap(fis));
                HuffmanCode huffmanCode = new HuffmanCode();

                huffmanCode.buildHuffmanTree(orderedFrequencyTable);

                FileInputStream fis2 = new FileInputStream(toCompress);
                String huffmanString = compress(fis2, huffmanCode.getHuffmanReferenceTable());

                // TODO: Don't print, instead output to file.
                System.out.println(huffmanString);
                System.out.println(huffmanString.length());

                break;
            case 'd':
                System.out.println("Decoding... TODO:");

                //TODO: Decode encoded file.
                break;
            default:
                System.out.println("Invalid Mode..");
                System.exit(-1);
                break;
        }
    }

    private static String compress(FileInputStream fis, Map<Character, String> huffmanMap) {
        StringBuilder huffmanEncodedString = new StringBuilder();
        try (InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            int singleCharInt;
            char singleChar;

            while ((singleCharInt = isr.read()) != -1) {
                singleChar = (char) singleCharInt;
                huffmanEncodedString.append(huffmanMap.get(singleChar));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return huffmanEncodedString.toString();
    }

    private static Map<Character, Integer> loadFileAsCharHashMap(FileInputStream fis) {
        Map<Character, Integer> charMap = new HashMap<>();
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

    public static LinkedHashMap<Character, Integer> sortHashMapByValues(Map<Character, Integer> passedMap) {
        List<Character> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

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




    public static void printMap(Map<Character, String> map) {
        for (Entry<Character, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() +"\t"+entry.getValue());
        }
        System.out.println("\n");
    }

}
