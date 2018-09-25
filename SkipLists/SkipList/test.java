import java.util.*;
public class test
{
  public static void main(String[] args)
  {
    int count = 0;
    for(int i = 0; i<1000; i++)
    {
      if((int)(Math.random()*2+1) == 1)
        count++;
    }
    System.out.println(count);
  }
}
