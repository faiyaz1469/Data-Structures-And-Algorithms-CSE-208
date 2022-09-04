import java.io.*;
import java.util.*;

class Edge
{
    int source, dest, weight;
    public Edge(int source, int dest, int weight)
    {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }
}

class Node
{
    int vertex, weight;
    public Node(int vertex, int weight)
    {
        this.vertex = vertex;
        this.weight = weight;
    }
}


class Graph
{
    List<List<Edge>> adjList;
    Graph(List<Edge> edges, int n)
    {
        adjList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (Edge edge: edges) {
            adjList.get(edge.source).add(edge);
        }
    }
}

class Dijkstra{

    private void getPath(int[] parent, int vertex, List<Integer> path)
    {
        if (vertex >= 0)
        {
            getPath(parent, parent[vertex], path);
            path.add(vertex);
        }
    }

    public void findShortestPathBin(Graph graph, int source, int destination, int n) throws Exception {
        //PriorityQueue <Node> minDistNode = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));
        BinaryHeap <Node> minDistNode = new BinaryHeap<>(Comparator.comparingInt(node -> node.weight));
        minDistNode.add(new Node(source, 0));

        int[] dist = new int[n];
        int[] parent = new int[n];
        boolean[] done = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[source] = 0;
        parent[source] = -1;
        done[source] = true;

        while (!minDistNode.isEmpty())
        {
            Node node = minDistNode.poll();
            int u = node.vertex;

            for (Edge edge: graph.adjList.get(u))
            {
                int v = edge.dest;
                int w = edge.weight;

                if (!done[v] && dist[u] + w < dist[v])
                {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    minDistNode.add(new Node(v, dist[v]));
                }
            }
            done[u] = true;
        }

        List<Integer> path = new ArrayList<>();
        getPath(parent, destination, path);

        /*System.out.println();
        System.out.println("Shortest path cost bin: " + dist[destination]);
        System.out.print(path.get(0)+ " -> ");
        for(int i=1; i<path.size()-1; i++){
            System.out.print(path.get(i) + " -> " );
        }
        System.out.print(path.get(path.size()-1));
        System.out.println();*/

        /*int size = path.size()-1;
        System.out.print(size+" "+dist[destination]+" ");*/
    }

    public String findShortestPathFib(Graph graph, int source, int destination, int n)
    {
        FibonacciHeap <Node> minDistNode = new FibonacciHeap<>(Comparator.comparingInt(node -> node.weight));
        minDistNode.insert(new Node(source, 0));

        int[] dist = new int[n];
        int[] parent = new int[n];
        boolean[] done = new boolean[n];

        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[source] = 0;
        parent[source] = -1;
        done[source] = true;

        while (!minDistNode.isEmpty())
        {
            Node node = minDistNode.extractMin();
            int u = node.vertex;

            for (Edge edge: graph.adjList.get(u))
            {
                int v = edge.dest;
                int w = edge.weight;

                if (!done[v] && dist[u] + w < dist[v])
                {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                    minDistNode.insert(new Node(v, dist[v]));
                }
            }
            done[u] = true;
        }

        List<Integer> path = new ArrayList<>();
        getPath(parent, destination, path);

        /*System.out.println();
        System.out.println("Shortest path cost fib: " + dist[destination]);
        System.out.print(path.get(0)+ " -> ");
        for(int i=1; i<path.size()-1; i++){
            System.out.print(path.get(i) + " -> " );
        }
        System.out.print(path.get(path.size()-1));
        System.out.println();*/

        int size = path.size()-1;
        System.out.print(size+" "+dist[destination]+" ");

        return size + " " + dist[destination] + " ";
    }
}


public class offline
{
    public static void main(String[] args) throws Exception {

        int u,v,w,source,destination;
        Graph graph;

        Dijkstra dijkstra = new Dijkstra();
        List<Edge> edges = new ArrayList<>();

        BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));

        final String FILE_NAME_1 = "input1.txt";
        String line;
        String[] tokens;

        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME_1));
        line = br.readLine();
        tokens = line.split("\\s");

        int n = Integer.parseInt(tokens[0]);
        int m = Integer.parseInt(tokens[1]);

        for (int i=0; i<m; i++){

            line = br.readLine();
            tokens = line.split("\\s");

            u = Integer.parseInt(tokens[0]);
            v = Integer.parseInt(tokens[1]);
            w = Integer.parseInt(tokens[2]);

            edges.add(new Edge(u, v, w));
            edges.add(new Edge(v, u, w));

        }

        br.close();

        graph = new Graph(edges, n);

        final String FILE_NAME_2 = "input2.txt";
        String line2;
        String[] tokens2;

        BufferedReader br2 = new BufferedReader(new FileReader(FILE_NAME_2));
        line2 = br2.readLine();
        tokens2 = line2.split("\\s");

        int k = Integer.parseInt(tokens2[0]);

        for (int i=0; i<k; i++){

            line2 = br2.readLine();
            tokens2 = line2.split("\\s");

            source = Integer.parseInt(tokens2[0]);
            destination = Integer.parseInt(tokens2[1]);

            long start1 = System.nanoTime();
            dijkstra.findShortestPathBin(graph, source, destination, n);
            long end1 = System.nanoTime();

            long start2 = System.nanoTime();
            String fileString = dijkstra.findShortestPathFib(graph, source, destination, n);
            long end2 = System.nanoTime();

            System.out.print((end1-start1)+" ns ");
            fileString = fileString.concat(String.valueOf((end1-start1)));
            fileString += " ns ";

            System.out.print((end2-start2)+" ns ");
            fileString = fileString.concat(String.valueOf((end2-start2)));
            fileString += " ns \n";

            bw.write(fileString);
            System.out.println();
        }

        br2.close();
        bw.close();
    }
}
