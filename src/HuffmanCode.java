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

    /**
     * Uses a priority queue to build the huffman tree based on the frequency of a given character.
     */
    public void buildHuffmanTree(Map<Character, Integer> characterFrequencyTable) {
        // Make priority queue with huffman comparator which compares the value of node.getFreq()
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(characterFrequencyTable.size(), new HuffmanComparator());

        // Create a HuffmanNode for every character in the reference table and add it to the priority queue
        for (Map.Entry<Character, Integer> entry : characterFrequencyTable.entrySet()) {
            HuffmanNode newNode = new HuffmanNode(entry.getKey(), entry.getValue());
            queue.add(newNode);
        }

        // Set rootNode as null for start
        HuffmanNode rootNode = null;

        // Loop while the queue still has values in it.
        while (queue.size() > 1) {
            // get and delete the 2 first values in the queue
            // these nodes are the nodes with the lowest frequency
            HuffmanNode x = queue.peek();
            queue.poll();
            HuffmanNode y = queue.peek();
            queue.poll();

            // create a parent node for both nodes we just took out and set its
            // frequency to the sum of the children
            HuffmanNode f = new HuffmanNode((x.getFreq() + y.getFreq()), x, y);

            // set the root node to the new node we created.
            rootNode = f;

            // add this new node to the queue.
            queue.add(f);
        }

        this.rootNode = rootNode;
    }

    /**
     * Compresses the text in a file opened in fis (FileInputString) using a map of
     * each character (the key) and its huffman code (the value)
     *
     * @param fis
     * @param huffmanMap
     * @return the encoded huffman string
     */
    public static String compress(FileInputStream fis, Map<Character, String> huffmanMap) {
        // StringBuilder to build the encoded string
        StringBuilder huffmanEncodedString = new StringBuilder();

        try (InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
            int singleCharInt;
            char singleChar;

            // Read each char in the file by reading the int value and casting it as a char
            while ((singleCharInt = isr.read()) != -1) {
                singleChar = (char) singleCharInt;
                // using the map to find the huffman code of a char and append it to the stringbuilder
                huffmanEncodedString.append(huffmanMap.get(singleChar));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return huffmanEncodedString.toString();
    }

    /**
     * We convert the byte array into an array of chars of '1's and '0's
     * and for each char we travers the tree until we find a node
     * that is a leaf.
     *
     * Once it's a leaf, it means we found a char, we then take that char and append it to the string
     * @param byteArray
     * @return the decoded and original text
     */
    public String decode(byte[] byteArray) {
        // Convert the byte array into an array of chars of 1s and 0s
        char[] huffmanStringToDecode = byteArrayToString(byteArray).toCharArray();
        StringBuilder decompressedString = new StringBuilder();

        // Set currentNode at the root of the tree.
        HuffmanNode currentNode = this.rootNode;

        for (char c : huffmanStringToDecode) {
            // If char is a '0' we go left (.getZero()) else if is '1' we go right (.getOne())
            if (c == '0') currentNode = currentNode.getZero();
            else if (c == '1') currentNode = currentNode.getOne();

            // If we found a leaf node
            if (currentNode.isLeaf()) {
                // We append the char found on that node to the string
                decompressedString.append(currentNode.getC());
                // We reset the current node back to the root of tree
                currentNode = this.rootNode;
            }
        }
        return decompressedString.toString();
    }

    /**
     * Convert an array of bytes into a string of 1s and 0s
     *
     * @param byteArray
     * @return
     */
    public String byteArrayToString(byte[] byteArray) {
        StringBuilder huffmanString = new StringBuilder();

        // For every byte in the byte array
        for (byte b : byteArray) {
            // We convert the 8 bits of the byte into a string of those bits example: 65 => 01000001 => "01000001"
            // That String is then appended to the huffmanString
            huffmanString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return huffmanString.substring(0, this.encodedLength);
    }

    /**
     * Given a map, a node, and a string
     *
     * Will build a Map of each value in the tree
     *
     * @param map
     * @param root
     * @param s
     */
    public static void buildHuffmanMapFromTree(Map<Character, String> map, HuffmanNode root, String s) {
        // If root that was given is null end function
        if (root == null) return;

        // Exit case: return once we fund a leaf
        if (root.getZero() == null && root.getOne() == null) {
            map.put(root.getC(), s);
            return;
        }

        // Continue on both sides of the tree with an appended value ot the string based on the direction we are going.
        buildHuffmanMapFromTree(map, root.getZero(), s + "0");
        buildHuffmanMapFromTree(map, root.getOne(), s + "1");
    }

    /**
     * Convert a String of 1s and 0s only into a byte array.
     * Any missing bits for the last byte will be filled with Zeros
     *
     * Example:
     * 010000010101001001101100010 will be converted to
     * byte[] = {
     *      0b01000001,
     *      0b10100100,
     *      0b01101100,
     *      0b01000000,
     * }
     *
     * @param huffmanText
     * @return
     * @throws Exception
     */
    public static byte[] huffmanTextToByteSequence(String huffmanText) throws Exception {
        int j = -1;

        int byteArraySize = huffmanText.length()/8;

        // If length is not divisible by 8 we will be missing bits there for we add +1
        if (huffmanText.length()%8 != 0) byteArraySize++;
        byte[] byteArray = new byte[byteArraySize];

        for (int i=0; i < byteArraySize*8; i++) {
            // if i is divisible by 8 we are at a new entry of the byte array
            if (i%8 == 0) {
                // we increment j and set the byte at j to 0b00000000
                j++;
                byteArray[j] = 0x00;
            }

            byte tempByte;
            // we make a temp byte of value 0b00000000 or 0b00000001 depending on if the char at i is 0 or 1 respectively
            if (i > huffmanText.length()-1 || huffmanText.charAt(i) == '0') tempByte = 0x00;
            else if (huffmanText.charAt(i) == '1') tempByte = 0x01;
            else throw new Exception("Error: Char at " + i + " cannot be " + huffmanText.charAt(i));

            // bit Magic to push the value of tempByte to the byteArray
            byteArray[j] = (byte) (tempByte | (byteArray[j] << 1));
        }
        return byteArray;
    }
}
