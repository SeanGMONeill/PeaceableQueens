import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;


public class Solver {
	
	int boardSize;
	ForkJoinPool commonPool;
	
	public static void main(String[] args) {
		Solver solver = new Solver(25);
		Set<Board> boards = solver.solveForPairs(300);
		System.out.println("Found " + boards.size() + " matches.");
		for(Board board : boards) {
			board.printBoard();
			BoardPainter bp = new BoardPainter(board);
			bp.paint();
		}
		
	    notifySuccess();
	}
	
	public Solver(int boardSize) {
		commonPool = ForkJoinPool.commonPool();
		this.boardSize = boardSize;
	}
	
	public Set<Board> solveForPairs(int pairs){
		Board empty = new Board(boardSize);
		Set<Board> boards = new HashSet<Board>();
		boards.add(empty);
		
		Set<RecursiveAddPairs> jobs = new HashSet<RecursiveAddPairs>();
		
		for(int i = 0; i < pairs; i++) {
			System.out.println("Pairs: "+i + "\n\tFound " + boards.size());
			//boards = addPair(boards);
			for(Board board : boards) {
				RecursiveAddPairs job = new RecursiveAddPairs(board);
				jobs.add(job);
				job.fork();
			}
			
			boards = new HashSet<Board>();
			
			for(RecursiveAddPairs job : jobs) {
				boards.addAll(job.join());
				if(pairs == 3 && boards.size() >= 100000) {
					System.out.println("Halted. Max pairs found: " + (i-1));
					break;
				}
			}
			
			Runtime.getRuntime().gc();
		}
		
		return boards;
	}
	

	public static Set<Board> addPair(Set<Board> previousPairs){
		Set<Board> newBoards = new HashSet<Board>();
		for(Board previous : previousPairs) {
			newBoards.addAll(addOnePair(previous));
		//	if(newBoards.size() > 1000) {
		//		break;
		//	}
		}
		return newBoards;
	}
	
	public static Set<Board> addOnePair(Board board){
		Set<Board> newBoards = new HashSet<Board>();
	
		Set<Board> blackBoards = addOnePiece(board, Piece.BLACK);
		
		Runtime.getRuntime().gc();
		
		for(Board bBoard : blackBoards) {
			//bBoard.printBoard();
			newBoards.addAll(addOnePiece(bBoard, Piece.WHITE));
			if(newBoards.size() > 2) {
				break;
			}
		}
		
		return newBoards;
	}
	
	public static Set<Board> addOnePiece(Board board, Piece colour){
		Board initial = board;
		Set<Board> newBoards = new HashSet<Board>();
		
		Board nextBoard = board.clone();
		
		for(int x = 0; x < board.getSize(); x++) {
			for(int y = 0; y < board.getSize(); y++) {
				if(nextBoard.addPiece(new Position(x, y), colour)) {
					newBoards.add(nextBoard);
					nextBoard = initial.clone();					
				}
				
			}
		}
		return newBoards;
	}
	
	public static void notifySuccess() {
		String token = "o.KwkhFmG3hT4RnG3HNxeQSvLyoPXmQV5u";
		
		String binary = "{\"email\": \"sean.gm.o@gmail.com\", \"type\":\"note\", \"title\":\"Solver success\", \"body\":\"Solver Complete.\"}";

		
		try {
			HttpURLConnection con = (HttpURLConnection) new URL("https://api.pushbullet.com/v2/pushes").openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Access-Token", token);
			con.setDoOutput(true);
			con.getOutputStream().write(binary.getBytes("UTF-8"));
		//	System.out.println(new String(con.getInputStream().readAllBytes()));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
