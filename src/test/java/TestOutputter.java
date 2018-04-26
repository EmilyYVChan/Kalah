import com.qualitascorpus.testsupport.MockIO;
import kalah.BoardState;
import kalah.Outputter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class TestOutputter {

    @Test
    public void testShowCurrentBoardState() {
        Outputter outputter = new Outputter(new MockIO());

        int[] playerOneSeedsInPits = {1,2,3,4,5,6,7};
        int[] playerTwoSeedsInPits = {1,2,3,4,5,6,7};

        BoardState boardState = new BoardState(playerOneSeedsInPits, playerTwoSeedsInPits);

        outputter.showCurrentBoardState(boardState);
    }

    @Test
    public void testShowGameOver() {
        Outputter outputter = new Outputter(new MockIO());

        int[] playerOneSeedsInPits = {1,2,3,4,5,6,7};
        int[] playerTwoSeedsInPits = {1,2,3,4,5,6,7};

        BoardState boardState = new BoardState(playerOneSeedsInPits, playerTwoSeedsInPits);

        outputter.showGameOver(boardState);
    }

    @Test
    public void testShowFinalScoreAndWinner_PlayerOneWins() {
        Outputter outputter = new Outputter(new MockIO());

        int[] playerOneSeedsInPits = {7,7,7,7,7,7,7};
        int[] playerTwoSeedsInPits = {1,2,3,4,5,6,7};

        BoardState boardState = new BoardState(playerOneSeedsInPits, playerTwoSeedsInPits);

        outputter.showFinalScoreAndWinner(boardState);
    }

    @Test
    public void testShowFinalScoreAndWinner_PlayerTwoWins() {
        Outputter outputter = new Outputter(new MockIO());

        int[] playerOneSeedsInPits = {1,2,3,4,5,6,7};
        int[] playerTwoSeedsInPits = {7,7,7,7,7,7,7};

        BoardState boardState = new BoardState(playerOneSeedsInPits, playerTwoSeedsInPits);

        outputter.showFinalScoreAndWinner(boardState);
    }

    @Test
    public void testShowFinalScoreAndWinner_PlayersTied() {
        Outputter outputter = new Outputter(new MockIO());

        int[] playerOneSeedsInPits = {1,2,3,4,5,6,7};
        int[] playerTwoSeedsInPits = {1,2,3,4,5,6,7};

        BoardState boardState = new BoardState(playerOneSeedsInPits, playerTwoSeedsInPits);

        outputter.showFinalScoreAndWinner(boardState);
    }
}
