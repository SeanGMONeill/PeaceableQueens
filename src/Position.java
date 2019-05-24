public class Position {

	final int x;
	final int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int hashCode() {
		return (int)(x<<4 + y);	
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Position)) {
			return false;
		}
		Position other = (Position)o;
		if(other.getX() == this.x && other.getY() == this.getY()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean canReach(Position other) {
		return sameRow(other) || sameColumn(other) || sameDiagonal(other);
	}
	
	public boolean sameRow(Position other) {
		return other.getY() == this.getY();
	}
	
	public boolean sameColumn(Position other) {
		return other.getX() == this.getX();
	}
	
	public boolean sameDiagonal(Position other) {
		int diffA = Math.abs(other.getY()-this.getY())/(other.getX()-this.getX());
		int diffB = Math.abs((this.getY() - other.getY())/(this.getX()-other.getX()));
		return (1 == diffA) || (1 == diffB);
	}
	
	public String toString() {
		return "<"+x+","+y+">";
	}
}
