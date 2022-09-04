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
        //System.out.println("Out" + vertex);
        if (vertex >= 0)
        {
            getPath(parent, parent[vertex], path);
            path.add(vertex);
            //System.out.println(vertex);
        }

    }

    public void findShortestPath(Graph graph, int source, int destination, int n)
    {
        PriorityQueue <Node> minDistNode = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));
        //BinaryHeap <Node> minDistNode = new BinaryHeap<>(Comparator.comparingInt(node -> node.weight));
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

        System.out.println("Shortest path cost: " + dist[destination]);
        System.out.print(path.get(0)+ " -> ");
        for(int i=1; i<path.size()-1; i++){
            System.out.print(path.get(i) + " -> " );
        }
        System.out.print(path.get(path.size()-1));
        System.out.println();
        System.out.println();
    }
}

class BellmanFord{

    void getPath(int[] parent, int vertex, List<Integer> path)
    {
        if (vertex >= 0) {
            getPath(parent, parent[vertex], path);
            path.add(vertex);
        }
    }

    public void findShortestPath(List<Edge> edges, int source,  int destination, int n)
    {

        int[] dist = new int[n];
        int[] parent = new int[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        Arrays.fill(parent, -1);

        for (int i = 0; i < n-1; i++)
        {
            for (Edge edge: edges)
            {
                int u = edge.source;
                int v = edge.dest;
                int w = edge.weight;

                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v])
                {
                    dist[v] = dist[u] + w;
                    parent[v] = u;
                }
            }
        }

        for (Edge edge: edges)
        {
            int u = edge.source;
            int v = edge.dest;
            int w = edge.weight;

            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v])
            {
                System.out.println("\nThe graph contains a negative cycle");
                System.out.println();
                return;
            }
        }

        List<Integer> path = new ArrayList<>();
        getPath(parent, destination, path);

        System.out.println("\nThe graph does not contain a negative cycle");
        System.out.println("Shortest path cost: " + dist[destination]);
        System.out.print(path.get(0)+ " -> ");
        for(int i=1; i<path.size()-1; i++){
            System.out.print(path.get(i) + " -> " );
        }
        System.out.print(path.get(path.size()-1));
        System.out.println();
        System.out.println();
    }
}

public class offline
{
    public static void main(String[] args)
    {
            int m,n,x,y,z,source,destination;
            Graph graph;

            Dijkstra dijkstra = new Dijkstra();
            BellmanFord bellmanFord = new BellmanFord();

            List<Edge> edges = new ArrayList<>();
            Scanner sc = new Scanner(System.in);

            System.out.println("Choose one option-");
            System.out.println("1.Dijkstra’s algorithm  2.Bellman-Ford algorithm");
            int choice = sc.nextInt();
            System.out.println("Please give the inputs");

            if (choice == 1) {

                n = sc.nextInt();
                m = sc.nextInt();

                for (int i = 0; i < m; i++) {

                    x = sc.nextInt();
                    y = sc.nextInt();
                    z = sc.nextInt();
                    edges.add(new Edge(x, y, z));
                }

                graph = new Graph(edges, n);

                source = sc.nextInt();
                destination = sc.nextInt();

                dijkstra.findShortestPath(graph, source, destination, n);
            }

            else if (choice == 2) {

                n = sc.nextInt();
                m = sc.nextInt();

                for (int i = 0; i < m; i++) {

                    x = sc.nextInt();
                    y = sc.nextInt();
                    z = sc.nextInt();
                    edges.add(new Edge(x, y, z));
                }

                source = sc.nextInt();
                destination = sc.nextInt();

                bellmanFord.findShortestPath(edges, source, destination, n);
            }

            else
                System.out.println("Wrong choice!!");

    }
}
