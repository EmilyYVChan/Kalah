import com.qualitascorpus.testsupport.MockIO;
import kalah.Outputter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class TestOutputter {

    @Test
    public void testShowCurrentBoardState() {
        Outputter outputter = new Outputter(new MockIO());

        int[] playerOneSeedsInPits = {1,2,3,4,5,6,7};
        int[] playerTwoSeedsInPits = {1,2,3,4,5,6,7};

        outputter.showCurrentBoardState(playerOneSeedsInPits, playerTwoSeedsInPits);
    }
}
