import java.util.*;

/* Notes:
  Height is strictly equal to the number of next nodes.
  A height of 0 indicates that the node is not initialized
  Levels begin at 0 (ground floor)
*/
class Node<Thing extends Comparable<Thing>>
{
  ArrayList<Node<Thing>> nextNodes;
  private int height;
  private Thing data;

  public Node(int height)
  {
    nextNodes = new ArrayList<>(height);

    for(int i = 0; i < height; i++)
      nextNodes.add(null);

    this.height = height;
  }

  public Node(Thing data, int height)
  {
    this(height);
    this.data = data;
  }

  public Thing value()
  {
    return this.data;
  }

  public int height()
  {
    return this.height;
  }

  public Node<Thing> next(int level)
  {
    if(!inBounds(level))
      return null;

    return nextNodes.get(level);
  }

  private boolean inBounds(int level)
  {
    return (level >= 0 && level < height);
  }

  public void setNext(int level, Node<Thing> next)
  {
    if(inBounds(level))
    {
      nextNodes.set(level, next);
    }
  }

  public void insert(int level, Node<Thing> next)
  {
    if(inBounds(level))
    {
      next.setNext(level, nextNodes.get(level));
      setNext(level, next);
    }
  }

  public void grow()
  {
    height++;
    nextNodes.add(null);
  }

  public boolean maybeGrow()
  {
    int toBe = (int)(Math.random()*2+1);
    if(toBe == 1)
      grow();

    return (toBe == 1);
  }

  @Override
  public String toString()
  {
    return data.toString() + ": Heigth - " + height;
  }
}

public class SkipList<Thing extends Comparable<Thing>>
{
  Node<Thing> head;
  private int size;

  public SkipList()
  {
    // Set the height to 1
    this(1);
  }

  public SkipList(int height)
  {
    // user-defined SkipList height
    // if the height is less than the default, use default instead
    if(height < 0)
      height = 0;
    head = new Node<Thing>(height);
    size = 0;
  }

  public int size()
  {
    // return number of nodes in the SkipList (excluding the head node)
    return size;
  }

  public int height()
  {
    // maximum height of the SkipList, which should also be the maximum height of the head node
    return head.height();
  }

  public Node<Thing> head()
  {
    return head;
  }

  public int expectedMaxHeight()
  {
    return (int)Math.ceil(Math.log10(size)/Math.log10(2));
  }

  public boolean contains(Thing data)
  {
    // I'm pretty that every .contains() in existance just returns a check on .get()
    return (get(data) != null);
  }

  public void insert(Thing data)
  {
    int maxHeight = Math.max(height(), expectedMaxHeight());
    int coinflips = 1;
    while(coinflips <= maxHeight && ((int)(Math.random()*2+1)) == 1)
      coinflips++;

    insert(data, coinflips);
  }

  public void insert(Thing data, int height)
  {
    Node<Thing> current = head, addMe = new Node<>(data, height);
    int max = height();

    for(int i = max-1; i >= 0; i-- )
    {
      Node<Thing> compare = current.next(i);
      if(compare != null)
      {
        Thing value = compare.value();
        if(value.compareTo(data) == 0)
        {
          // The node to insert is a duplicate.
          if(i < height)
            current.insert(i, addMe);
        }
        else if(value.compareTo(data) < 0)
        {
          // The value of the next node is too small. That means we can traverse this level until a value that is bigger is found.
          while(compare.next(i) != null && compare.next(i).value().compareTo(data) < 0)
          {
              compare = compare.next(i);
          }
          if(i < height)
            compare.insert(i, addMe);

          current = compare;
        }
        else
        {
          // The next value we got is too big, if the node is tall enough to reach this level, we need to plow through some pointers.
          if(i < height)
          {
            //System.out.println(data+" "+height);
            current.insert(i, addMe);
          }
        }
      }
      else
      {
          // If the next node is null and the level is less than or equal to insert node's height, we should insert
          //System.out.println((i < height)+" " + i + " "+ height);
          if(i < height)
          {
            current.insert(i, addMe);
          }
      }
    }

    size++;
    if(expectedMaxHeight() > height())
    {
      // The list is now to big! Skyward expansion for the good of the empire!
      skyward(height()-1);
    }
    //print();
  }

  private void skyward(int level)
  {
    head.grow();
    Node<Thing> top = head;
    Node<Thing> nextLevel = head;
    while(top.next(level) != null)
    {
      if(top.next(level).maybeGrow())
      {
        nextLevel.setNext(level+1, top.next(level));
        nextLevel = nextLevel.next(level+1);
      }
      top = top.next(level);
    }
  }

  private void recursiveDelete(int level, Node<Thing> current, Thing target)
  {
    if(level < 0 || current == null)
      return;

    if(current.next(level) == null)
    {
       recursiveDelete(level-1, current, target);
    }
    else
    {
      int comp = current.next(level).value().compareTo(target);
      if(comp == 0)
      {
        current.setNext(level, current.next(level).next(level));
        recursiveDelete(level - 1, current, target);
      }
      else if(comp < 0)
      {
        recursiveDelete(level, current.next(level), target);
      }
      else
      {
        recursiveDelete(level-1, current, target);
      }
    }
  }

  public void delete(Thing data)
  {
    // if this causes the maximum allowed height to fall below the actual max height, trim all nodes with a maximum height
  }

  /*
    A recursive algorithm that both delete() and get() methods can call.
    I tried extending this functionality to insert(), but it just needs to do to much to be able to be packed into this as well.
  */
  private Node<Thing> search(int level, Node<Thing> current, Thing target, Command com)
  {
    if(level < 0 || current == null)
      return null;

    if(current.next(level) == null)
    {
      return com.execute(level-1, current, target);
      //return search(level-1, current, target);
    }
    else
    {
      int comp = current.next(level).value().compareTo(target);
      if(comp == 0)
      {
        return com.execute(level, current.next(level), target);
        //return current;
      }
      else if(comp < 0)
      {
        return com.execute(level, current.next(level), target);
        //return search(level, current.next(level), target);
      }
      else
      {
        return com.execute(level-1, current, target);
        //return search(level-1, current, target);
      }
    }
  }

  public Node<Thing> get(Thing data)
  {
    Command com = ((level, current, target)->
    {
      if(target instanceof Thing)
        target = (Thing)target;
      int i = current.value().compareTo(target);
      if(i == 0)
        return current;
    });
    return search(height()-1, head(), data, com);
  }

  public void print()
	{
		for(int level = 0; level < height(); level++)
		{
			Node<Thing> current = head;
			while(current != null)
			{
				System.out.print(current.value()+" ");
				current = current.next(level);
			}
			System.out.println("");
		}
	}

  public static double difficultyRating()
  {
    return 2.5;
  }

  public static double hoursSpent()
  {
    return 6.0;
  }
}

@FunctionalInteface
interface Command<Thing extends Comparable<Thing>>
{
  public Node<Thing> execute(int level, Node<Thing> current, Thing target);
}
