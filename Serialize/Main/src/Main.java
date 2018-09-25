import java.io.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@FunctionalInterface
interface Function1
{
    public abstract void done();
}

@FunctionalInterface
interface Function2
{
    public abstract void done();
}

@FunctionalInterface
interface Function3
{
    public abstract void done();
}

abstract class SetOFunctions implements Serializable
{
    Function1 f1;
    Function2 f2;
    Function3 f3;

    public SetOFunctions(){

    }

    public abstract void dot();
}

class CustomFunction extends SetOFunctions
{

    public CustomFunction() {


        f1 = (Function1 & Serializable) () -> System.out.println("Function 1 is serialized!");
        f2 = (Function2 & Serializable) () -> System.out.println("Function 2 is serialized!");
        f3 = (Function3 & Serializable) () -> System.out.println("Function 3 is serialized!");

    }

    public void dot()
    {
        f1.done();
        f2.done();
        f3.done();
    }
}


public class Main {

    private static void S(Object b)
    {
        System.out.println(b);
    }

    public static void main(String args[])
    {
        SetOFunctions sof = new CustomFunction();
        try{
            FileOutputStream f = new FileOutputStream("testinghax");
            ObjectOutputStream out = new ObjectOutputStream(f);

            out.writeObject(sof);
            out.close();
            f.close();

            FileInputStream ff = new FileInputStream("testinghax");
            ObjectInputStream in = new ObjectInputStream(ff);
            Object b = in.readObject();
            if(b instanceof  SetOFunctions)
                ((SetOFunctions) b).dot();
            in.close();
            ff.close();
        }
        catch( Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("wtf");

        /*
        Function<String, Integer> f = (String b) -> 1;
        Consumer r = (Consumer & Serializable)(n) -> {System.out.println("Sample function"); S(n);};

        r.accept("HELLO WORLD");

        try{
            FileOutputStream f = new FileOutputStream("runnable_object");
            ObjectOutputStream out = new ObjectOutputStream(f);

            out.writeObject(r);
            out.close();
            f.close();

            FileInputStream ff = new FileInputStream("runnable_object");
            ObjectInputStream in = new ObjectInputStream(ff);
            Object b = in.readObject();
            if(b instanceof  Consumer)
                ((Consumer) b).accept("WORK?");
            in.close();
            ff.close();
        }
        catch( Exception e)
        {

        }
        */
    }
}
