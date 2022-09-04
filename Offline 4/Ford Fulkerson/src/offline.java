import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.LinkedList;

class FordFulkerson {

    int vertex;

    FordFulkerson (int vertex) {
        this.vertex = vertex;
    }

    boolean bfs(int[][] residualGraph, int source, int sink, int[] parent) {

        boolean[] visited = new boolean[vertex];
        for (int i = 0; i < vertex; i++)
            visited[i] = false;

        LinkedList<Integer> queueVertex = new LinkedList<>();
        queueVertex.add(source);
        parent[source] = -1;
        visited[source] = true;

        while (queueVertex.size() != 0) {

            int i = queueVertex.poll();
            for (int j = 0; j < vertex; j++) {

                if (!visited[j] && residualGraph[i][j] > 0) {

                    if (j == sink) {
                        parent[j] = i;
                        return true;
                    }
                    queueVertex.add(j);
                    parent[j] = i;
                    visited[j] = true;
                }
            }
        }

        return false;
    }


    int maxFlow(int[][] graph, int source, int sink) {

        int i, j;

        int[][] residualGraph = new int[vertex][vertex];

        for (i = 0; i < vertex; i++) {
            for (j = 0; j < vertex; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }

        int[] parent = new int[vertex];
        int maxFlow = 0;

        while (bfs(residualGraph, source, sink, parent)) {

            int pathFlow = Integer.MAX_VALUE;
            for (j = sink; j != source; j = parent[j]) {
                i = parent[j];
                if(residualGraph[i][j] < pathFlow)
                    pathFlow = residualGraph[i][j];
            }

            for (j = sink; j != source; j = parent[j]) {
                i = parent[j];
                residualGraph[i][j] -= pathFlow;
                residualGraph[j][i] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

}

public class offline {

    public static void main(String[] args) throws IOException {

        final String FILE_NAME = "input2.txt";
        String line;
        String[] tokens;

        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        line = br.readLine();
        tokens = line.split("\\s");

        int v = Integer.parseInt(tokens[0]);

        int n = v + 2 + (v-1)*(v-2)/2;  //total nodes
        int[][] graph = new int[n][n];

        String[] name = new String[v+1];
        int[] w = new int[v+1];
        int[] l = new int[v+1];
        int[] r = new int[v+1];
        List<Integer> temp = new ArrayList<>();

        for (int i=1; i<=v; i++){

            line = br.readLine();
            tokens = line.split("\\s");

            name[i] = tokens[0];

            w[i] = Integer.parseInt(tokens[1]);
            l[i] = Integer.parseInt(tokens[2]);
            r[i] = Integer.parseInt(tokens[3]);

            for(int j=1; j<=v; j++){
                temp.add(Integer.parseInt(tokens[j+3]));  //list of rem games
            }
        }

        br.close();

        int[][] g = new int[v+1][v+1];
        boolean[][] graphCheck = new boolean[v+1][v+1];

        int a = 0;
        for(int j=1; j<=v; j++){
            for(int k=1; k<=v; k++){
                g[j][k] = temp.get(a);
                graphCheck[j][k] = false;
                a++;
            }
        }

        for (int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                graph[i][j] = 0;
            }
        }

        System.out.println();
        FordFulkerson fordFulkerson = new FordFulkerson(n);

        int curr = 1;   //current team
        while(curr <= v) {

            int m = 2;
            int sum = 0;

            for (int i = 1; i <= v; i++) {
                for (int j = 1; j <= v; j++) {
                    if(i!=j && i!=curr && j!= curr && !graphCheck[i][j]){

                        graph[0][v+m] = g[i][j];  //artificial source 0 to each game node
                        sum += graph[0][v+m];
                        graphCheck[i][j]=true;
                        graphCheck[j][i]=true;
                        graph[v+m][i]=Integer.MAX_VALUE;   // game nodes to team nodes
                        graph[v+m][j]=Integer.MAX_VALUE;   // game nodes to team nodes
                        m++;

                    }
                }
            }

            for (int i=1; i<=v; i++){
                if(i!=curr){
                    graph[i][v+1] = w[curr] + r[curr] - w[i];   // team nodes to sink (v+1)
                }
            }

            int maxFlow = fordFulkerson.maxFlow(graph, 0, v+1);
            //System.out.println("The maximum possible flow for " + name[curr] + " is " + maxFlow);


            /*if(sum == maxFlow)
                System.out.println("Saturated");
            else
                System.out.println("Not Saturated");*/

            if(sum != maxFlow) {         //max flow does not saturate all arcs leaving source
                System.out.println(name[curr] + " is eliminated.");
                System.out.println();
            }

            for(int j=1; j<=v; j++){
                for(int k=1; k<=v; k++){
                    graphCheck[j][k] = false;
                }
            }

            for (int i=0; i<n; i++){
                for(int j=0; j<n; j++){
                    graph[i][j] = 0;
                }
            }

            curr++;  // next team
        }
    }
}

