import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileWriter {

    public static void writeHuffmanStringToFile(String huffmanText, String filename, HuffmanCode huffmanCode) {
        try {
            ArrayList<Object> objectList = new ArrayList<>();
            byte[] huffmanByteSequence = HuffmanCode.huffmanTextToByteSequence(huffmanText);
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            objectList.add(huffmanCode);
            objectList.add(huffmanByteSequence);
            oos.writeObject(objectList);
            oos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage()+ ">??");
        }
    }

    public static void writeTextToFile(String text, String filename) {
        try {
            PrintStream out = new PrintStream(new FileOutputStream(filename), false, "UTF-8");
            out.print(text);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
