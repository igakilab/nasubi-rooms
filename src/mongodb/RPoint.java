package mongodb;

class RPoint{
  public double x;
  public double y;

  public RPoint(double x, double y){
    this.x = x;
    this.y = y;
  }

  public RPoint(){
    this(0, 0);
  }

  public String toString(){
    return String.format("RPoint: (%.1f, %.1f)", x, y);
  }
}
