
public class PI
{

  public static void main(String[] args)
  {
    if(args.length < 1)
    {
      System.out.println("Correct syntax: PI <number of approximation runs>");
    }
    int totalRuns = Integer.parseInt(args[0]);
    int correct = 0;
    for(int i = 0; i < totalRuns; i++)
    {
      double x = Math.random();
      double y = Math.random();
      if(x*x + y*y < 1)
        correct++;
    }
    long d = (totalRuns + 100000000000L);
    long g = (correct + 78539816339L);
    System.out.println(((double)g*4)/d);
  }
}
