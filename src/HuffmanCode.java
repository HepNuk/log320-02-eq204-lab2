import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HuffmanCode implements Serializable {
    private static final long serialVersionUID = 1L;
    private HuffmanNode rootNode;
    private int encodedLength;

    public HuffmanCode() { this.rootNode = null; }

    public HuffmanNode getRootNode() { return this.rootNode; }

    public void setEncodedLength(int encodedLength) { this.encodedLength = encodedLength; }

    public void buildHuffmanTree(Map<Character, Integer> characterFrequencyTable) {
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(characterFrequencyTable.size(), new HuffmanComparator());

        for (Map.Entry<Character, Integer> entry : characterFrequencyTable.entrySet()) {
            HuffmanNode newNode = new HuffmanNode(entry.getKey(), entry.getValue());
            queue.add(newNode);
        }

        HuffmanNode rootNode = null;
        while (queue.size() > 1) {
            HuffmanNode x = queue.peek();
            queue.poll();

            HuffmanNode y = queue.peek();
            queue.poll();

            HuffmanNode f = new HuffmanNode((x.getFreq() + y.getFreq()), x, y);
            rootNode = f;

            queue.add(f);
        }

        this.rootNode = rootNode;
    }

    public static String compress(FileInputStream fis, Map<Character, String> huffmanMap) {
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

    public String decode(byte[] byteArray) {
        char[] huffmanStringToDecode = byteArrayToString(byteArray).toCharArray();
        StringBuilder decompressedString = new StringBuilder();

        HuffmanNode currentNode = this.rootNode;
        for (char c : huffmanStringToDecode) {
            if (c == '0') currentNode = currentNode.getZero();
            else if (c == '1') currentNode = currentNode.getOne();

            if (currentNode.isLeaf()) {
                decompressedString.append(currentNode.getC());
                currentNode = this.rootNode;
            }
        }
        return decompressedString.toString();
    }

    public String byteArrayToString(byte[] byteArray) {
        StringBuilder huffmanString = new StringBuilder();

        for (byte b : byteArray) {
            huffmanString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));

        }
        return huffmanString.substring(0, this.encodedLength);
    }

    public static void buildHuffmanMapFromTree(Map<Character, String> map, HuffmanNode root, String s) {
        if (root == null) return;

        if (root.getZero() == null && root.getOne() == null) {
            map.put(root.getC(), s);
            return;
        }

        buildHuffmanMapFromTree(map, root.getZero(), s + "0");
        buildHuffmanMapFromTree(map, root.getOne(), s + "1");
    }

    public static byte[] huffmanTextToByteSequence(String huffmanText) throws Exception {
        int j = -1;

        int byteArraySize = huffmanText.length()/8;

        if (huffmanText.length()%8 != 0) byteArraySize++;

        byte[] byteArray = new byte[byteArraySize];

        for (int i=0; i < byteArraySize*8; i++) {
            if (i%8 == 0) {
                j++;
                byteArray[j] = 0x00;
            }

            byte tempByte;

            if (i > huffmanText.length()-1 || huffmanText.charAt(i) == '0') tempByte = 0x00;
            else if (huffmanText.charAt(i) == '1') tempByte = 0x01;
            else throw new Exception("Error: Char at " + i + " cannot be " + huffmanText.charAt(i));

            byteArray[j] = (byte) (tempByte | (byteArray[j] << 1));
        }
        return byteArray;
    }
//    /// For Testing
//    public void printCode(HuffmanNode root, String s) {
//        if (root.getZero() == null && root.getOne() == null) {
//            System.out.println(root.getC() + ":" + s);
//            return;
//        }
//        if (root != null) {
//            printCode(root.getZero(), s + "0");
//            printCode(root.getOne(), s + "1");
//        }
//    }
}
