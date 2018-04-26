package kalah;

import com.qualitascorpus.testsupport.IO;

public class Outputter {

    private IO io;

    public Outputter(IO io) {
        this.io = io;
    }

    public void showCurrentBoardState(int[] numSeedsInPlayerOnePits, int[] numSeedsInPlayerTwoPits) {

		io.println("+----+-------+-------+-------+-------+-------+-------+----+");

		io.print("| P2 ");
		for (int i = numSeedsInPlayerTwoPits.length - 2; i >= 0; i--) {
		    int houseNumber = i + 1;
		    int seedsCount = numSeedsInPlayerTwoPits[i];

		    String houseNumberAsString = addPrefixBlankSpaceIfSingleDigit(houseNumber);
		    String seedsCountAsString = addPrefixBlankSpaceIfSingleDigit(seedsCount);
			io.print("|" + houseNumberAsString + "[" + seedsCountAsString + "] ");
		}

		String p1SeedsInStoreAsString = createStringOfPlayerSeedsInStore(numSeedsInPlayerOnePits[numSeedsInPlayerOnePits.length - 1]);
		io.println(p1SeedsInStoreAsString);

		io.println("|    |-------+-------+-------+-------+-------+-------|    |");

		String p2SeedsInStoreAsString = createStringOfPlayerSeedsInStore(numSeedsInPlayerTwoPits[numSeedsInPlayerTwoPits.length - 1]);
		io.print(p2SeedsInStoreAsString);

		for (int i = 0; i < numSeedsInPlayerOnePits.length - 1; i++) {
		    int houseNumber = i + 1;
		    int seedsCount = numSeedsInPlayerOnePits[i];

		    String houseNumberAsString = addPrefixBlankSpaceIfSingleDigit(houseNumber);
		    String seedsCountAsString = addPrefixBlankSpaceIfSingleDigit(seedsCount);
		    io.print(houseNumberAsString + "[" + seedsCountAsString + "] |");
        }

        io.println(" P1 |");

        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    private String createStringOfPlayerSeedsInStore(int numSeedsInStore) {
        String numSeedsInStoreAsString = addPrefixBlankSpaceIfSingleDigit(numSeedsInStore);
        return "| " + numSeedsInStoreAsString + " |";
    }

    private String addPrefixBlankSpaceIfSingleDigit(int digit) {
        return (digit < 9 ? (" " + digit) : Integer.toString(digit));
    }
}
