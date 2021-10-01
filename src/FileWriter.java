import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class FileWriter {

    public static void createFile(String huffmanText, String filename, HuffmanCode huffmanCode) {
        try {
            ArrayList<Object> objectList = new ArrayList<>();
            byte[] huffmanByteSequence = huffmanTextToByteSequence(huffmanText);
            FileOutputStream fos = new FileOutputStream(new File(filename));
            ObjectOutputStream objos = new ObjectOutputStream(fos);
            objectList.add(huffmanCode);
            objectList.add(huffmanByteSequence);
            objos.writeObject(objectList);
            objos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static byte[] huffmanTextToByteSequence(String huffmanText) throws Exception {
        int j = -1;

        int byteArraySize = huffmanText.length()/8;

        if (huffmanText.length()%8 != 0) byteArraySize++;

        byte[] byteArray = new byte[byteArraySize];

        for (int i=0; i < huffmanText.length(); i++) {
            if (i%8 == 0) {
                j++;
                byteArray[j] = 0x00;
            }

            byte tempByte;

            if (huffmanText.charAt(i) == '1') tempByte = 0x01;
            else if (huffmanText.charAt(i) == '0') tempByte = 0x00;
            else throw new Exception("Error: Char at " + i + " cannot be " + huffmanText.charAt(i));

            byteArray[j] = (byte) (tempByte | (byteArray[j] << 1));
        }

        return byteArray;
    }
}
