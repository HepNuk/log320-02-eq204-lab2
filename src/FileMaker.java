import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

// https://stackoverflow.com/questions/8517323/how-to-convert-map-to-bytes-and-save-to-internal-storage

public class FileMaker {
    public static void writeCompressedFile(String filename, StringBuilder huffmanText, Map<Character, String> huffmanRefTable) throws IOException {
        // Convert map to byte array
        deleteIfFileExists(filename);
    }

    public static void writeToFile() {

    }

    public static void deleteIfFileExists(String path) {
        File f = new File(path);
        try {
            f.delete();
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String to8bit(String binary) {
        String zeros = "";

        for (int i = 0; i != (8 - binary.length()); i++) {
            zeros += "0";
        }
        return zeros + binary;
    }

    private static void writeHeader(String header, String filename) {
        for (char c : header.toCharArray()) {
            String section = to8bit(Integer.toBinaryString(c));
        }
    }

    public static void writeBody(String huffmanText) {
        int arraySize = (int) Math.ceil(huffmanText.length() / 8.d);
        byte[] byteArray = new byte[arraySize];

        for (int i = 0; i < arraySize*8 - huffmanText.length(); i++) {

        }
        for (int i = 0; i < arraySize; i++) {

            for (int j = 0; j < 8; j++) {
                huffmanText.charAt(8 * i + j);
            }

            byteArray[i] = 5;
        }
    }

    private static void writeCompressedBody() {

    }
}
