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

    public void findShortestPath(Graph graph, int source, int destination, int n)
    {
        PriorityQueue <Node> minDistNode = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));
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

        //System.out.println("Shortest path cost: " + dist[destination]);
        int size = path.size()-1;
        System.out.println(size);
        //System.out.println();
    }
}


public class Online
{
    public static void main(String[] args)
    {
        int m,n,x,y,z,source,destination;
        Graph graph;

        Dijkstra dijkstra = new Dijkstra();

        List<Edge> edges = new ArrayList<>();
        Scanner sc = new Scanner(System.in);


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

        System.out.println("Best Values-");
        for ( destination = 0; destination < n; destination++) {
            dijkstra.findShortestPath(graph, source, destination, n);
        }

    }
}
