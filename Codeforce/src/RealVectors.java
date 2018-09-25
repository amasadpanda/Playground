import java.util.ArrayList;
import java.util.function.Function;

public class RealVectors {

}
class NumericVector<T> extends Vector<Double>
{

    @Override
    Function<Vector<Double>, Vector<Double>> snapTwoPieces() {
        return null;
    }

    @Override
    Vector<Double> subtract(Vector<Double> t) {
        return null;
    }
}


class DoubleVector extends Vector<Double> {

    private Function<Vector<Double>, Vector<Double>> fx;

    public DoubleVector(ArrayList<Double> ar) {
        super(ar);
    }

    public DoubleVector(Double... t) {
        super(t);
    }

    @Override
    public Function<Vector<Double>, Vector<Double>> snapTwoPieces() {
        if (fx == null)
            return fx = (inVector) -> {
                ArrayList<Double> ar = new ArrayList<>();
                inVector.getValues().forEach(i -> ar.add(i / 2));
                return new DoubleVector(ar);
            };
        else
            return fx;
    }

    @Override
    Vector<Double> subtract(Vector<Double> t) {
        ArrayList<Double> ar = new ArrayList<>();
        int counter = 0;
        for(Double d : this.getValues())
        {
            ar.add(d - t.getValues().get(counter++));
        }
        return new DoubleVector(ar);
    }
}

class IntegerVector extends Vector<Integer> {
    private Function<Vector<Integer>, Vector<Integer>> fx;

    public IntegerVector(ArrayList<Integer> ar) {
        super(ar);
    }

    public IntegerVector(Integer... t) {
        super(t);
    }

    @Override
    public Function<Vector<Integer>, Vector<Integer>> snapTwoPieces() {
        if (fx == null)
            return fx = (inVector) -> {
                ArrayList<Integer> ar = new ArrayList<>();
                inVector.getValues().forEach(i -> ar.add(i / 2));
                return new IntegerVector(ar);
            };
        else
            return fx;
    }

    @Override
    Vector<Integer> negate(Vector<Integer> t) {
        ArrayList<Integer> ar = new ArrayList<>();
        int counter = 0;
        for(Integer d : this.getValues())
        {9
            ar.add(d - t.getValues().get(counter++));
        }
        return new IntegerVector(ar);
    }


    /*
    @Override
    public Function<Vector<Integer>, Vector<Integer>> getHalf() {
        return new Function<Vector<Integer>, Vector<Integer>>() {
            @Override
            public Vector<Integer> apply(Vector<Integer> integerVector) {

                Vector<Integer> halfd = new IntegerVector();

                for(Integer i : integerVector.getValues())
                {

                }
            }
        }
    }
    */
}
