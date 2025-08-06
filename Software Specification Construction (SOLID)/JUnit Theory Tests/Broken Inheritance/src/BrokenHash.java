
public final class BrokenHash {
	private final int x;
	private final int y;
	public BrokenHash(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override public boolean equals(Object a) {
		if(!(a instanceof BrokenHash)) return false;
		BrokenHash tmp = (BrokenHash)a;
		return this.x == tmp.x && this.y == tmp.y;
	}
	
	/* FIX FOR CONTRACT*/

	  @Override public int hashCode(){
	  	return this.x * 31 + this.y ;
	  }

}
	