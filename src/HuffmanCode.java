import java.util.ArrayList;

public class HuffmanCode {
    private ArrayList<HuffmanReference> huffmanReferenceTable;
    private final HuffmanNode rootNode;

    public HuffmanCode (HuffmanNode rootNode) {
        this.rootNode = rootNode;
        this.huffmanReferenceTable = new ArrayList<>();
    }

    public void encodeHuffmanCode(HuffmanNode rootNode, String data) {
        // encode and return data as huffman code
    }

    public void decodeHuffmanCode(String data) {
        // decode and return decoded data
    }

    public HuffmanNode getRootNode() { return this.rootNode; }
    public ArrayList<HuffmanReference> getHuffmanReferenceTable() { return this.huffmanReferenceTable; }

    public void addReference(String huffmanCode, char huffmanChar) {
        this.huffmanReferenceTable.add(new HuffmanReference(huffmanCode, huffmanChar));
    }
}
