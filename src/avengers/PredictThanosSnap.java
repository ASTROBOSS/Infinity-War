package avengers;

/**
 * Given an adjacency matrix, use a random() function to remove half of the nodes. 
 * Then, write into the output file a boolean (true or false) indicating if 
 * the graph is still connected.
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * PredictThanosSnapInputFile name is passed through the command line as args[0]
 * Read from PredictThanosSnapInputFile with the format:
 *    1. seed (long): a seed for the random number generator
 *    2. p (int): number of people (vertices in the graph)
 *    2. p lines, each with p edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note: the last p lines of the PredictThanosSnapInputFile is an ajacency matrix for
 * an undirected graph. 
 * 
 * The matrix below has two edges 0-1, 0-2 (each edge appear twice in the matrix, 0-1, 1-0, 0-2, 2-0).
 * 
 * 0 1 1 0
 * 1 0 0 0
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * Delete random vertices from the graph. You can use the following pseudocode.
 * StdRandom.setSeed(seed);
 * for (all vertices, go from vertex 0 to the final vertex) { 
 *     if (StdRandom.uniform() <= 0.5) { 
 *          delete vertex;
 *     }
 * }
 * Answer the following question: is the graph (after deleting random vertices) connected?
 * Output true (connected graph), false (unconnected graph) to the output file.
 * 
 * Note 1: a connected graph is a graph where there is a path between EVERY vertex on the graph.
 * 
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, isConnected is true if the graph is connected,
 *   false otherwise):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(isConnected);
 * 
 * @author Yashas Ravi
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap.in predictthanossnap.out
*/

public class PredictThanosSnap {
	 
    public static void main (String[] args) {
 
        if ( args.length < 2 ) {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        StdRandom.setSeed(StdIn.readLong());
        int n = StdIn.readInt();
        int[][] adjMatrix = new int[n][n];
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                adjMatrix[i][j] = StdIn.readInt();
        int numDeleted = 0;
        for(int i = 0; i < n; i++) {
            if(StdRandom.uniform() <= 0.5) {
                for(int j = 0; j < n; j++) {
                    adjMatrix[j][i] = 0;
                    adjMatrix[i][j] = 0;
                }
                numDeleted++;
            }
        }
        int start = -1;
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if (adjMatrix[i][j] != 0)
                    start = i;
        if(start == -1) StdOut.print("false");
        else {
            boolean[] visited = new boolean[n];
            dfs(adjMatrix, visited, start);
            int totalVisited = 0;
            for(int i = 0; i < n; i++) {
                if (visited[i] == true) {
                    totalVisited++;
                }
            }
            if(totalVisited + numDeleted < n) {
                StdOut.print("false");
            }
            else StdOut.print("true");
        }
    	// WRITE YOUR CODE HERE

    }
    private static void dfs(int[][] adjMatrix, boolean visited[], int vertex) {
        visited[vertex] = true;
        for(int i = 0; i < adjMatrix.length; i++) {
            if(!visited[i] && adjMatrix[vertex][i] == 1)
                dfs(adjMatrix, visited, i);
        }
    }
}
