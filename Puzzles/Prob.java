public class Prob
{
  public static void main(String args[])
  {
    int trials = Integer.parseInt(args[0]);
    int[] box = new int[2];
    for(int i = 0 ; i < trials; i++)
      box[(int)Math.random()*2]++;
    System.out.println((box[0]) + " " + (box[1]/(double)trials));
  }
}
