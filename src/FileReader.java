import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileReader {
    public static Map<Character, Integer> loadFileAsCharHashMap(FileInputStream fis) {
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
            e.printStackTrace();
            System.out.println("IO EXCEPTION, EXITING");
            System.exit(-1);
        }
        return charMap;
    }

    public static ArrayList<Object> loadFilesToDecompress(FileInputStream fis) {
        ArrayList<Object> objectList = new ArrayList<>();
        try {
            ObjectInputStream objIS = new ObjectInputStream(fis);
            return ((ArrayList<Object>) objIS.readObject());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Object>();
        }
    }
}
