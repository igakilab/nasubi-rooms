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

  public double distance (RPoint p1){
	  return Math.sqrt(Math.pow(p1.x - this.x,2)+Math.pow(p1.y - this.y,2));
  }
}
