import java.util.HashMap;
import java.util.Map;

public class HuffmanCode {
    private Map<Character, String> huffmanTableMap;
    private final HuffmanNode rootNode;

    public HuffmanCode () {
        this.rootNode = new HuffmanNode();
        this.huffmanTableMap = new HashMap<>();
    }

    public void encodeHuffmanCode(HuffmanNode rootNode, String data) {
        // encode and return data as huffman code
    }

    public void decodeHuffmanCode(String data) {
        // decode and return decoded data
    }

    public HuffmanNode getRootNode() { return this.rootNode; }
    public Map<Character, String> getHuffmanReferenceTable() { return this.huffmanTableMap; }

    private void addReference(char huffmanChar, String huffmanCode) {
        this.huffmanTableMap.put(huffmanChar, huffmanCode);
    }

    public void createHuffmanTableMap() {

    }

    public void createHuffmanDataTree() {

    }
}
