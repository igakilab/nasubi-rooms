package mongodb;

class RCircle extends RPoint{
  public double r;

  public RCircle(double x, double y, double r){
    super(x, y);
    this.r = r;
  }

  public RCircle(){
    this(0, 0, 0);
  }

  public String toString(){
    return String.format("RCircle center=(%.1f, %.1f) radius=%.1f",
      x, y, r);
  }
}
