public class HuffmanReference {
    private String huffmanCode;
    private char huffmanChar;

    public HuffmanReference (String huffmanCode, char huffmanChar) {
        this.huffmanCode = huffmanCode;
        this.huffmanChar = huffmanChar;
    }

    public String getHuffmanCode() { return this.huffmanCode; }
    public char getHuffmanChar() { return this.huffmanChar; }

    public void setHuffmanCode(String huffmanCode) { this.huffmanCode = huffmanCode; }
    public void setHuffmanChar(char huffmanChar) { this.huffmanChar = huffmanChar; }
}
