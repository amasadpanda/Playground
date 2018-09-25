import java.util.TreeSet;

public class p4 {
    public static void main(String args[])
    {
        TreeSet<Integer> t12 = new TreeSet<>();
        for(int j = 999; j > 100; j--)
            for(int i = 999; i > 100; i--)
            {
                StringBuilder a = new StringBuilder(""+(j*i));
                StringBuilder b = new StringBuilder(""+(j*i)).reverse();
                if(b.toString().equals(a.toString()))
                {
                    System.out.println(i );
                    t12.add(Integer.parseInt(a.toString()));
                    break;
                }
            }
        System.out.println(t12.last());
    }
}
