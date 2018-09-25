import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

abstract class superclass
{
    static Integer field = 0;
}

class subclass extends superclass
{
    public subclass()
    {
        field = 1;
    }
}


public class prob  {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //System.out.println(  (Number)new Integer(4).doubleValue());


        System.out.println(new subclass().field);
        System.out.println(new superclass() {
        }.field);
        int[][] adjMatrix = new int[8][8];
        ArrayList<Integer> ar = new ArrayList<>();
        ar.add(1);
        ar.add(2);
        ar.add(4);

        

       // System.out.println(ar.get(0));

    }

}
