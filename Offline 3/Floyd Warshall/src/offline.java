import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class FloydWarshall{

    public int[][] allPairShortestPath(int[][] adjMatrix)
    {

        int n = adjMatrix.length;
        int[][] cost = new int[n][n];

        for (int v = 0; v < n; v++)
        {
            for (int u = 0; u < n; u++) {
                cost[v][u] = adjMatrix[v][u];
            }
        }

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                for (int k = 0; k < n; k++)
                {
                    if (cost[j][i] != Integer.MAX_VALUE && cost[i][k] != Integer.MAX_VALUE
                            && (cost[j][i] + cost[i][k] < cost[j][k]))
                    {
                        cost[j][k] = cost[j][i] + cost[i][k];
                    }
                }

                if (cost[j][j] < 0)
                {
                    System.out.println("Negative-weight cycle found!!");
                    return cost;
                }
            }
        }

        return cost;
    }
}

class MatrixMultiplication{

    public int[][] matMulti(int[][] adjMatrix, int[][] adjMatrix2)
    {

        int n = adjMatrix.length;
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                cost[i][j] = Integer.MAX_VALUE;

                for (int k = 0; k < n; k++)
                {
                    if (adjMatrix[i][k] != Integer.MAX_VALUE && adjMatrix2[k][j] != Integer.MAX_VALUE
                            && (adjMatrix[i][k] + adjMatrix2[k][j] < cost[i][j]))
                    {
                        cost[i][j] = adjMatrix[i][k] + adjMatrix2[k][j];
                    }
                }

                if (cost[j][j] < 0)
                {
                    System.out.println("Negative-weight cycle found!!");
                    return cost;
                }

            }
        }

        return cost;
    }

    public int[][] allPairShortestPath(int[][] adjMatrix){

        int k = adjMatrix.length;
        int[][] mat = new int[k][k];

        for (int v = 0; v < k; v++)
        {
            for (int u = 0; u < k; u++)
            {
                mat[v][u] = adjMatrix[v][u];
            }
        }

        for (int i=1; i<=k-2; i++)
        {
            mat = matMulti(mat,adjMatrix);
        }

        return mat;
    }
}

public class offline
{
    public static void printMatrix(int[][] result)
    {
        int n = result.length;
        String [][] temp = new String[n][n];
        System.out.println("Shortest distance matrix");
        for (int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                temp[i][j] = String.valueOf(result[i][j]);
                if(temp[i][j].equals(String.valueOf(Integer.MAX_VALUE))){
                    temp[i][j]="INF";
                }
                System.out.print(temp[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {

        MatrixMultiplication matrixMultiplication = new MatrixMultiplication();
        FloydWarshall floydWarshall = new FloydWarshall();

        final String FILE_NAME = "input.txt";
        String line;
        String[] tokens;

        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        line = br.readLine();
        tokens = line.split("\\s");

        int n = Integer.parseInt(tokens[0]);
        int m = Integer.parseInt(tokens[1]);
        int[][] graph = new int[n][n];

        for (int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i==j)
                    graph[i][j]=0;
                else
                    graph[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int i=0; i<m; i++){

            line = br.readLine();
            tokens = line.split("\\s");

            int x = Integer.parseInt(tokens[0])-1;
            int y = Integer.parseInt(tokens[1])-1;
            int z = Integer.parseInt(tokens[2]);

            graph[x][y] = z;

        }

        br.close();

        System.out.println();
        int[][] mat1 = matrixMultiplication.allPairShortestPath(graph);
        System.out.println("Using Matrix Multiplication Algorithm");
        printMatrix(mat1);

        System.out.println();
        int[][] mat2 = floydWarshall.allPairShortestPath(graph);
        System.out.println("Using Floyd-Warshall Algorithm");
        printMatrix(mat2);

    }
}