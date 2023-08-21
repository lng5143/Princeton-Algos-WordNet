import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

public class SAP {
    private final Digraph G;
    private int length;
    private int[] vEdgeTo;
    private int[] wEdgeTo;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.G = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        Queue<Integer> vQueue = new Queue<>();
        Queue<Integer> wQueue = new Queue<>();
        vQueue.enqueue(v);
        wQueue.enqueue(w);
        return sap(vQueue, wQueue)[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        Queue<Integer> vQueue = new Queue<>();
        Queue<Integer> wQueue = new Queue<>();
        vQueue.enqueue(v);
        wQueue.enqueue(w);
        return sap(vQueue, wQueue)[0];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        Queue<Integer> vQueue = new Queue<>();
        Queue<Integer> wQueue = new Queue<>();

        for (int i: v)
            vQueue.enqueue(i);
        for (int i: w)
            wQueue.enqueue(i);
        return sap(vQueue, wQueue)[1];
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        Queue<Integer> vQueue = new Queue<>();
        Queue<Integer> wQueue = new Queue<>();

        for (int i: v)
            vQueue.enqueue(i);
        for (int i: w)
            wQueue.enqueue(i);
        return sap(vQueue, wQueue)[0];
    }

    //helper method: lockstep bfs with Queue as parameter
    private int[] sap(Queue<Integer> vQueue, Queue<Integer> wQueue) {
        int[] result = new int[2]; // sap[0] is shortest common ancestor, sap[1] is shortest ancestor path
        int shortestCommonAncestor = -1;
        int shortestAncestorPath = Integer.MAX_VALUE;

        int n = G.V();
        boolean[] vMarked = new boolean[n];
        boolean[] wMarked = new boolean[n];
        vEdgeTo = new int[n];
        wEdgeTo = new int[n];
        int[] vDistTo = new int[n];
        int[] wDistTo = new int[n];

        for (int i: vQueue)
            vDistTo[i] = 0;
        for (int i: wQueue)
            wDistTo[i] = 0;

        while (!vQueue.isEmpty() && !wQueue.isEmpty()) {
            if (!vQueue.isEmpty()) {
                int v0 = vQueue.dequeue();
                for (int v1 : G.adj(v0)) {
                    vDistTo[v1] = vDistTo[v0] + 1;
                    if (!vMarked[v1] && vDistTo[v1] < shortestAncestorPath) {
                        vQueue.enqueue(v1);
                        vMarked[v1] = true;
                        vEdgeTo[v1] = v0;
                        if (wMarked[v1] && vDistTo[v1] + wDistTo[v1] < shortestAncestorPath) {
                            shortestCommonAncestor = v1;
                            shortestAncestorPath = vDistTo[v1] + wDistTo[v1];
                        }
                    }
                }
            }

            if (!wQueue.isEmpty()) {
                int w0 = wQueue.dequeue();
                for (int w1 : G.adj(w0)) {
                    wDistTo[w1] = wDistTo[w0] + 1;
                    if (!wMarked[w1] && wDistTo[w1] < shortestAncestorPath) {
                        wQueue.enqueue(w1);
                        wMarked[w1] = true;
                        wEdgeTo[w1] = w0;
                        if (vMarked[w1] && wDistTo[w1] + vDistTo[w1] < shortestAncestorPath) {
                            shortestCommonAncestor = w1;
                            shortestAncestorPath = wDistTo[w1] + vDistTo[w1];
                        }
                    }
                }
            }
        }
        result[0] = shortestCommonAncestor;
        result[1] = shortestAncestorPath;
        return result;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        Digraph digraph = new Digraph(12);
    }
}