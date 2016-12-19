package intersection;

public class Point {
	public double x;
	public double y;

	public Point(){
		this(0, 0);
	}

	public Point(double px, double py){
		this.x = px;
		this.y = py;
	}

	public void move(double dx, double dy){
		this.x += dx;
		this.y += dy;
	}

	public String toString(){
		return String.format("(%.1f, %.1f)", x, y);
	}
}
