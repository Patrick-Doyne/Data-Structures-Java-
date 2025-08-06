import java.util.ArrayList;
import java.util.List;

public class IntSet2 extends IntSet  {
    private List<Double> lst;

    @Override
    public boolean equals(Object obj) {
        // The code here does not care about the super class, it only cares about checking against itself
        if (!(obj instanceof IntSet2)) return false;

        IntSet2 s = (IntSet2) obj;
        return lst.equals(s.lst);
    }

    public IntSet2() {
        lst = new ArrayList<Double>();
    }

    public void addDouble(double x) {
        lst.add(x);
    }

    public IntSet2(List<Double> lst) {
            this.lst = lst;
    }

    @Override
    public IntSet2 clone() {
        return new IntSet2(new ArrayList<Double>(lst));
    }
}