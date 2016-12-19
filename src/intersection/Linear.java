package intersection;

public class Linear {
	/* ax + by + c = 0 */

	double a;
	double b;
	double c;

	public Linear(double a, double b, double c){
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public double getX(double y){
		if( b == 0 ){
			return -c / a;
		}else{
			return (-c - (b * y)) / a;
		}
	}

	public double getY(double x){
		if( a == 0 ){
			return -c / b;
		}else{
			return (-c - (a * x)) / b;
		}
	}

	public String toString(){
		return String.format("%.1fx + %.1fy + %.1f = 0", a, b, c);
	}
}
