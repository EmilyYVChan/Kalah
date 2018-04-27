package kalah.player;

import kalah.board.BoardState;

public class PlayerMove {

    public Player player;
    public int selectedHouse;
    public BoardState preMoveBoardState;

    public PlayerMove(Player player, int selectedHouse) {
        this.player = player;
        this.selectedHouse = selectedHouse;
    }
}
