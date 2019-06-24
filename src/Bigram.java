class Bigram {

    private final String word1, word2;
    private int count;
    private int probability;

    public Bigram(String word1, String word2) {
        this.word1 = word1;
        this.word2 = word2;
        this.count = 0;
        this.probability = 0;
    }

    public String getWord1() {
        return word1;
    }

    public String getWord2() {
        return word2;
    }

    @Override
    public String toString() {
        return word1 + " " + word2;
    }

    @Override
    public int hashCode() {
        return word1.hashCode() ^ word2.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Bigram) && ((Bigram) obj).word1.equals(word1)
                && ((Bigram) obj).word2.equals(word2);
    }
}