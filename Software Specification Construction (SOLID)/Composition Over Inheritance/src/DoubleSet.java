import java.util.ArrayList;
import java.util.List;

public class DoubleSet<T extends DeepCopy<T>> extends ImprovedSet<T> {
	

    private List<Double> lst;
   
    public DoubleSet(Class<T> type){
    	super(type);
        lst = new ArrayList<Double>();
    }

    public void addDouble(Double x) {
    	if(x == null) throw new NullPointerException("Null elements being added to lst not permitted");
		if(x.getClass() != Double.class) throw new ClassCastException("Types stored within lst is limited to just Doubles, nothing else");
		if(!lst.contains(x)) lst.add(x);
    }

    @Override
    public boolean equals(Object obj) {
    	
        if (!(obj instanceof DoubleSet<?>) && (obj instanceof ImprovedSet<?>)) {
            return super.equals(obj);
        }

        if (!(obj instanceof DoubleSet<?>) || ((DoubleSet<?>)obj).getType() != this.getType()) return false;
        
        DoubleSet<?> s = (DoubleSet<?>) obj;
        if(s.lst.size() != this.lst.size()) return false;
		for(Object i : s.lst) {
			if(!this.lst.contains(i)) return false;
		}
		return true;
    }

    @Override
    public int hashCode() {
    	int hash = 1;
        for(Double i : lst) {
        	hash += (i == null ? 0 : i.hashCode());
        }
        return hash + super.hashCode();
    }

    @Override
    public DoubleSet<T> clone() throws CloneNotSupportedException {
    	DoubleSet<T> doubleSet = (DoubleSet<T>) super.clone();
    	doubleSet.lst = new ArrayList<Double>(this.lst);
        return doubleSet;
    }
}