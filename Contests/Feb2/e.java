import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class e
{
  static boolean[][] matrix;
  static boolean[] isHard;
  static int[][] map;
    public static void add(int node, int row, int col)
    {
      if(row > 0 && row < map.length && col > 0 && col < map.length)
        if(map[row][col] != -1)
        matrix[node][map[row][col]] = true;
    }

    public static void main(String[] args)
    {
      Scanner scan = new Scanner(System.in);
      int rows = scan.nextInt();
      int maxN = scan.nextInt();
      int a = scan.nextInt();
      int b = scan.nextInt();
      int x = scan.nextInt();
      int rm1 = rows-1;
      int nodes = (rows*rows*rows)-(rm1*rm1*rm1);
      isHard = new boolean[nodes+1];

      StringBuilder s = new StringBuilder(rows-1);
      for(int i = 0; i < (rows-1)*2; i++)
      {
        s.append(i);
      }
      s.append(new StringBuilder(s).reverse());
      matrix = new boolean[nodes+1][nodes+1];
      int row = 0;
      int count = 0;
      map = new int[rows*2-1][rows*2-1];
      int count1 = 1;
      for(int i = 0; i < map.length; i++)
      {
        int offset = Math.max(0, map.length-(rows+i)+1);
        int offset2 = Math.min(map.length, map.length-i+rows+1);
        for(int h = 0; h < offset; h++)
          map[i][h] = -1;
        for(int h = offset2; h < map.length; h++)
          map[i][h] = -1;
        for(int j = offset; j < offset2; j++)
        {
          map[i][j] = count1;
        }
      }
      
      for(int i = 1; i < nodes+1; i++)
      {
        if(!(map[i][j] <= 0))
        {
          add(map[i][j], i-1, j);
          add(map[i][j], i-1, j+1);
          add(map[i][j], i, j-1);
          add(map[i][j], i, j+1);
          add(map[i][j], i+1, j);
          add(map[i][j], i+1, j-1);
        }
      }
      for(int i = 0; i < x; i++)
      {
        isHard[scan.nextInt()] = true;
      }
      int result = bfs(a, b);

      for(int i = 1; i < nodes+1; i++)
      {
        System.out.print(i+": ");
        for(int j = 1; j < nodes+1; j++)
        {
          if(matrix[i][j])
          System.out.print(j+" ");
        }
        System.out.println();
      }

    //if(result > maxN)
    //    System.out.println("No");
    //  else
        System.out.println(result);
    }
    public static int bfs(int node, int end)
    {
      Queue<Integer> fi = new LinkedList<>();
      boolean[] visited = new boolean[matrix.length];
      int count = 0;
      fi.add(node);

      while(!fi.isEmpty())
      {
        int ex = fi.remove();
        System.out.println(ex);
        if(ex == end)
          break;
        for(int i = 1; i < matrix.length; i++)
        {
          if(!visited[i] && matrix[ex][i] && !isHard[i])
          {
            visited[i] = true;
            fi.add(i);
          }
        }
        count++;
      }

      return count;
    }
}
