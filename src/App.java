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
        String outputCompressed = "to_decompress.txt";
        String outputDecompressed = "decompressed.txt";
        char mode = 'd'; // char mode = args[0]
        //// TEMPORARY
        HuffmanCode huffmanCode;

        switch (mode) {
            case 'c':
                System.out.println("Encoding...");

                FileInputStream fis = new FileInputStream(toCompress);
                Map<Character, Integer> orderedFrequencyTable = sortHashMapByValues(loadFileAsCharHashMap(fis));
                huffmanCode = new HuffmanCode();

                huffmanCode.buildHuffmanTree(orderedFrequencyTable);

                FileInputStream fis2 = new FileInputStream(toCompress);
                String huffmanString = compress(fis2, huffmanCode.getHuffmanReferenceTable()).toString();
                huffmanCode.setEncodedLength(huffmanString.length());
                System.out.println(huffmanString);
                FileWriter.createFile(huffmanString, outputCompressed, huffmanCode);
                break;
            case 'd':
                System.out.println("Decoding...");

                FileInputStream fisDecompress = new FileInputStream(toDecompress);
                ArrayList<Object> files = loadFileToDecompress(fisDecompress);

                HuffmanCode huffmanCode2 = (HuffmanCode) files.get(0);
                byte[] huffmanByteArray = (byte[]) files.get(1);
                String decodedHuffmanString = huffmanCode2.decode(huffmanByteArray);

                break;
            default:
                System.out.println("Invalid Mode..");
                System.exit(-1);
                break;
        }
    }

    private static StringBuilder compress(FileInputStream fis, Map<Character, String> huffmanMap) {
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

        return huffmanEncodedString;
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

    public static ArrayList<Object> loadFileToDecompress(FileInputStream fis) {
        ArrayList<Object> objectList = new ArrayList<>();
        try {
            ObjectInputStream objIS = new ObjectInputStream(fis);
            return ((ArrayList<Object>) objIS.readObject());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Object>();
        }
    }


    public static void printMap(Map<Character, String> map) {
        for (Entry<Character, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() +"\t"+entry.getValue());
        }
        System.out.println("\n");
    }

}
