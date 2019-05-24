import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class RecursiveAddPairs extends RecursiveTask<Set<Board>> {

	Board board;
	
	public RecursiveAddPairs(Board board) {
		this.board = board;
	}
	
	@Override
	protected Set<Board> compute() {
		return Solver.addOnePair(board);
	}

}
