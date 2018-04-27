package kalah.board;

import kalah.player.Player;
import kalah.player.PlayerMove;

import java.util.ArrayList;
import java.util.List;

public class Board {

    protected final int numHousesPerPlayer;
    protected final int numSeedsPerHouseInitial;

    private final int zeroBasedPositionOfStoreInPlayerPits;

    private List<Pit> playerOnePits;
    private List<Pit> playerTwoPits;

    public Board (int numHousesPerPlayer, int numSeedsPerHouseInitial) {
        this.numHousesPerPlayer = numHousesPerPlayer;
        this.numSeedsPerHouseInitial = numSeedsPerHouseInitial;
        zeroBasedPositionOfStoreInPlayerPits = numHousesPerPlayer;

        initialisePlayerPits();
        arrangePitsAsCircularLinkedList();
    }

    private void initialisePlayerPits() {
        playerOnePits = new ArrayList<Pit>();
        playerTwoPits = new ArrayList<Pit>();

        addHousesToPlayerPits();
        addStoresToPlayerPits();
    }

    private void addHousesToPlayerPits() {
        for (int i = 0; i < numHousesPerPlayer; i++) {
            playerOnePits.add(new Pit(numSeedsPerHouseInitial, Pit.PitType.HOUSE, Player.PLAYER_ONE, i));
            playerTwoPits.add(new Pit(numSeedsPerHouseInitial, Pit.PitType.HOUSE, Player.PLAYER_TWO, i));
        }
    }

    private void addStoresToPlayerPits() {
        playerOnePits.add(new Pit(0, Pit.PitType.STORE, Player.PLAYER_ONE, zeroBasedPositionOfStoreInPlayerPits));
        playerTwoPits.add(new Pit(0, Pit.PitType.STORE, Player.PLAYER_TWO, zeroBasedPositionOfStoreInPlayerPits));
    }

    private void arrangePitsAsCircularLinkedList() {
        linkPlayersHouses();
        linkPlayersStores();
    }

    private void linkPlayersHouses() {
        for (int i = 0; i < numHousesPerPlayer - 1; i++) {
            playerOnePits.get(i).setNextPit(playerOnePits.get(i+1));
            playerTwoPits.get(i).setNextPit(playerTwoPits.get(i+1));
        }
    }

    private void linkPlayersStores() {
        linkPlayerLastHouseToStore(playerOnePits.get(numHousesPerPlayer - 1), playerOnePits.get(zeroBasedPositionOfStoreInPlayerPits));
        linkPlayerLastHouseToStore(playerTwoPits.get(numHousesPerPlayer - 1), playerTwoPits.get(zeroBasedPositionOfStoreInPlayerPits));

        linkPlayerStoreToOpponentNextHouse(playerOnePits.get(zeroBasedPositionOfStoreInPlayerPits), playerTwoPits.get(0));
        linkPlayerStoreToOpponentNextHouse(playerTwoPits.get(zeroBasedPositionOfStoreInPlayerPits), playerOnePits.get(0));
    }

    private void linkPlayerLastHouseToStore(Pit lastHouse, Pit store) {
        lastHouse.setNextPit(store);
    }

    private void linkPlayerStoreToOpponentNextHouse(Pit store, Pit opponentFirstHouse) {
        store.setNextPit(opponentFirstHouse);
    }

    public BoardState getCurrentBoardState() {
        int[] primitivePlayerOnePits = new int[playerOnePits.size()];
        int[] primitivePlayerTwoPits = new int[playerTwoPits.size()];

        for (int i = 0; i < playerOnePits.size(); i++) {
            primitivePlayerOnePits[i] = playerOnePits.get(i).getSeeds();
            primitivePlayerTwoPits[i] = playerTwoPits.get(i).getSeeds();
        }

        return new BoardState(primitivePlayerOnePits, primitivePlayerTwoPits);
    }

    protected Pit sow(PlayerMove playerMove) {
        List<Pit> playerPits = selectPlayerPits(playerMove.player);

        Pit houseSelectedByPlayer = playerPits.get(playerMove.selectedHouse - 1);
        int seedsToSow = houseSelectedByPlayer.getSeeds();

        houseSelectedByPlayer.setSeedsToZero();
        
        Pit currentPitToSowSeed = houseSelectedByPlayer.getNextPit();
        Pit pitOfLastSownSeed = houseSelectedByPlayer;

        while (seedsToSow > 0) {
            if (!isPitBelongedToPlayer(currentPitToSowSeed, playerMove.player)
                    && currentPitToSowSeed.getPitType() == Pit.PitType.STORE) {
                currentPitToSowSeed = currentPitToSowSeed.getNextPit();
                continue;
            }
            currentPitToSowSeed.incrementSeeds();
            seedsToSow--;
            currentPitToSowSeed = currentPitToSowSeed.getNextPit();
            pitOfLastSownSeed = pitOfLastSownSeed.getNextPit();
        }
        
        return pitOfLastSownSeed;
    }

    private boolean isPitBelongedToPlayer(Pit pit, Player player) {
        return pit.getOwnerPlayer() == player;
    }

    public void capture(PlayerMove playerMove) {
        List<Pit> captorPits = selectPlayerPits(playerMove.player);
        List<Pit> captivePits = (captorPits == playerOnePits) ? playerTwoPits : playerOnePits;

        Pit captorStore = captorPits.get(zeroBasedPositionOfStoreInPlayerPits);
        Pit captorHouse = captorPits.get(playerMove.selectedHouse - 1);
        Pit capturedHouse = captivePits.get(numHousesPerPlayer - playerMove.selectedHouse);

        captorStore.addSeeds(captorHouse.getSeeds() + capturedHouse.getSeeds());

        captorHouse.setSeedsToZero();
        capturedHouse.setSeedsToZero();
    }

    private List<Pit> selectPlayerPits(Player player) {
        switch(player) {
            case PLAYER_ONE:
                return playerOnePits;
            default:
                return playerTwoPits;
        }
    }
}
