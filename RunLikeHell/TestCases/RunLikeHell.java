/*
  Timothy Yu
  ti628070
  CS2 - RunLikeHell
  Sean Szumlanski
   _________________
  | @             @ |
  |     /¯¯¯¯¯`\    |
  |    | ||¯¯| ||   |
  |     ¯   _|_||   |
  |        / _/     |
  |       |_|       |
  |        _        |
  | @     [_]|    @ |
  |_________________|

*/

public class RunLikeHell
{
  public static int maxGain(int[] blocks)
  {
    // +2 due to the out of bounds error, or like coding in the base cases for the recursive algorithm
    int[] dp = new int[blocks.length+2];

    // This for loop starts at the back, then works its way back to the front.
    // It uses the calculations from the recursive function
    for(int i = blocks.length-1; i >= 0; i--)
      dp[i] = Math.max(blocks[i] + dp[i+2], dp[i+1]);


    return dp[0];
  }

  // recursive function that looks at the k-th block and finds the max
  public static int maxGainRec(int[] blocks, int k)
  {
    if(k >= blocks.length)
      return 0;

    //Take current block, skip the next block and the next one
    //Skip this block, decide on next block
    return Math.max(
        blocks[k] + maxGainRec(blocks, k+2),
        maxGainRec(blocks, k+1)
        );
  }

  public static double difficultyRating()
  {
    return 2;
  }

  public static double hoursSpent()
  {
    return 2;
  }
}
