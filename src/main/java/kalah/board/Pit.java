package kalah;

public class Pit {

    public enum PitType {
        STORE, HOUSE
    }

    private int seeds;
    private PitType pitType;
    private Pit nextPit;

    public Pit(int initialSeeds, PitType pitType) {
        this.seeds = initialSeeds;
        this.pitType = pitType;
        this.nextPit = null;
    }

    protected Pit getNextPit() {
        return this.nextPit;
    }

    protected void setNextPit(Pit nextPit) {
        this.nextPit = nextPit;
    }

    protected int getSeeds() {
        return seeds;
    }

    protected void addSeeds(int newSeeds) {
        this.seeds += newSeeds;
    }

    protected void setSeedsToZero() {
        this.seeds = 0;
    }

    protected void incrementSeeds() {
        this.seeds++;
    }

    protected PitType getPitType() {
        return this.pitType;
    }
}
