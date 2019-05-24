import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

public class BoardPainter {

	
	int squarePixels = 50;
	
	Color currCol = Color.lightGray;
	
	Board board;
	Map<Position, Piece> pieces;
	
	public BoardPainter(Board board) {
		this.board = board;
		this.pieces = board.getPieces();
	}
	
	public void paint() {
		BufferedImage bufferedImage = new BufferedImage(board.getSize() * squarePixels, board.getSize() * squarePixels, BufferedImage.TYPE_INT_RGB);
	
		Graphics2D g2d = bufferedImage.createGraphics();
		
		Color lineStart = currCol;
		
		for(int y = 0; y < board.getSize(); y++) {
			currCol = lineStart;
			flipCol();
			lineStart = currCol;
			g2d.setColor(currCol);
			for(int x = 0; x < board.getSize(); x++) {
				g2d.fillRect(x*squarePixels, y*squarePixels, squarePixels, squarePixels);
				flipCol();
				g2d.setColor(currCol);
			}
		}
		
		for(Position position : pieces.keySet()) {
			if(pieces.get(position) == Piece.BLACK) {
				g2d.setColor(Color.black);
			}
			else if(pieces.get(position) == Piece.WHITE) {
				g2d.setColor(Color.white);
			}
			else {
				g2d.setColor(Color.red);
			}
			
			g2d.fillOval(position.getX() * squarePixels, position.getY() * squarePixels, squarePixels, squarePixels);
		}
		
		g2d.dispose();
		
		File file = new File("board_"+board.hashCode()+".png");
        try {
			ImageIO.write(bufferedImage, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void flipCol() {
		if(currCol == Color.lightGray) {
			currCol = Color.DARK_GRAY;
		}
		else {
			currCol = Color.lightGray;
		}
	}
	
}
