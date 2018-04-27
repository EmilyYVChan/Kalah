package kalah;

import kalah.board.Board;
import kalah.board.BoardState;
import kalah.player.Player;
import kalah.player.PlayerMove;

public class GameMechanicsController {

    private Board board;

    public GameMechanicsController(Board board) {
        this.board = board;
    }

    public GameState processPlayerMove(PlayerMove playerMove) {
        Player currentPlayer = playerMove.player;
        Player opponentPlayer = playerMove.player.determineOpponentPlayer();
        BoardState preMoveBoardState = board.getCurrentBoardState();

        if (isHouseSelectedByPlayerEmpty(preMoveBoardState, playerMove)) {
            return choosePlayerTurn(currentPlayer);
        } else {
            board.sow(playerMove);
        }

        if (isLastSeedInPlayerStore(preMoveBoardState, playerMove)) {
            return choosePlayerTurn(currentPlayer);
        } else if (isPlayerHouseEmptyBeforeLastSeedWasPlaced(preMoveBoardState, playerMove)) {
            this.playCaptureMove(preMoveBoardState, playerMove);
        }

        if (isGameFinished()) {
            return GameState.FINISHED;
        } else {
            return choosePlayerTurn(opponentPlayer);
        }
    }

    private boolean isPlayerHouseEmptyBeforeLastSeedWasPlaced(BoardState preMoveBoardState, PlayerMove playerMove) {
        int expectedLastSeedPitZeroBasedPositionInPlayerPits = calcExpectedLastSeedPitZeroBasedPositionInPlayerPits(preMoveBoardState, playerMove);
        int[] playerCurrentPits = choosePlayerPits(board.getCurrentBoardState(), playerMove.player);

        if (isLastSeedInPlayerHouse(preMoveBoardState, playerMove)
                && playerCurrentPits[expectedLastSeedPitZeroBasedPositionInPlayerPits] == 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLastSeedInPlayerHouse(BoardState preMoveBoardState, PlayerMove playerMove) {
        return isLastSeedInPlayerPits(preMoveBoardState, playerMove)
                && !isLastSeedInPlayerStore(preMoveBoardState, playerMove);
    }

    private boolean isLastSeedInPlayerStore(BoardState preMoveBoardState, PlayerMove playerMove) {
        int[] playerPits = choosePlayerPits(preMoveBoardState, playerMove.player);
        int expectedLastSeedPitZeroBasedPositionInPlayerPits = calcExpectedLastSeedPitZeroBasedPositionInPlayerPits(preMoveBoardState, playerMove);

        if (isLastSeedInPlayerPits(preMoveBoardState, playerMove) &&
                (expectedLastSeedPitZeroBasedPositionInPlayerPits == playerPits.length - 1)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLastSeedInPlayerPits(BoardState preMoveBoardState, PlayerMove playerMove) {
        int expectedLastSeedPitZeroBasedPositionInPlayerPits = calcExpectedLastSeedPitZeroBasedPositionInPlayerPits(preMoveBoardState, playerMove);
        return !(expectedLastSeedPitZeroBasedPositionInPlayerPits == -1);
    }

    private boolean isHouseSelectedByPlayerEmpty(BoardState preMoveBoardState, PlayerMove playerMove) {
        int[] playerPits = choosePlayerPits(preMoveBoardState, playerMove.player);
        int seedsAtSelectedHouse = playerPits[playerMove.selectedHouse - 1];

        return (seedsAtSelectedHouse == 0);
    }

    private void playCaptureMove(BoardState preMoveBoardState, PlayerMove playerMove) {
        int expectedLastSeedPitZeroBasedPositionInPlayerPits = calcExpectedLastSeedPitZeroBasedPositionInPlayerPits(preMoveBoardState, playerMove);
        PlayerMove captureMove = new PlayerMove(playerMove.player, expectedLastSeedPitZeroBasedPositionInPlayerPits + 1);
        board.capture(captureMove);
    }

    private int calcExpectedLastSeedPitZeroBasedPositionInPlayerPits(BoardState preMoveBoardState, PlayerMove playerMove) {
        int numOfAllPitsOnBoard = preMoveBoardState.playerOnePits.length + preMoveBoardState.playerTwoPits.length;
        int seedsAtSelectedHouse = getSeedsAtSelectedHouse(preMoveBoardState, playerMove);
        int numOfPlayerPits = choosePlayerPits(preMoveBoardState, playerMove.player).length;

        int expectedLastSeedPitZeroBasedPositionInBoard =
                ((seedsAtSelectedHouse - (numOfAllPitsOnBoard - playerMove.selectedHouse)) % numOfAllPitsOnBoard) - 1;

        return (expectedLastSeedPitZeroBasedPositionInBoard >= numOfPlayerPits
                ? -1 : expectedLastSeedPitZeroBasedPositionInBoard);
    }

    private int[] choosePlayerPits(BoardState boardState, Player player) {
        return (player == Player.PLAYER_ONE ? boardState.playerOnePits : boardState.playerTwoPits);
    }

    private GameState choosePlayerTurn(Player player) {
        return (player == Player.PLAYER_ONE ? GameState.PLAYER_ONE_TURN : GameState.PLAYER_TWO_TURN);
    }

    private int getSeedsAtSelectedHouse(BoardState boardState, PlayerMove playerMove) {
        int[] playerPits = choosePlayerPits(boardState, playerMove.player);
        return playerPits[playerMove.selectedHouse - 1];
    }

    private boolean isGameFinished() {
        BoardState boardState = board.getCurrentBoardState();

        int[] playerOnePits = boardState.playerOnePits;
        int[] playerTwoPits = boardState.playerTwoPits;

        if (isPlayerHousesAllEmpty(playerOnePits) || isPlayerHousesAllEmpty(playerTwoPits)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPlayerHousesAllEmpty(int[] playerPits) {
        for (int seeds : playerPits) {
            if (seeds > 0) {
                return false;
            }
        }
        return true;
    }



}
