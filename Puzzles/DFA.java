import java.util.Scanner;

public class DFA{

  public static void main(String[] args)
  {
    Scanner scan = new Scanner(System.in);
    String w = scan.nextLine();
    System.out.println(state1(w, 0));
  }

  public static boolean state1(String s, int l){
    if(l == s.length())
    {
      return false;
    }
    else
    {
      if(s.charAt(l) == 'a')
      {
        return state1(s, l+1);
      }
      else
      {
        return state2(s, l+1);
      }
    }
  }

  public static boolean state2(String s, int l)
  {
    if(l == s.length())
    {
      return false;
    }
    else
    {
      if(s.charAt(l) == 'a')
      {
        return state4(s, l+1);
      }
      else
      {
        return state3(s, l+1);
      }
    }
  }

  public static boolean state3(String s, int l)
  {
    if(l == s.length())
    {
      return true;
    }
    else
    {
      if(s.charAt(l) == 'a')
      {
        return state4(s, l+1);
      }
      else
      {
        return state3(s, l+1);
      }
    }
  }

  public static boolean state4(String s, int l)
  {
    if(l == s.length())
    {
      return true;
    }
    else
    {
      if(s.charAt(l) == 'a')
      {
        return state1(s, l+1);
      }
      else
      {
        return state2(s, l+1);
      }
    }
  }
}
