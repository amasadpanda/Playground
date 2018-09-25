import java.util.*;
import java.io.*;

public class maze
{
  public static void main(String args[]) throws Exception
  {
    if(args.length < 1)
    {
      System.out.println("Enter input file!");
      System.exit(1);
    }
    Scanner scan = new Scanner(new File(args[0]));
    int n = scan.nextInt();
    int m = scan.nextInt();
    int[][] map = new int[n][m];
    for(int i = 0; i < n; i++)
    {
      for(int j = 0; j < m; j++)
      {
        map[i][j] = scan.nextInt();
      }
    }
    boolean[][] visited = new boolean[n][m];
    System.out.println(DFS(map, visited, 0, 0, 0));
  }

  public static int DFS(int[][] map, boolean[][] visited, int x, int y, int count)
  {
    StringBuilder tabs = new StringBuilder();
    for(int i = 0; i < count; i++)
      tabs.append("  ");
    tabs.append("(").append(x).append(",").append(y).append(")->");
    if(x < 0 || y < 0 || x >= map.length || y >= map[0].length || visited[x][y])
    {
        System.out.print(tabs.append("Deadend\n").toString());
        return (int)1e6;
    }
    if(x == map.length-1 && y == map[0].length-1)
    {
      System.out.print(tabs.append("Done\n").toString());
      return count;
    }

    visited[x][y] = true;
    int jump = map[x][y];
    System.out.print(tabs.toString()+"\n");
    int a = DFS(map, visited, x-jump, y, count+1);
    int b = DFS(map, visited, x+jump, y, count+1);
    int c = DFS(map, visited, x, y-jump, count+1);
    int d = DFS(map, visited, x, y+jump, count+1);

    return Math.min(Math.min(a, b) , Math.min(c, d));
  }
}
