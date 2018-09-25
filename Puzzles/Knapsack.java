public class Knapsack
{

  public static void main(String[] args)
  {
    int n = 10;
    int capacity = 50;

    if(args.length > 0)
      n = Integer.parseInt(args[0]);

    if(args.length > 1)
      capacity = Integer.parseInt(args[1]);

    int[] weights = new int[n];
    int[] values = new int[n];

    System.out.println("\tWeights\tValues");

    for(int i = 0; i < n; i++)
    {
      weights[i] = (int)(Math.random()*100)+1;
      values[i] = (int)(Math.random()*101);
      System.out.println((i+1)+":\t" + weights[i] + "\t" + values[i]);
    }


    System.out.println("Max values: " + func(weights, values, capacity));
  }

  public static int func(int[] weights, int[] values, int capacity)
  {
    return rec(weights, values, 0, capacity);
  }

  private static int rec(int[] weights, int[] values, int k, int capacity)
  {
    if(capacity <= 0)
      return 0-values[k-1];

    if(k >= weights.length)
      return 0;

    return Math.max(rec(weights, values, k+1, capacity), values[k] + rec(weights, values, k+1, capacity-weights[k]));
  }

  private static int dp(int[] weights, int[] values, int capacity)
  {
    int[] dp = new int[weights.length][capacity+1];

    for(int j = 0; j < weights.length; j++)
    {
      for(int i = 0; i < capacity+1; i++)
      {
        dp[j][i] = dp[j+1][i], values[j] + dp[j+1][capacity-weights[k]];
      }
    }



  }
}
