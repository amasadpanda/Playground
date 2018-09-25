import java.util.ArrayList;
import java.util.function.Function;


/**
 * A representation of a vector as a column matrix.
 * <p>
 *     The column matrix holds up to n items, which can be thought as a single point in dimension n.
 * </p>
 *
 * @param <Thing> snapTwoPieces is arbitrary and implementation is up to the user! It is strongly recommended to return the
 *               representation of 'half' of the elements of the vector.
 */

// This is probably the worst way to do this, sphaghetti, should I just make it abstract????
abstract class Vector <Thing extends Comparable<Thing>>
{
    /*
    @Deprecated
    public Vector<Thing> getHalf() throws Exception
    {
        int length = this.column.size();
        if(length > 0)
        {
            if(column.get(0) instanceof Number)
            {
                Vector<Double> halfd = new Vector<>(length);
                for(int i = 0; i < column.size(); i++)
                {
                    Number num = (Number)column.get(i);
                    halfd.add(num.doubleValue());
                }
                // This should be fine, as Double is bigger than other numeric types
                return (Vector<Thing>)halfd;
            }
            else
            {
                throw new Exception("Not a number: Override Vector.getHalf() for non-numeric types.");
            }
        }
        // An empty vector in half is the same vector
        return this;
    }
    */

    /**
     * Note this function is computationally expensive as it is called everytime. Suggest that the child class create and store it.
     *
     * @return Returns a functiont that takes the vector and produces the result when it is 'halved'.
     */
    abstract Function<Vector<Thing>, Vector<Thing>> snapTwoPieces();
    abstract Vector<Thing> subtract(Vector<Thing> t);

    public Vector<Thing> mitosis()
    {
        return snapTwoPieces().apply(this);
    }

    private ArrayList<Thing> column;
    private int capacity;

    public Vector(Thing...t)
    {
        int size = t.length;
        column = new ArrayList<>(size);
        capacity = size;

        for(int i = 0; i < size; i++)
        {
            column.add(t[i]);
        }
    }

    public Vector(ArrayList<Thing> t)
    {
        int size = t.size();
        column = t;
        capacity = size;
    }

    private Vector(int capa)
    {
        column = new ArrayList<>(capa);
        capacity = capa;
    }

    // Returns success
    // The number of elements in t must be equal to the size of the vector
    private boolean set(Thing ... t)
    {
        if(t.length != capacity)
            return false;


        for(int i = 0; i < capacity; i++)
        {
            // wtf? array of a generic type?
            column.set(i, t[i]);
        }

        return true;
    }

    // Returns success
    private boolean add(Thing t)
    {
        if(column.size() < capacity)
            return false;

        column.add(t);

        return true;
    }


    public int size()
    {
        return capacity;
    }

    // Is returning an Integer array better?
    // Type is known to be integer, there isn't any reason to use vector results....
    public Integer[] compare(Vector<Thing> v) throws Exception
    {
        if(v.size() != capacity)
            throw new Exception("Vectors of different sizes cannot be compared.");
        Integer[] com = new Integer[capacity];

        for(int i = 0; i < capacity; i++)
        {
            // check for nulls here?
            com[i] = column.get(i).compareTo(v.column.get(i));
        }
        return com;
    }

    public ArrayList<Thing> getValues()
    {
        return column;
    }

    @Override
    public String toString()
    {
        StringBuilder b = new StringBuilder();
        b.append('[');
        for(Thing t : column)
        {
            b.append(t.toString()).append(", ");
        }
        b.deleteCharAt(b.length()-1);
        b.deleteCharAt(b.length()-1);
        b.append(']');

        return b.toString();
    }
}


class PointNode<Value>
{
    // If node is a branch, point marks the center of the rectangle
    Vector point;
    Value value;
    ArrayList<PointNode<Value>> children;

    public PointNode(Value v, Vector p)
    {
        if(p != null)
        {
            point = p;
            value = v;
            int size = p.size();

            // Line split results in two halves
            // Plane split results in four quadrant
            // Cuboid split results in eight pieces
            children = new ArrayList<>(1 << size);
        }
        else
        {
            try {
                throw new Exception("PointNode creation with null vector!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
    [-1] -> left side
    [-1, -1] -> left side, bottom
    [-1, -1, -1] -> left side, bottom, back

    If the element is equal, it will be treated as being a bigger value
     */
    protected int getOrthant(Vector p)
    {
        try {
            Integer[] results = p.compare(point);
            int sliced = 0;
            for(int i = results.length-1; i >= 0; i--)
            {
                System.out.println(i+": " + results[i]);
                if(results[i] < 0) {
                    sliced = sliced | 1;
                }
                    sliced = sliced << 1;
            }

            return sliced >> 1;
        } catch (Exception e) {
            System.err.println("Are PointNode vectors of the same dimension and type?");
            e.printStackTrace();
        }
        return -1;
    }

    /*
    isLeaf can work in two different ways:
        1. All its children are null
        2. Its value is null
         ^ Does not work if we want each node to store the average of its children, so 1. is implemented
     */
    public boolean isLeaf()
    {
        for(PointNode<Value> pn : children)
        {
            if(pn != null)
                return false;
        }
        return true;
    }

    public void add(Value v, Vector p, Vector parentBorder)
    {
        int g = getOrthant(p);
        PointNode<Value> child = children.get(g);

        Vector parentCenter = parentBorder.mitosis();


        if(child == null)
        {
            //good to go, pop in that value
            children.set(g, new PointNode<Value>(v, p));
        }
        else
        {
            // not this point, but the borders of the current node
            //change below
            Vector newChildCenter = parentCenter.mitosis();
            //find orientation
            Vector temp = child.point;
            child.point = newChildCenter;
            child.add(v, temp);
            child.add(this.value, temp);
        }
    }

}

public class OrthantTree<Type>
{
    private PointNode<Type> root;


    public OrthantTree(Vector size, Vector center)
    {
        root = new PointNode(null, size);
    }

    public static void main(String[] args) throws Exception
    {

        //PointNode<String, Integer> root = new PointNode<String, Integer>("this is the root", 0,0,0);
        //Vector<Integer> ty = new Vector<>(-1,-1, 1);

        Vector v = new IntegerVector(2, 2, 2);
        Vector d = new DoubleVector(3.0,3.0,3.0);
        Vector vInHalf = v.mitosis();
        PointNode<String> apoint = new PointNode<>("hello world", v);

        System.out.println(vInHalf.toString());
    }
}