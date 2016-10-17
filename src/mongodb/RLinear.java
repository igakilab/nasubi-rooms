package mongodb;

class RLinear {
  public double a;
  public double b;
  public double c;

  public RLinear(double a, double b, double c){
    set(a, b, c);
  }

  public RLinear(){
    this(0, 0, 0);
  }

  public void set(double a, double b, double c){
    this.a = a;
    this.b = b;
    this.c = c;
  }

  public String toString(){
    return String.format("%.1fx + %.1fy + %.1f = 0", a, b, c);
  }
}
