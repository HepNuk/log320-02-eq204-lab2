public class HuffmanNode {
    private char c;

    private HuffmanNode zero;
    private HuffmanNode one;

    public HuffmanNode(char c, int count, HuffmanNode zero, HuffmanNode one) {
        this.c = c;
        this.zero = zero;
        this.one = one;
    }
    public HuffmanNode(char c) {
        this.c = c;
        this.zero = null;
        this.one = null;
    }
    public HuffmanNode() {
        this.zero = null;
        this.one = null;
    }

    public char getC() { return this.c; }
    public HuffmanNode getZero() { return this.zero; }
    public HuffmanNode getOne() { return this.one; }

    public void setC(char c) { this.c = c; }
    public void setZero(HuffmanNode zero) { this.zero = zero; }
    public void setOne(HuffmanNode one) { this.one = one; }

    public Boolean isEndNode() { return (this.zero == null && this.one == null); }
}
