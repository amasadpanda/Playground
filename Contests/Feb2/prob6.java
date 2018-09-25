import java.util.Scanner;
import java.util.Arrays;

public class prob6
{
  public static void main(String args[])
  {
    Scanner scan = new Scanner(System.in);

    int nums = scan.nextInt();
    int[] ar = new int[nums];
    int total = 0;

    for(int i = 0; i < nums; i++)
    {
      ar[i] = scan.nextInt();
      total += ar[i];
    }

    int avg = total/nums;

    for(int i = 1; i < nums-1; i++)
    {
      ar[i] -= avg;
    }

    int max_sofar = 0;
    int max_here = 0;
    int start = 0;
    int end = 0;

    for(int i = 1; i < nums-1; i++)
    {
      max_here += ar[i];
      if(max_here < 0)
      {
        max_here = 0;
        start = i+1;
        end = i;
      }
      if(max_sofar <= max_here)
      {
        max_sofar = max_here;
        end = i;
      }
    }
    //System.out.println(max_sofar);
    for(int i = start; i <= end; i++)
    {
      total -= (ar[i]+avg);
    }
    //System.out.println(start + " " + end + " " + total);
    System.out.println(total/(double)(nums-(end-start+1)));
/*

double min = Double.MAX_VALUE;
    for(int i = 1; i < nums-1; i++)
    {
      double t = total;
      for(int j = i; j < nums-1; j++)
      {
        t -= ar[j];
        if(t/(nums-(j+1-i)) < min)
        {
            min = t/(nums-(j+1-i));
        }
      }
    }
*/
  }
}
