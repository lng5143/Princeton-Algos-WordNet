public class Outcast {
    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxId = 0;
        int maxValue = 0;
        for (int i = 0; i < nouns.length; i++) {
            int distSum = 0;
            for (int j = 0; j < nouns.length; j++) {
                int dist = wordnet.distance(nouns[i], nouns[j]);
                distSum += dist;
            }
            if (distSum > maxValue)
                maxId = i;
        }
        return nouns[maxId];
    }

    // see test client below
    public static void main(String[] args) {

    }
}