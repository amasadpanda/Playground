import java.io.File;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

class Vertex implements Comparable<Vertex>
{
    int id, dist;

    public Vertex(int id, int dist)
    {
        this.id = id;
        this.dist = dist;
    }

    @Override
    public int compareTo(Vertex o) {
        return dist - o.dist;
    }
}

class Graph
{
    Vertex[] graph;
    int[][] matrix;
    int nodes;

    public Graph(String filename) throws Exception
    {
        Scanner scan = new Scanner(new File(filename));
        int n = nodes = scan.nextInt();
        matrix = new int[n][n];

        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                matrix[i][j] = scan.nextInt();
            }
        }
    }

    public void runDjikstra(int source)
    {
        int[] dist = new int[nodes];
        boolean[] visited = new boolean[nodes];

        Arrays.fill(dist, (int)1e9);
        dist[source] = 0;

        PriorityQueue<Vertex> minHeap = new PriorityQueue<>();

        for(int i = 0; i < nodes; i++)
        {
            minHeap.add(new Vertex(i, dist[i]));
        }

        while(!minHeap.isEmpty())
        {
            Vertex v = minHeap.remove();

            if(visited[v.id]) continue;

            visited[v.id] = true;
            System.out.println(v);

            for(int i = 0; i < nodes; i++)
            {
                if(matrix[v.id][i] >= 0 && !visited[i] && v.dist + matrix[v.id][i] < dist[i])
                {
                    dist[i] = v.dist + matrix[v.id][i];
                    minHeap.add(new Vertex(i, dist[i]));
                }
            }
        }

    }
}

public class Djikstra {

}
