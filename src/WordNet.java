import edu.princeton.cs.algs4.*;

import java.util.ArrayList;

public class WordNet {
    private final In inSynsets;
    private final In inHypernyms;
    private Digraph digraph = null;
    private SAP sap = null;
    private int synsetsCount = 0;
    private ST<String, Integer> nouns;
    private ArrayList<String> synsetsArr = new ArrayList<>();

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        inSynsets = new In(synsets);
        inHypernyms = new In(hypernyms);

        createDigraph();
        createSAP();
    }

    //helper method: create digraph from hypernyms
    private void createDigraph() {
        digraph = new Digraph(synsetsCount);

        while(!inHypernyms.isEmpty()) {
            String inHypernymsLine = inHypernyms.readLine();
            String[] hypernymsLineArr = inHypernymsLine.split(",");
            for (int i = 1; i < hypernymsLineArr.length; i++)
                digraph.addEdge(Integer.parseInt(hypernymsLineArr[0]), Integer.parseInt(hypernymsLineArr[i]));
        }
    }

    //helper method: create SAP from digraph
    private void createSAP() {
        sap = new SAP(digraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        //implement symbol table instead, noun is key, id is value
        //Stack<String> nouns = new Stack<>();
        nouns = new ST<>();

        //how to read line in synsets?
        while (!inSynsets.isEmpty()) {
            String inSynsetsLine = inSynsets.readLine();
            String[] splitLine = inSynsetsLine.split(",");
            synsetsArr.add(splitLine[1]);

            String[] nounsArr = splitLine[1].split(" ");
            for (String s : nounsArr) nouns.put(s, Integer.parseInt(splitLine[0]));
            synsetsCount++;
        }
        return nouns;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        //is this case sensitive?

        return nouns.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        //how to get noun id
        //implement symbol table data structure
        //binary search, because the table is already sorted

        int idA = nouns.get(nounA), idB = nouns.get(nounB);
        return sap.length(idA, idB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        //how to get synset
        //option 1: implement another ST, key is id, value is synset
        //option 2: implement an array(list)

        int idA = nouns.get(nounA), idB = nouns.get(nounB);
        int sapID = sap.ancestor(idA, idB);
        return synsetsArr.get(sapID);
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}