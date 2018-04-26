import kalah.Board;
import kalah.BoardState;
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
        Board board = new Board(6,4);

    }
}
