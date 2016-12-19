package intersection;

public class Circle extends Point{
	public double r;

	public Circle(){
		this(0, 0, 0);
	}

	public Circle(double cx, double cy, double r){
		super(cx, cy);
		this.r = r;
	}

	public void expand(double dr){
		this.r += r;
	}
}
