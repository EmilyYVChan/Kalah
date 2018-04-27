import kalah.board.Board;
import kalah.board.BoardState;
import kalah.player.Player;
import kalah.player.PlayerMove;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBoard {

    @Test
    public void testBoardInitiation() {
        int numHousesPerPlayer = 6;
        int numSeedsPerHouseInitial = 4;
        Board board = new Board(numHousesPerPlayer, numSeedsPerHouseInitial);

        int[] expectedPlayerSeedsCountInPits = {4,4,4,4,4,4,0};

        BoardState boardState = board.getCurrentBoardState();

        assertThat(boardState.playerOnePits).containsExactly(expectedPlayerSeedsCountInPits);
        assertThat(boardState.playerTwoPits).containsExactly(expectedPlayerSeedsCountInPits);
    }

    @Test
    public void testBoardSow() {
        Board board = new Board(6,13);

        int[] expectedPlayerOnePits = {1,14,14,14,14,14,1};
        int[] expectedPlayerTwoPits = {14,14,14,14,14,14,0};

        PlayerMove playerMove = new PlayerMove(Player.PLAYER_ONE, 1);
        board.sow(playerMove);
        BoardState boardState = board.getCurrentBoardState();

        assertThat(boardState.playerOnePits).containsExactly(expectedPlayerOnePits);
        assertThat(boardState.playerTwoPits).containsExactly(expectedPlayerTwoPits);

    }

    @Test
    public void testBoardCapture() {
        Board board = new Board(6,4);

        int[] expectedPlayerOnePits = {0,4,4,4,4,4,8};
        int[] expectedPlayerTwoPits = {4,4,4,4,4,0,0};

        PlayerMove playerMove = new PlayerMove(Player.PLAYER_ONE, 1);
        board.capture(playerMove);
        BoardState boardState = board.getCurrentBoardState();

        assertThat(boardState.playerOnePits).containsExactly(expectedPlayerOnePits);
        assertThat(boardState.playerTwoPits).containsExactly(expectedPlayerTwoPits);
    }
}
