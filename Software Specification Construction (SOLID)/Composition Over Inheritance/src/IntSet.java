import java.util.ArrayList;
import java.util.List;

public class IntSet implements Cloneable {
    private List<Integer> els;
    public IntSet () {
        els = new ArrayList<Integer>();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IntSet)) return false;

        IntSet s = (IntSet) obj;
        return els.equals(s.els);
    }

    public void add (int x) {
        els.add(x);
    }

    @Override
    public int hashCode() {
        return els.hashCode();
    }

    // adding a private constructor
    private IntSet (List<Integer> list) {
        els = list;
    }

    @Override
    public IntSet clone() {
        return new IntSet ( new ArrayList<Integer>(els));
    }
}