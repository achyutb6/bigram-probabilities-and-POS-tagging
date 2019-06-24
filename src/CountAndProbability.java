public class CountAndProbability {

    public Bigram bigram;
    public int count;
    public float addOneCount;
    public float goodTuringCount;
    public float noSmoothingProbability;
    public float addOneSmoothingProbability;
    public float goodTuringProbability;

    public CountAndProbability() {
        this.count = 0;
    }

    public CountAndProbability(int count,Bigram bigram) {
        this.count = count;
        this.bigram = bigram;
    }

    public int getCount() {
        return count;
    }

    public int getAddOneCount() {
        return count;
    }

    public int getGoodTuringtCount() {
        return count;
    }

    public float getNoSmoothingProbability(){
        return noSmoothingProbability;
    }

    public float getAddOneSmoothingProbability(){
        return addOneSmoothingProbability;
    }

    public float getGoodTuringProbability(){
        return goodTuringProbability;
    }

    public void incrementCount(){
        count++;
    }


}