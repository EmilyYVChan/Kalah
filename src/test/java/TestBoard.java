import kalah.board.Board;
import kalah.board.BoardState;
import kalah.player.Player;
import kalah.player.PlayerMove;
import org.junit.Test;

import java.util.*;

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
    public void testBoardCapture_NoWrap() {
        Board board = new Board(6,4);

        int[] expectedPlayerOnePits = {0,4,4,4,4,4,8};
        int[] expectedPlayerTwoPits = {4,4,4,4,4,0,0};

        PlayerMove playerMove = new PlayerMove(Player.PLAYER_ONE, 1);
        board.capture(playerMove);
        BoardState boardState = board.getCurrentBoardState();

        assertThat(boardState.playerOnePits).containsExactly(expectedPlayerOnePits);
        assertThat(boardState.playerTwoPits).containsExactly(expectedPlayerTwoPits);
    }

    @Test
    public void testBoardCapture_ExactLapCapture() {
        Queue<Player> queue = new LinkedList<Player>();
        for (int i = 0; i < 5; i++) {
            queue.offer(Player.PLAYER_ONE);
            queue.offer(Player.PLAYER_TWO);
            queue.offer(Player.PLAYER_ONE);
        }

        int[] moves = {2,2,3,3,1,4,4,6,5,5,5,4,4,5,6};

        Board board = new Board(6,4);
//
//        for (int i = 0; i < moves.length - 1; i++) {
//            PlayerMove playerMove = new PlayerMove(queue.poll(), moves[i]);
//            board.sow(playerMove);
//        }
//        PlayerMove playerMove = new PlayerMove(queue.poll(), moves[moves.length - 1]);
//        board.sow(playerMove);
        for (int i = 0; i < moves.length - 1; i++) {
            Player player = queue.poll();
            int selectedHouse = moves[i];
            PlayerMove playerMove = new PlayerMove(player, selectedHouse);
            board.sow(playerMove);
            System.out.println(player.name() + "\t" + selectedHouse);
            printPlayerPitsInReverse(board.getCurrentBoardState().playerTwoPits);
            printPlayerPits(board.getCurrentBoardState().playerOnePits);
            System.out.println();
        }

//        BoardState boardState = board.getCurrentBoardState();
//        int[] expectedPlayerOnePits = {7,2,2,0,0,4,4};
//        int[] expectedPlayerTwoPits = {4,4,4,0,1,13,3};
////    >+----+-------+-------+-------+-------+-------+-------+----+
////                >| P2 | 6[ 4] | 5[ 0] | 4[ 0] | 3[ 2] | 2[ 2] | 1[ 7] |  3 |
////>|    |-------+-------+-------+-------+-------+-------|    |
////>|  4 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 0] | 5[ 1] | 6[13] | P1 |
////>+----+-------+-------+-------+-------+-------+-------+----+
////        int[] expectedPlayerOnePits = {6,6,6,2,3,1,14};
////        int[] expectedPlayerTwoPits = {1,4,4,2,2,6,4};
//
//        assertThat(boardState.playerOnePits).containsExactly(expectedPlayerOnePits);
//        assertThat(boardState.playerTwoPits).containsExactly(expectedPlayerTwoPits);

//        >+----+-------+-------+-------+-------+-------+-------+----+
//                >| P2 | 6[ 6] | 5[ 2] | 4[ 2] | 3[ 4] | 2[ 4] | 1[ 1] | 14 |
//>|    |-------+-------+-------+-------+-------+-------|    |
//>|  4 | 1[ 6] | 2[ 6] | 3[ 6] | 4[ 2] | 5[ 3] | 6[ 1] | P1 |
//>+----+-------+-------+-------+-------+-------+-------+----+

    }

    private void printPlayerPits(int[] playerPits) {
        System.out.println(Arrays.toString(playerPits));
    }

    private void printPlayerPitsInReverse(int[] playerPits) {
        List<Integer> pits = new ArrayList<Integer>();
        for (int i : playerPits) {
            pits.add(i);
        }
        Collections.reverse(pits);
        System.out.println(pits);
    }
}