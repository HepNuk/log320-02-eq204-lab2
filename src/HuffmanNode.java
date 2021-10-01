import java.io.Serializable;

public class HuffmanNode implements Serializable {
    private char c;
    private int freq;

    private HuffmanNode zero;
    private HuffmanNode one;

    public HuffmanNode(char c, int freq, HuffmanNode zero, HuffmanNode one) {
        this.c = c;
        this.freq = freq;
        this.zero = zero;
        this.one = one;
    }
    public HuffmanNode(char c, int freq) {
        this.c = c;
        this.freq = freq;
        this.zero = null;
        this.one = null;
    }
    public HuffmanNode(int freq, HuffmanNode zero, HuffmanNode one) {
        this.freq = freq;
        this.zero = zero;
        this.one = one;
    }
    public HuffmanNode() {
        this.zero = null;
        this.one = null;
    }

    public char getC() { return this.c; }
    public int getFreq() { return this.freq; }
    public HuffmanNode getZero() { return this.zero; }
    public HuffmanNode getOne() { return this.one; }

    public void setC(char c) { this.c = c; }
    public void setFreq(int freq){ this.freq = freq; }
    public void setZero(HuffmanNode zero) { this.zero = zero; }
    public void setOne(HuffmanNode one) { this.one = one; }

    public Boolean isLeaf() { return (this.zero == null) && (this.one == null); }
}
