import java.util.*;
import java.io.*;

public class ConstrainedTopoSort
{

  int vertices;
  int[][] adjList;

  public ConstrainedTopoSort(String fileName)
  {
    try
    {
      Scanner scan = new Scanner(new File(fileName));

      vertices = scan.nextInt();
      adjList = new int[vertices][];

      for(int i = 0; i < vertices; i++)
      {
        int nums = scan.nextInt();
        adjList[i] = new int[nums];
        for(int j = 0; j < nums; j++)
        {
          adjList[i][j] = scan.nextInt();
        }
      }
    }
    catch (Exception ex)
    {
      System.err.println("Cannot find file: " + fileName);
    }
  }

  /*
  @params x, y Is there a topological sort in which x comes before y?
  */
  public boolean hasConstrainedTopoSort(int x, int y)
  {
    if(x == y)
      return false;

    boolean[] visited = new boolean[vertices];
    boolean[] recursiveStack = new boolean[vertices];
    visited[y-1] = true;

    return DFS(y, x, visited, recursiveStack);
  }

  /* Because valid topological sorts should not have any cycles, if any cycle is detected, there should not be a valid toposort.
  */
  private boolean DFS(int y, int doNotFind, boolean[] visited, boolean[] stack)
  {
    boolean results = true;

    for(Integer a : adjList[y-1])
    {
      if(a == doNotFind)
        return false;

      if(!visited[a-1])
      {
        visited[a-1] = true;
        stack[y-1] = true;
        results &= DFS(a, doNotFind, visited, stack);
        stack[a-1] = false;
      }
      else if(stack[a-1])
      {
        //Cycle detected!
        return false;
      }
    }

    return results;
  }

  public static void main(String[] args) {
    ConstrainedTopoSort t = new ConstrainedTopoSort("g1");
    System.out.println(t.hasConstrainedTopoSort(1,3));
  }

  public static double difficultyRating()
  {
    return 1.0;
  }

  public static double hoursSpent()
  {
    return 1.4;
  }
}
