/*
Timothy Yu
ti628070
COP 3503
Sean Szumlanski

░░░░░░░░██████████████████
░░░░████░░░░░░░░░░░░░░░░░░████
░░██░░░░░░░░░░░░░░░░░░░░░░░░░░██
░░██░░░░░░░░░░░░░░░░░░░░░░░░░░██
██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██
██░░░░░░░░░░░░░░░░░░░░██████░░░░██
██░░░░░░░░░░░░░░░░░░░░██████░░░░██
██░░░░██████░░░░██░░░░██████░░░░██
░░██░░░░░░░░░░██████░░░░░░░░░░██
████░░██░░░░░░░░░░░░░░░░░░██░░████
██░░░░██████████████████████░░░░██
██░░░░░░██░░██░░██░░██░░██░░░░░░██
░░████░░░░██████████████░░░░████
░░░░░░████░░░░░░░░░░░░░░████
░░░░░░░░░░██████████████

*/

import java.util.*;


//  Height is equal to the number of nodes (so indexing shoudl be [0, height)

class Node<Thing extends Comparable<Thing>>
{
  private ArrayList<Node<Thing>> nextNodes;
  private Thing data;

  public Node(int height)
  {
    nextNodes = new ArrayList<>();

    for(int i = 0; i < height; i++)
      nextNodes.add(null);
  }

  public Node(Thing data, int height)
  {
    this(height);
    this.data = data;
  }

  public Thing value()
  {
    return data;
  }

  public int height()
  {
    return nextNodes.size();
  }

  public Node<Thing> next(int level)
  {
    if(!inBounds(level))
      return null;

    return nextNodes.get(level);
  }

  private boolean inBounds(int level)
  {
    return (level >= 0 && level < height());
  }

  public void setNext(int level, Node<Thing> next)
  {
    if(inBounds(level))
      nextNodes.set(level, next);
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
    nextNodes.add(null);
  }

  public boolean maybeGrow()
  {
    int toBe = (int)(Math.random()*2+1);
    if(toBe == 1)
      grow();

    return (toBe == 1);
  }

  public void trim(int level)
  {
    for(int i = height()-1; i > level; i--)
        nextNodes.remove(i);
  }

  @Override
  public String toString()
  {
    return value().toString();
  }
}

public class SkipList<Thing extends Comparable<Thing>>
{
  private Node<Thing> head;
  private int size;

  public SkipList()
  {
    this(1);
  }

  public SkipList(int height)
  {
    // if the height is less than the default, use default instead
    if(height < 1)
      height = 1;
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
    if(size <= 2)
      return 1;
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
    while(coinflips < maxHeight && ((int)(Math.random()*2+1)) == 1)
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
              compare = compare.next(i);

          if(i < height)
            compare.insert(i, addMe);

          current = compare;
        }
        else
        {
          // The next value we got is too big, if the node is tall enough to reach this level, we need to plow through some pointers.
          if(i < height)
            current.insert(i, addMe);
        }
      }
      else
      {
          // If the next node is null and the level is less than or equal to insert node's height, we should insert
          //System.out.println((i < height)+" " + i + " "+ height);
          if(i < height)
            current.insert(i, addMe);
      }
    }

    // Checks the expected height vs. the current height
    // A possible "bug" *cough* feature would be automatically resizing the SkipList down below user-defined levels, which probably should not have happened. (This behavior was existant in the test cases, however, so I left this "feature" in).
    size++;
    if(expectedMaxHeight() > height())
      skyward(height()-1);

  }

  // The list is now to big! Skyward expansion for the good of the empire!
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

  // Copy-pasted from the search() method since I was not able to implement Command patterns.
  // Everything worked as expected until TestCase14, where the left-most node should be deleted.
  // So I hacked it a little to only delete once the node is found on level 0 (which should be the also be the left-most).
  private Node<Thing> recursiveDelete(int level, Node<Thing> current, Thing target, Node<Thing> found)
  {
    if(level < 0 || current == null)
      return found;

    if(current.next(level) == null)
    {
       return recursiveDelete(level-1, current, target, found);
    }
    else
    {
      Node<Thing> temp = current.next(level);
      int comp = temp.value().compareTo(target);
      if(comp == 0)
      {
        if(level == 0)
        {
          // TEST CASE 14 CONQUERED
          current.setNext(level, current.next(level).next(level));
          return temp;
        }
         temp = recursiveDelete(level - 1, current, target, current);
         // TEST CASE 14 CONQUERED
         if(temp != null && temp == current.next(level))
           current.setNext(level, temp.next(level));
      }
      else if(comp < 0)
      {
        temp = recursiveDelete(level, current.next(level), target, found);
        // TEST CASE 14 CONQUERED
        if(temp != null && temp == current.next(level))
          current.setNext(level, temp.next(level));
      }
      else
      {
        temp = recursiveDelete(level-1, current, target, found);
        if(temp != null && temp == current.next(level))
          current.setNext(level, temp.next(level));
      }
      return temp;
    }
  }

  public void delete(Thing data)
  {
    if(recursiveDelete(height()-1, head, data, null) != null)
    {
      // Recursive delete should return the node to be deleted
      // If it doesn't, the node was not found and nothing happens.
      size--;

      // If the change in size causes the maximum allowed height to drop, some references need to be culled
      if(expectedMaxHeight() < height())
        homeward(expectedMaxHeight() - 1, head);
    }
    //lmao it works
  }

  // Recursively DESTROY references
  public void homeward(int level, Node<Thing> current)
  {
    if(current.next(level) != null)
      homeward(level, current.next(level));

    current.trim(level);
  }

  // Recursive method to search through the list.
  // This was planned to be of a Command pattern, but Generics and FunctionalInterfaces are tough to get working nicely together.
  // I also wanted to recursively insert nodes, but it seemed iteratively was a better approach (too much stuff going on in the insert method).
  private Node<Thing> search(int level, Node<Thing> current, Thing target)
  {
    if(level < 0 || current == null)
      return null;

    if(current.next(level) == null)
    {
       return search(level-1, current, target);
    }
    else
    {
      int comp = current.next(level).value().compareTo(target);
      if(comp == 0)
      {
        return current;
      }
      else if(comp < 0)
      {
        return search(level, current.next(level), target);
      }
      else
      {
        return search(level-1, current, target);
      }
    }
  }

  // Calls a recursive function that gets the nodes
  // Originally I planned for this to use Command Patterns
  public Node<Thing> get(Thing data)
  {
    return search(height()-1, head(), data);
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
    return 2.9;
  }

  public static double hoursSpent()
  {
    return 10.0;
  }
}
