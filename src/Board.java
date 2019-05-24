import java.util.HashMap;
import java.util.Map;

public class Board {
	
	
	Map<Position, Piece> pieces = new HashMap<Position, Piece>();
	
	int size;
	
	public Board(int size) {
		this.size = size;
	}
	
	public Board(int size, Map<Position, Piece> pieces) {
		this.size = size;
		this.pieces = pieces;
	}
	
	public boolean addPiece(Position position, Piece piece) {
		if(piece == Piece.NONE){
	//		System.out.println("Rejected " + piece + " at " + position + " : cannot place none");
			return false;
		}
		if(pieces.containsKey(position)) {
	//		System.out.println("Rejected " + piece + " at " + position + " : already exists with piece " + pieces.get(position));
			//printBoard();
			return false;
		}
		
		if(checkValidPlacement(position, piece)){
			pieces.put(position, piece);
	//		System.out.println("Placed " + piece + " at " + position);
			return true;
		}
		else {
		//	System.out.println("Rejected " + piece + " at " + position + " : invalid placement");
			return false;
		}
	}

	public boolean checkValidPlacement(Position position, Piece piece) {
		for(Position other : pieces.keySet()) {
			//Check for possible conflict (opposite colour piece)
			if(pieces.get(other) != piece && pieces.get(other) != Piece.NONE) {
				if(position.canReach(other)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	
	public Board clone() {
		Map<Position, Piece> pieceMap = new HashMap<Position, Piece>();
		pieceMap.putAll(this.pieces);
		return new Board(this.size, pieceMap);
	}
	
	public void printBoard() {
		System.out.println("Board:");
		for(Position pos : pieces.keySet()) {
			System.out.println("\t" + pos.toString() + " : " + pieces.get(pos).toString());
		}
	}
	
	public Map<Position, Piece> getPieces(){
		return pieces;
	}
	
	public int getSize() {
		return size;
	}
	
	public int hashCode() {
		int hash = 0;
		for(Position p : pieces.keySet()) {
			hash += p.hashCode();
		}
		return hash;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Board)) {
			return false;
		}
		
		Board otherBoard = (Board)o;
		
		if(otherBoard.getPieces() == this.getPieces()) {
			return true;
		}
		
		Map<Position, Piece> inverseMap = new HashMap<Position, Piece>();
		for(Position p : pieces.keySet()) {
			inverseMap.put(p, pieces.get(p) == Piece.WHITE ? Piece.BLACK : Piece.WHITE);
		}
		
		if(otherBoard.getPieces() == inverseMap) {
			return true;
		}
		
		return false;
	}
}
