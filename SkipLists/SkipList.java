/*

*/
import java.util.ArrayList;

class Node<Thing extends Comparable>
{
  private ArrayList<Thing> next;
  private Thing data;

  public Node(int height)
  {
    next = new ArrayList<>(height);
  }

  public Node(Thing value, int height)
  {
    data = value;
    next = new ArrayList<>(height);
  }

  public Thing value()
  {
    return data;
  }

  public int height()
  {
    return ArrayList.length;
  }
}

public class SkipList<Thing extends Comparable>
{

}
