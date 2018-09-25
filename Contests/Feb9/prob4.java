import java.util.Scanner;
import java.util.HashSet;

public class prob4
{
  public static void main(String args[])
  {
    Scanner scan = new Scanner(System.in);
    int nums = scan.nextInt();
    int[] coprimes = new int[nums];
    for(int i = 0; i < nums; i++)
    {
      coprimes[i] = scan.nextInt();
    }
    HashSet<Integer> hashy = new HashSet<>();
    for(int i = 0; i < nums; i++)
    {
      for(int j = i+1; j < nums; j++)
      {
        hashy.add(gcd(coprimes[i], coprimes[j]));
      }
    }


    for(int i : hashy)
    {
      System.out.println(i);
    }

  }

  public static int gcd(int a, int b)
  {
    if(b == 0)
      return a;
    return gcd(b, a%b);
  }

}
