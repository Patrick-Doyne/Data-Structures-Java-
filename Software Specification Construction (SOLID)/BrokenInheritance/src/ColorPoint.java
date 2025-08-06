import java.awt.Color;

public class ColorPoint extends Point {
	private final Color color;
	public ColorPoint(int x, int y, Color color) { super(x, y); this.color = color; }
	@Override public boolean equals(Object o) {
		if (!(o instanceof ColorPoint)) return false;
		return super.equals(o) && ((ColorPoint) o).color == color;
	}
	@Override public String toString() {
		return super.toString() + " " + color;
	}
}