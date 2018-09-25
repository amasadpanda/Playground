import java.util.ArrayList;


class QuadNode <Thing, PType extends Comparable<PType>>
{
    // Quadtree quadrants are numbered as follows:
    // 0: North West
    // 1: North East
    // 2: South West
    // 3: South East

    Thing value;
    PType center; //Point of the leaf or center of a quadrant

    ArrayList<QuadNode<Thing, PType>> children;

    public QuadNode()
    {
        children = new ArrayList<>(4);
    }

    /*
    isLeaf can work in two different ways:
        1. All its children are null
        2. Its value is null
         ^ Does not work if we want each node to store the average of its children, so 1. is implemented
     */
    public boolean isLeaf()
    {
        for(QuadNode<Thing, PType> node : children)
        {
            if(node != null)
                return false;
        }
        return true;
    }

    public int getQuadrant(PType p)
    {
        /*
        //What to do to points that l
        int xCompare = p.;
        int yCompare = p.y.compareTo(center.y);
        // Quadrant 0 or 2
        if(xCompare < 0)
        {
            //if()
        }
        // Quadrant 1 or 3
        else
        {

        }
        */
        return 1;
    }

    public void addChild()
    {
        //children
    }
}

public class QuadTree<Thing> {

    QuadNode<Thing, Double> root;

    public QuadTree()
    {
        root = new QuadNode<>();

    }

    /*
        Is it better to use integer co-ordinates?
     */
    public void add(double x, double y, Thing value)
    {
        add(root, x, y, value);
    }

    private void add(QuadNode<Thing, Double> qn, double x, double y, Thing value)
    {
        if(qn.isLeaf())
        {

        }
        else
        {

        }
    }




    public static void main(String[] args)
    {
      //  QuadNode<Integer> qu = new QuadNode<>();
    }
}
