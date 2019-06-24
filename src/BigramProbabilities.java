import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BigramProbabilities {
    ArrayList<String> corpus;
    HashMap<String,Integer> tokenCount;
    HashMap<Bigram,CountAndProbability> bigramCount;
    TreeMap<Integer,Integer> bucket;
    int totalNumberOfBigrams;

    public void readCorpus() throws FileNotFoundException {
        Scanner sc;
        File file = new File("HW2_F18_NLP6320-NLPCorpusTreebank2Parts-CorpusA-Windows.txt");

        sc = new Scanner(file);
        corpus = new ArrayList<>();
        tokenCount = new HashMap<>();
        bigramCount = new HashMap<>();
        bucket = new TreeMap<>();
        String token = new String();

        while(sc.hasNext()){
            token = sc.next();
            corpus.add(token);
            if(!tokenCount.containsKey(token)){
                tokenCount.put(token,1);
            }else{
                tokenCount.put(token,tokenCount.get(token)+1);
            }
        }
    }

    public void countBigram(){
        for(int i=0; i < corpus.size() - 1 ; i++){
            if(!corpus.get(i+1).equals(".")){
                Bigram currentBigram = new Bigram(corpus.get(i),corpus.get(i+1));
                //System.out.println(currentBigram.getWord1() +" : "+currentBigram.getWord2());
                if(!bigramCount.containsKey(currentBigram)){
                    bigramCount.put(currentBigram,new CountAndProbability(1,currentBigram));
                }else{
                    CountAndProbability cnp = bigramCount.get(currentBigram);
                    cnp.incrementCount();
                    bigramCount.put(currentBigram,cnp);
                }
                totalNumberOfBigrams++;
            }else {
                i = i+1;
            }
        }
    }

    public void noSmoothingAndAddOneSmoothing(){
        try {
            PrintWriter noSmoothingWriter = new PrintWriter("No_Smoothing.txt", "UTF-8");
            PrintWriter addOneSmoothingWriter = new PrintWriter("Add_One_Smoothing.txt", "UTF-8");
            noSmoothingWriter.format("%32s %20s %20s","Bigram","Count","Probability");
            noSmoothingWriter.println();
            addOneSmoothingWriter.format("%32s %20s %20s","Bigram","Count","Probability");
            addOneSmoothingWriter.println();

            for(int i=0; i < corpus.size() - 1 ; i++){
                if(!corpus.get(i+1).equals(".")){
                    Bigram currentBigram = new Bigram(corpus.get(i),corpus.get(i+1));
                    CountAndProbability currentCP = bigramCount.get(currentBigram);
                    currentCP.noSmoothingProbability = (float) currentCP.getCount() / tokenCount.get(currentBigram.getWord1());
                    currentCP.addOneSmoothingProbability = (float) (currentCP.getCount() + 1) / (tokenCount.get(currentBigram.getWord1()) + tokenCount.size());
                    noSmoothingWriter.format("%32s %20s %20s",currentBigram.toString(),currentCP.count,currentCP.noSmoothingProbability);
                    noSmoothingWriter.println();
                    addOneSmoothingWriter.format("%32s %20s %20s",currentBigram.toString(),currentCP.count,currentCP.addOneSmoothingProbability);
                    addOneSmoothingWriter.println();
                    //System.out.println(currentBigram.toString()+" - "+currentCP.getNoSmoothingProbability() +":"+ currentCP.getAddOneSmoothingProbability());
                }else {
                    i = i+1;
                }
            }
            noSmoothingWriter.close();


        }catch (Exception e){

        }

    }

    public void goodTuring() throws IOException {
        HashMap<Integer,Float> cStar = new HashMap<>();
        HashMap<Integer,Float> pStar = new HashMap<>();
        PrintWriter goodTuringWriter = new PrintWriter("Good_Turing_Smoothing.txt", StandardCharsets.UTF_8);
        goodTuringWriter.format("%32s %20s %20s","Bigram","Count*","Probability");
        goodTuringWriter.println();

        for(int i=0; i < corpus.size() - 1 ; i++){
            if(!corpus.get(i+1).equals(".")){
                Bigram currentBigram = new Bigram(corpus.get(i),corpus.get(i+1));
                Integer currentCount = bigramCount.get(currentBigram).getCount();
                if(!bucket.containsKey(currentCount)){
                   bucket.put(currentCount,1);
                }else{
                    bucket.put(currentCount,bucket.get(currentCount)+1);
                }
            }else {
                i = i+1;
            }
        }

        for(Map.Entry<Integer,Integer> entry : bucket.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            float cstar = (float)(key+1) * (bucket.get(key+1)==null?0:bucket.get(key+1)) / value;
            cStar.put(key,cstar);
            pStar.put(key,cstar/totalNumberOfBigrams);
            //System.out.println(key +" : "+ value +" - "+cstar +" : "+cstar/totalNumberOfBigrams);
        }

        pStar.put(0,(float)bucket.get(1)/totalNumberOfBigrams); //count = 0;

        for(int i=0; i < corpus.size() - 1 ; i++){
            if(!corpus.get(i+1).equals(".")){
                Bigram currentBigram = new Bigram(corpus.get(i),corpus.get(i+1));
                Integer currentCount = bigramCount.get(currentBigram).getCount();
                CountAndProbability cnp = bigramCount.get(currentBigram);
                cnp.goodTuringCount = cStar.get(currentCount);
                cnp.goodTuringProbability = pStar.get(currentCount);
                goodTuringWriter.format("%32s %20s %20s",currentBigram.toString(),cnp.goodTuringCount,cnp.goodTuringProbability);
                goodTuringWriter.println();
                //System.out.println(currentBigram.toString() +" : "+ currentCount +" : "+ cnp.goodTuringProbability);
            }else {
                i = i+1;
            }
        }


    }


    public static void main(String args[]) throws IOException {
        BigramProbabilities bigram = new BigramProbabilities();
        bigram.readCorpus();
        bigram.countBigram();
        bigram.noSmoothingAndAddOneSmoothing();
        bigram.goodTuring();
    }
}

