import kalah.player.Player;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestPlayer {

    @Test
    public void testDetermineOpponentPlayer() {
        Player playerOne = Player.PLAYER_ONE;
        Player playerTwo = Player.PLAYER_TWO;

        assertThat(playerOne.determineOpponentPlayer().name()).isEqualTo(playerTwo.name());
        assertThat(playerTwo.determineOpponentPlayer().name()).isEqualTo(playerOne.name());

    }
}
