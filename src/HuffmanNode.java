public class HuffmanNode {
    private char c;
    private int count;

    private HuffmanNode zero;
    private HuffmanNode one;

    public HuffmanNode(char c, int count, HuffmanNode zero, HuffmanNode one) {
        this.c = c;
        this.count = count;
        this.zero = zero;
        this.one = one;
    }

    public char getC() { return this.c; }
    public int getCount() { return this.count; }
    public HuffmanNode getZero() { return this.zero; }
    public HuffmanNode getOne() { return this.one; }

    public void setC(char c) {this.c = c; }
    public void setCount(int count) { this.count = count; }
    public void setZero(HuffmanNode zero) { this.zero = zero; }
    public void setOne(HuffmanNode one) { this.one = one; }
}
