import java.util.*;
import java.io.*;

public class TopoPath
{
  int vertices;
  int[][] adjList;
  int[] incomingEdgeCount;

  public TopoPath(String fileName)
  {
    try
    {
      Scanner scan = new Scanner(new File(fileName));
      vertices = scan.nextInt();
      adjList = new int[vertices][vertices];
      incomingEdgeCount = new int[vertices];

      for(int i = 0; i < vertices; i++)
      {
        int nums = scan.nextInt();
        for(int j = 0; j < nums; j++)
        {
          int next = scan.nextInt()-1;
          adjList[next][i] = 1;
          incomingEdgeCount[next]++;
        }
      }
    }
    catch(Exception ex)
    {
      System.err.println("File not found: " + fileName);
    }
  }

  public static boolean hasTopoPath(String fileName)
  {
    TopoPath tp = new TopoPath(fileName);

    // if there are two or more possible starts, then it can't satisfy the path property
    // if there are no possible starts, then it can't satisfy the topological sort property
    Integer start = null;
    for(int i : tp.incomingEdgeCount)
    {
      System.out.println(i);
      if(i == 0)
      {
        if(start != null)
          return false;
        start = i;
      }
    }

    return DFS(start, tp, new boolean[tp.vertices], 0);
  }

  private static boolean DFS(int start, TopoPath tp, boolean[] visited, int count)
  {
    visited[start] = true;
    if(count == tp.vertices-1)
      return true;

    ArrayList<Integer> canGoTo = new ArrayList<>();
    for(int i : tp.adjList[start])
    {
      if(i != 0)
      {
        canGoTo.add(i);
        tp.incomingEdgeCount[i]--;
      }
    }

    Integer next = null;
    for(Integer i : canGoTo)
    {
      if(tp.incomingEdgeCount[i] == 0 && !visited[i])
      {
        if(next != null)
          return false;
        next = i;
      }
    }
    System.out.println(start);
    if(next == null)
      return false;
    else
      return DFS(next, tp, visited, count+1);
  }
}
