import java.util.Comparator;

public class HuffmanComparator implements Comparator<HuffmanNode>{
    @Override
    public int compare(HuffmanNode o1, HuffmanNode o2) {
        return o1.getCount() - o2.getCount();
    }
}
