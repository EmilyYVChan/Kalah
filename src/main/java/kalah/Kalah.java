package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.board.Board;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah {

    private static final int NUM_HOUSES_PER_PLAYER = 6;
    private static final int NUM_SEEDS_PER_HOUSE_INITIAL = 4;

    private GameState currentGameState;
    private Board board;
    private Outputter outputter;
    private GameMechanicsController gameMechanicsController;

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

	public void play(IO io) {
        currentGameState = GameState.PLAYER_ONE_TURN;
        board = new Board(NUM_HOUSES_PER_PLAYER, NUM_SEEDS_PER_HOUSE_INITIAL);
        outputter = new Outputter(io);
        gameMechanicsController = new GameMechanicsController(board);



//		io.println("Player 1's turn - Specify house number or 'q' to quit: ");


	}
}
