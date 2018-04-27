package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.board.Board;
import kalah.player.Player;
import kalah.player.PlayerMove;

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
	    setupFields(io);

	    while (currentGameState == GameState.PLAYER_ONE_TURN || currentGameState == GameState.PLAYER_TWO_TURN) {

            outputter.showCurrentBoardState(board.getCurrentBoardState());
	        int playerInput = readInputFromPlayer(io, currentGameState);

	        if (playerInput == -1) {
	            currentGameState = GameState.QUIT;
	            break;
            }

            Player currentPlayer = determineCurrentPlayer(currentGameState);
            PlayerMove playerMove = new PlayerMove(currentPlayer, playerInput);

            GameState nextGameState = gameMechanicsController.processPlayerMove(playerMove);
            currentGameState = nextGameState;

            if (currentGameState == GameState.FINISHED) {
                outputter.showGameOver(board.getCurrentBoardState());
                outputter.showFinalScoreAndWinner(board.getCurrentBoardState());
                break;
            }
        }
	}

    private Player determineCurrentPlayer(GameState currentGameState) {
        return (currentGameState == GameState.PLAYER_ONE_TURN ? Player.PLAYER_ONE : Player.PLAYER_TWO);
    }

    private int readInputFromPlayer(IO io, GameState currentGameState) {
        String playerString = "";
        String partialPrompt = "'s turn - Specify house number or 'q' to quit: ";

        switch(currentGameState) {
            case PLAYER_ONE_TURN:
                playerString = "Player P1";
                break;
            case PLAYER_TWO_TURN:
                playerString = "Player P2";
                break;
            default:
                return -1;
        }

        String fullPrompt = playerString + partialPrompt;

        return io.readInteger(fullPrompt, 1, NUM_HOUSES_PER_PLAYER, -1, "q");
    }

    private void setupFields(IO io) {
        currentGameState = GameState.PLAYER_ONE_TURN;
        board = new Board(NUM_HOUSES_PER_PLAYER, NUM_SEEDS_PER_HOUSE_INITIAL);
        outputter = new Outputter(io);
        gameMechanicsController = new GameMechanicsController(board);
    }
}