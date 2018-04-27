package kalah.board;

import kalah.GameState;
import kalah.player.Player;
import kalah.player.PlayerMove;

public class BoardController {

    private Board board;

    public BoardController(Board board) {
        this.board = board;
    }

    public GameState processValidPlayerMove(PlayerMove playerMove) {
        Player currentPlayer = playerMove.player;
        Player opponentPlayer = playerMove.player.determineOpponentPlayer();

        Pit pitOfLastSownSeed = board.sow(playerMove);

        if (isLastSeedInPlayerStore(pitOfLastSownSeed, currentPlayer)) {
            return choosePlayerTurn(currentPlayer);
        } else if (isPlayerHouseEmptyBeforeLastSeedWasPlaced(pitOfLastSownSeed, currentPlayer)
                && !isOppositeHouseCurrentlyEmpty(pitOfLastSownSeed)) {
            this.playCaptureMove(pitOfLastSownSeed);
        }

        return choosePlayerTurn(opponentPlayer);
    }

    public boolean isPlayerMoveValidOnBoard(PlayerMove playerMove) {
        return !isHouseSelectedByPlayerEmpty(board.getCurrentBoardState(), playerMove);
    }

    private boolean isOppositeHouseCurrentlyEmpty(Pit pitOfLastSownSeed) {
        BoardState currentBoardState = board.getCurrentBoardState();
        Player ownerOfPitOfLastSownSeed = pitOfLastSownSeed.getOwnerPlayer();
        int[] oppositePits = (ownerOfPitOfLastSownSeed == Player.PLAYER_ONE
                ? currentBoardState.playerTwoPits : currentBoardState.playerOnePits);

        int zeroBasedPositionOfOppositeHouse = board.numHousesPerPlayer
                - pitOfLastSownSeed.getZeroBasedPitPositionInOwnerPits() - 1;

        return (oppositePits[zeroBasedPositionOfOppositeHouse] == 0);
    }

    private boolean isPlayerHouseEmptyBeforeLastSeedWasPlaced(Pit pitOfLastSownSeed, Player player) {
        return (pitOfLastSownSeed.getOwnerPlayer() == player
                && pitOfLastSownSeed.getSeeds() == 1);
    }

    private boolean isLastSeedInPlayerStore(Pit pitOfLastSownSeed, Player player) {
        return (pitOfLastSownSeed.getOwnerPlayer() == player
                && pitOfLastSownSeed.getPitType() == Pit.PitType.STORE);
    }

    private boolean isHouseSelectedByPlayerEmpty(BoardState preMoveBoardState, PlayerMove playerMove) {
        int[] playerPits = choosePlayerPits(preMoveBoardState, playerMove.player);
        int seedsAtSelectedHouse = playerPits[playerMove.selectedHouse - 1];

        return (seedsAtSelectedHouse == 0);
    }

    private void playCaptureMove(Pit pitOfLastSownSeed) {
        int houseNumberOfLastSownSeed = pitOfLastSownSeed.getZeroBasedPitPositionInOwnerPits() + 1;
        PlayerMove captureMove = new PlayerMove(pitOfLastSownSeed.getOwnerPlayer(), houseNumberOfLastSownSeed);
        board.capture(captureMove);
    }

    private int[] choosePlayerPits(BoardState boardState, Player player) {
        return (player == Player.PLAYER_ONE ? boardState.playerOnePits : boardState.playerTwoPits);
    }

    private GameState choosePlayerTurn(Player player) {
        return (player == Player.PLAYER_ONE ? GameState.PLAYER_ONE_TURN : GameState.PLAYER_TWO_TURN);
    }

    public boolean isPlayerHousesAllEmpty(Player player) {
        BoardState currentBoardState = board.getCurrentBoardState();
        int[] playerPits = choosePlayerPits(currentBoardState, player);

        for (int seeds : playerPits) {
            if (seeds > 0) {
                return false;
            }
        }
        return true;
    }
}
