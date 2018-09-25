/*
Timothy Yu - ti628070
Assignment 6, TopoPath
CS2, Dr. Szumlanski
*/

import java.util.*;
import java.io.*;

public class TopoPath
{
  int vertices;
  int[][] adjList;
  // This indicates the indegree of a node numbered -1
  int[] incomingEdgeCount;

  // Although the specifications do not call for creation of an TopoPath object, since the code is similar to ConstrainedTopoSort, we can re-use it.
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
          adjList[i][next] = 1;
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

    // If there are two or more possible starts, then it can't satisfy the path property
    // If there are no possible starts, then it can't satisfy the topological sort property
    Integer start = null;
    for(int i : tp.incomingEdgeCount)
    {
      if(i == 0)
      {
        if(start != null)
          return false;
        start = i;
      }
    }

    return DFS(start, tp, new boolean[tp.vertices], 0);
  }

  // We are using DFS to generate a possible path. If at anytime there is an impossible choice, that means a topological sorting or path cannot be possible in the current path.
  private static boolean DFS(int start, TopoPath tp, boolean[] visited, int count)
  {
    visited[start] = true;

    Integer next = null;
    int which = 0;

    // Determining which node to go to next. If there are more than one possible choice, check if more than one have indegree dependence of 0. If more than one does, it cannot be a path, since only one of the vertices will be visited.
    for(int i : tp.adjList[start])
    {
      if(i != 0)
      {
        if(--tp.incomingEdgeCount[which] == 0 && !visited[which])
        {
          if(next != null)
            return false;
          next = which;
        }
      }
      which++;
    }

    // If this is the last vertex to visit, a topological sort is possible as well as a path.
    if(count == tp.vertices-1)
      return true;

    if(next == null)
      return false;
    else
      return DFS(next, tp, visited, count+1);
  }

  public static double difficultyRating()
  {
    return 2;
  }

  public static double hoursSpent()
  {
    return 3;
  }
}
