public class vectors
{
  int x, y;
  public vectors(int a, int b)
  {
    x = a;
    y = b;
  }

  public double magnitude()
  {
    return Math.sqrt(x*x+y*y);
  }

  public vectors add(vectors v)
  {
    return new vectors(v.x+x, v.y+y);
  }

  public int product(vectors v)
  {
    return v.x * x + v.y * y;
  }
  public static void main(String[] args)
  {

  }
}
