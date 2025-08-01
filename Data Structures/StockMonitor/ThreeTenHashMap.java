
import java.util.Iterator;

/**
 * This class is a hash map that utilizes separate chaining to spread the load evenly.
 * @author Patrick T Doyne
 *
 * @param <K> Key for mapping.
 * @param <V> Value for mapping.
 */
public class ThreeTenHashMap<K, V> {

	/**
	 * This is an object that we will be using to store within the hash map.
	 * @author pdoyne
	 *
	 * @param <K> Key to the pair.
	 * @param <V> Value for the pair.
	 */
	private class Pair<K,V> {
		/**
		 * Key for this pair.
		 */
		private K key;
		/**
		 * Value for this pair.
		 */
		private V value;
		/**
		 * Constructor.
		 * @param key is the key for the pair.
		 * @param value is the value for the pair.
		 */
		public Pair(K key, V value){
			this.key = key;
			this.value = value;
		}
		/**
		 * Get the specified key.
		 * @return the key being requested.
		 */
		public K getKey(){ return key; }
		/**
		 * Get the specified value.
		 * @return the value being requested.
		 */
		public V getValue(){ return value; }
		/**
		 * Set the given key to a new one.
		 * @param key is the object we are setting this key to.
		 */
		public void setKey(K key){ this.key = key; }
		/**
		 * Set the given value to a new one.
		 * @param value is the object we are setting this value to.
		 */
		public void setValue(V value){ this.value = value; }
		/**
		 * Returns a string representation of this pair.
		 */
		@Override
		public String toString(){
			return "<"+key.toString()+","+value.toString()+">";
		}
		/**
		 * Checks if two keys are equal in 2 pairs.
		 * @return true if they are equal.
		 */
		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object o) {
			// return true if two pairs have matching keys
			// i.e. <"Alice", 1> is considered as equal to <"Alice", 2>
			if(o instanceof Pair) {
				Pair<K,V> pair = (Pair<K,V>)o;
				return pair.key.equals(key);  
			}
			return false;
		}
		/**
		 * Produces the hash code for this key object.
		 * @return the hash code of the key.
		 */
		@Override
		public int hashCode(){
			//hashCode is determined by key only
			return key.hashCode();
		}
	}


	/**
	 * Array of DL lists used for seperate chaining.
	 */
	private ThreeTenDLList<Pair<K,V>>[] buckets;

	/**
	 * Capacity of this map.
	 */
	final static private int DEFAULT_CAPACITY = 11;

	/**
	 * Size of this map.
	 */
	private int size;

	/**
	 * Constructor.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenHashMap() {
		buckets = (ThreeTenDLList<Pair<K,V>>[])new ThreeTenDLList[DEFAULT_CAPACITY];
		size = 0;
	}
	/**
	 * Returns the amount of indexes being used in buckets.
	 * @return the size of the current map.
	 */
	public int size() {
		return size;
	}
	/**
	 * Shows the map size this map can be.
	 * @return length of the buckets array.
	 */
	private int capacity() {
		return buckets.length;
	}
	/**
	 * Gets the hash code for a given key.
	 * @param key is the key we want the hash code for.
	 * @return absolute value of the hash code for the key passed in.
	 */
	private int getHash(K key) {
		return Math.abs(key.hashCode());
	}
	/**
	 * Returns a string representation of this hash map and its elements.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<buckets.length; i++) {		
			ThreeTenDLList<Pair<K,V>> list = buckets[i];	
			sb.append("[");	

			if (list != null) {
				sb.append(list.listToString());
			}
			sb.append("]");

			if (i!=buckets.length-1)
				sb.append(",");	  
			System.out.println(sb.toString());
		}
		return "{" + sb.toString() + "}";
	}
	/**
	 * Allows you to add a new pair to this hash map.
	 * @param key is the key of the pair being added.
	 * @param value is the value of the pair being added.
	 */
	public void put(K key, V value) {
		if(key==null||value==null)return;

		Pair<K,V> temp = new Pair<K,V>(key,value);

		int index = Math.abs(key.hashCode()%DEFAULT_CAPACITY);

		if(this.buckets[index]==null) {
			this.buckets[index] = new ThreeTenDLList<Pair<K,V>>();
			this.buckets[index].addLast(temp);
			this.size++;
			return;
		}

		else {
			Pair<K,V> temp2 = new Pair<K,V> (key, value);
			Iterator<Pair<K,V>> it = this.buckets[index].iterator();

			while( it.hasNext()) {
				temp2 = it.next();
				if(temp2.getKey().equals(key)) {
					this.buckets[index].remove(temp2);
					this.buckets[index].addLast(temp);;

					return;
				}
				
				if(!it.hasNext()) {
					this.buckets[index].addLast(temp);
					this.size++;
				}
			}
		}
	}
	/**
	 * Returns the value for the given key in this hash map.
	 * @param key is the key to the value we wish to find.
	 * @return the value of the passed in key.
	 */
	public V get(K key) {
		if(key==null)return null;

		int index = Math.abs(key.hashCode()%DEFAULT_CAPACITY);

		if(this.buckets[index]==null) {
			return null;
		}

		Iterator<Pair<K,V>> it = this.buckets[index].iterator();
		Pair<K,V> tmp = null;

		while(this.buckets[index].size()!=0&&it.hasNext()) {
			tmp = it.next();
			if(tmp.getKey().equals(key)) {
				V ans = tmp.getValue();
				return ans;
			}
		}

		return null;
	}
	/**
	 * Remove the pair with the given key from this hash map.
	 * @param key is the key to the pair being removed.
	 * @return the value of the pair that has been removed.
	 */
	public V delete(K key){

		if(key==null)return null;

		int index = Math.abs(key.hashCode()%DEFAULT_CAPACITY);
		Iterator<Pair<K,V>> it = this.buckets[index].iterator();
		Pair<K,V> tmp = null;

		while(this.buckets[index].size()!=0 && it.hasNext()) {
			tmp = it.next();
			if(tmp.getKey().equals(key)) {
				Pair<K,V> ans = this.buckets[index].remove(tmp);
				this.size--;
				if(this.buckets[index].size()==0) {
					this.buckets[index] = new ThreeTenDLList<Pair<K,V>>();
				}
				return ans.getValue();
			}			
		}
		return null;
	}

	/**
	 * This is to test different cases of functionality within this object.
	 * @param args are the arguments passed in from the console.
	 */
	public static void main(String args[]) {
		ThreeTenHashMap<String, String> map = new ThreeTenHashMap<>();

		ThreeTenHashMap<Integer, String> map2 = new ThreeTenHashMap<>();
		map2.put(2, "red");
		map2.put(3, "yellow");
		map2.put(14, "purple");
		map2.put(4, "purpl");
		System.out.println(map2.toString());
		System.out.println(map2.get(14));

		map.put("apple", "red");
		map.put("pear", "yellow");
		map.put("eggplant", "purple");

		if (map.get("apple").equals("red") && map.get("eggplant").equals("purple") && map.size() == 3){
			System.out.println("Yay1");
		}

		//		//change mapping, delete
		map.put("apple", "green");

		if (map.get("apple").equals("green") && map.size()==3 && map.delete("pear").equals("yellow") 
				&& map.size() == 2) {
			System.out.println("Yay2");
		}

		//key not present
		if (map.get("banana")==null && map.delete("pear")==null){
			System.out.println("Yay3");		
		}
		if (map.toString().equals("{[],[],[],[],[],[],[],[],[<eggplant,purple>],[],[<apple,green>]}")){
			System.out.println("Yay4");		
		}
	}

}
