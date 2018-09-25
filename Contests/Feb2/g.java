import java.util.Scanner;
import java.util.HashSet;

public class g
{
  static int nodes;
  static int[] nextforced;
  static int[][] matrix;
  static HashSet<Integer> hashy;
  public static void main(String args[])
  {
      Scanner scan = new Scanner(System.in);
      hashy = new HashSet<>();
      nodes = scan.nextInt();
      int edges = scan.nextInt();
      matrix = new int[nodes+1][nodes+1];
      nextforced = new int[nodes+1];
      for(int i = 0; i < edges; i++)
      {
        int a = scan.nextInt();
        int b = scan.nextInt();
        if(a < 0) // forced
        {
          nextforced[-1*a] = b;
          matrix[-1*a][b] = -1;
        }
        else
          matrix[a][b] = 1;
      }

      int result = bdfs(1, false, new boolean[nodes+1]);
    //  for(int[] a: matrix){ for(int j : a) System.out.print(j + " "); System.out.println();}
    //  for(int b : nextforced) System.out.println(b);
    //  System.out.println(result);
      System.out.println(hashy.size());
  }

  public static int bdfs(int node, boolean isBugged, boolean[] visited)
  {
    int result = 0;

    if(visited[node])
      return 0;

    visited[node] = true;

    if(isBugged)
    {
      if(nextforced[node] == 0)
      {
          result++;
          hashy.add(node);
      }
      else
      {
        result+=bdfs(nextforced[node], isBugged, visited);
        visited[nextforced[node]] = false;
      }
    }
    else
    {
      if(nextforced[node] != 0)
      {
        result += bdfs(nextforced[node], isBugged, visited);
        visited[nextforced[node]] = false;
      }
      else
      {
        hashy.add(node);
        result++;
      }

      for(int i = 1; i < nodes+1; i++)
      {
        if(matrix[node][i] > 0)
        {
          result += bdfs(i, true, visited);
          visited[i] = false;
        }
      }
    }
    return result;
  }
}
