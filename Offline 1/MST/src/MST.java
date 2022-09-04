import java.util.*;

class Edge
{
    int source, destination;
    float weight;

    public Edge(int src, int dest, float weight)
    {
        this.source = src;
        this.destination = dest;
        this.weight = weight;
    }

}

class Edge2
{
    int source, destination;

    public Edge2(int src, int dest)
    {
        this.source = src;
        this.destination = dest;
    }

    @Override
    public String toString() {
        return "(" + source + ", " + destination + ")";
    }
}

class unionFindAlgo
{
    HashMap <Integer, Integer> ancestor = new HashMap<>();

    public void init(int n)
    {
        for (int i = 0; i < n; i++) {
            ancestor.put(i, -1);
        }
    }

    private int find(int k)
    {
        if (ancestor.get(k) == -1) {
            return k;
        }

        return find(ancestor.get(k));
    }

    private void union(int a, int b)
    {
        int x = find(a);
        int y = find(b);

        ancestor.put(x, y);
    }

    public static List<Edge2> Kruskal(List<Edge> edges, int n)
    {
        unionFindAlgo ds = new unionFindAlgo();
        ds.init(n);

        int index = 0;
        float minDist = 0;
        List<Edge2> MST = new ArrayList<>();

        edges.sort(Comparator.comparingInt(e -> (int) e.weight));

        while (MST.size() != n - 1)
        {
            Edge inputEdge = edges.get(index++);

            int x = ds.find(inputEdge.source);
            int y = ds.find(inputEdge.destination);

            if (x != y)
            {
                MST.add(new Edge2(inputEdge.source,inputEdge.destination));
                ds.union(x, y);
                minDist = minDist + inputEdge.weight;
            }
        }

        System.out.println("Cost of the minimum spanning tree by Kruskal's: " + minDist);

        return MST;
    }

    public static List<Edge2> Prim(float[][] G, int V) {

        int INF = Integer.MAX_VALUE;
        boolean[] visited = new boolean[V];
        Arrays.fill(visited, false);
        visited[0] = true;

        float minDist = 0;
        List<Edge2> MST2 = new ArrayList<>();

        while (MST2.size() != V - 1) {

            int min = INF;
            int x = 0;
            int y = 0;

            for (int i = 0; i < V; i++) {
                if (visited[i]) {
                    for (int j = 0; j < V; j++) {
                        if (!visited[j] && G[i][j] != 0) {
                            if (G[i][j] < min) {
                                min = (int) G[i][j];
                                x = i;
                                y = j;
                            }
                        }
                    }
                }
            }

            MST2.add(new Edge2(x,y));
            minDist = minDist + G[x][y];
            visited[y] = true;
        }

        System.out.println("Cost of the minimum spanning tree by Prim's: " + minDist);

        return MST2;
    }

    public static void main(String[] args)
    {

        List<Edge> edges = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        float[][] graph = new float[n][n];

        for (int i=0; i<m; i++){

            int x = sc.nextInt();
            int y = sc.nextInt();
            float z = sc.nextFloat();

            graph[x][y] = z;
            graph[y][x] = z;
            edges.add(new Edge(x,y,z));

        }

        List<Edge2> e1 = Prim(graph, n);
        System.out.println("List of edges selected by Prim’s: " + e1);

        List<Edge2> e2 = Kruskal(edges, n);
        System.out.println("List of edges selected by Kruskal’s: " + e2);

    }

}

