import java.util.ArrayList;
import java.util.List;

public abstract class ImprovedSet<T extends DeepCopy<T>>  implements Cloneable {

	private final Class<T> type;
	private List<T> els;
	
	public ImprovedSet (Class<T> type) {
		this.type = type;
		els = new ArrayList<T>();
	}
	
	public Class<T> getType(){
		return this.type;
	}

	public void add (T x) {
		if(x == null) throw new NullPointerException("Null elements being added to els not permitted");
		if(x.getClass() != this.getType()) throw new ClassCastException("Types stored within els is limited to just one, not multiple");
		if(!els.contains(x)) els.add(x);
	}

	@Override
	public boolean equals(Object obj) {
		if ((!(obj instanceof ImprovedSet<?>)) || (((ImprovedSet<?>)obj).getType() != this.getType())) return false;

		ImprovedSet<?> s = (ImprovedSet<?>) obj;
		if(s.els.size() != this.els.size()) return false;
		for(Object i : s.els) {
			if(!this.els.contains(i)) return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		for(T i : els) {
			hash += (i == null ? 0 : i.hashCode());
		}
		return hash;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ImprovedSet<T> clone() throws CloneNotSupportedException {
		if(this instanceof ImprovedSet<T>) {
			ImprovedSet<T> set = (ImprovedSet<T>) super.clone();
			set.els = new ArrayList<T>();
			for(T t : this.els) {
				T tmp = t.deepCopy();
				set.add(tmp);
			}
			return set;
		}
		return null;
	}

}