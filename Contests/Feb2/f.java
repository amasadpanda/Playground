import java.util.Scanner;

public class f
{
  public static void main(String[] args)
  {
    Scanner scan = new Scanner(System.in);

    StringBuilder a = new StringBuilder(scan.nextLine());
    StringBuilder b = new StringBuilder(scan.nextLine());
    for(int i = 0 ; i < 100000; i++)
    {
      a.append('a');
    }
    for(int i = 0 ; i < 100000; i++)
    {
      b.append('b');
    }
    //int[][] ways = new int[a.length()][b.length()];
    for(int i = 1; i < b.length(); i++)
    {
      for(int j = i; j < b.length(); j++)
      {
        StringBuilder reverse = new StringBuilder(a.subSequence(i, j));
        reverse.reverse();
      }
    }
  }
}
