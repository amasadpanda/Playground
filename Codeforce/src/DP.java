import java.util.*;

public class DP
{

  static int[][] memo_table;

  public static void main(String[] args)
  {
    Scanner scan = new Scanner(System.in);

    int capacity = scan.nextInt();
    int items = scan.nextInt();
    int[] values = new int[items];
    int[] weights = new int[items];

    for(int i = 0; i < items; i++)
    {
      values[i] = scan.nextInt();
      weights[i] = scan.nextInt();
    }

    memo_table = new int[capacity+1][items];
    System.out.println(memo(capacity, values, weights, 0));
  }

  //recursive
  public static int maxValue(int capacity, int[] values, int[] weights, int place)
  {
    if(place == values.length || capacity <= 0)
      return 0;
    return Math.max(
      ((capacity-weights[place] >= 0)?values[place]+maxValue(capacity-weights[place], values, weights, place):0),
      maxValue(capacity, values, weights, place+1)
    );
  }

  //memoized
  public static int memo(int capacity, int[] values, int[] weights, int place)
  {
    if(place == values.length || capacity <= 0)
      return 0;

    if(memo_table[capacity][place] == 0)
    {
      int j = Math.max(
        ((capacity-weights[place] >= 0)?values[place]+memo(capacity-weights[place], values, weights, place):0),
        memo(capacity, values, weights, place+1)
      );
      memo_table[capacity][place] = j;
      return j;
    }
    else
    {
      return memo_table[capacity][place];
    }
  }
}
