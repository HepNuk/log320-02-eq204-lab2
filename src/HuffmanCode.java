import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HuffmanCode implements Serializable {
    private Map<Character, String> huffmanMap;
    private HuffmanNode rootNode;

    public HuffmanCode () {
        this.rootNode = new HuffmanNode();
        this.huffmanMap = new HashMap<>();
    }

    public void encodeHuffmanCode(Iterator<Character> iterator, String huffmanValue, HuffmanNode currentNode) {
        Character current = iterator.next();

        if (!iterator.hasNext()) {
            huffmanMap.put(current, huffmanValue + "0");
            currentNode.setZero(new HuffmanNode(current));
        } else {
            huffmanMap.put(current, huffmanValue + "1");
            currentNode.setOne(new HuffmanNode(current));
            currentNode.setZero(new HuffmanNode());
            currentNode = currentNode.getZero();

            encodeHuffmanCode(iterator, huffmanValue + "0", currentNode);
        }
    }

    public void decodeHuffmanCode(String data) {
        // decode and return decoded data
    }

    public HuffmanNode getRootNode() { return this.rootNode; }
    public Map<Character, String> getHuffmanReferenceTable() { return this.huffmanMap; }

    private void addReference(char huffmanChar, String huffmanCode) {
        this.huffmanMap.put(huffmanChar, huffmanCode);
    }

    public void createHuffmanTableMap() {

    }

    public void createHuffmanDataTree() {

    }

    // Private methods
}
