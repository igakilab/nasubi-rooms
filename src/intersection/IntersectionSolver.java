package intersection;

import java.util.ArrayList;
import java.util.List;

public class IntersectionSolver {
	public static Linear getPointIntersectionLinear(Point p1, Point p2){
		double a = p2.y - p1.y;
		double b = - (p2.x - p1.x);
		double c = p1.y * (p2.x - p1.x) - p1.x * (p2.y - p1.y);

		return new Linear(a, b, c);
	}

	public static Linear getCircleIntersectionLinear(Circle c1, Circle c2){
		double a = 2 * (c2.x - c1.x);
		double b = 2 * (c2.y - c1.y);
		double c = (c1.x - c2.x) * (c1.x + c2.x)
			+ (c1.y - c2.y) * (c1.y + c2.y) - (c1.r - c2.r) * (c1.r + c2.r);

		return new Linear(a, b, c);
	}

	public static Point getLinearIntersection(Linear l1, Linear l2){
		double det = l1.a * l2.b - l2.a * l1.b;
		if( det != 0 ){
			return new Point(
				(l1.b * l2.c - l2.b * l1.c) / det,
				(l2.a * l1.c - l1.a * l2.c) / det
			);
		}else{
			return null;
		}
	}

	private List<Circle> circles;
	private Linear linear1;
	private Linear linear2;
	private Point pt;
	private boolean twoCircleDetect;

	public IntersectionSolver(){
		circles = new ArrayList<Circle>();
		linear1 = null;
		linear2 = null;
		pt = null;
		twoCircleDetect = false;
	}

	public void clearCircle(){
		circles.clear();
	}

	public void addCircle(Circle c){
		circles.add(c);
	}

	public List<Circle> getCircles(){
		return circles;
	}

	public Point solve(){
		if( circles.size() >= 2 ){
			linear1 = IntersectionSolver.getCircleIntersectionLinear(
					circles.get(0), circles.get(1));
		}
		if( circles.size() >= 3 ){
			linear2 = IntersectionSolver.getCircleIntersectionLinear(
					circles.get(1), circles.get(2));
		}else if( circles.size() >= 2 && twoCircleDetect ){
			linear2 = IntersectionSolver.getPointIntersectionLinear(
					circles.get(0), circles.get(1));
		}

		if( linear1 != null && linear2 != null ){
			pt = IntersectionSolver.getLinearIntersection(linear1, linear2);
			return pt;
		}else{
			return null;
		}
	}

	public Linear[] getIntersectionLinears(){
		Linear[] lin = {linear1, linear2};
		return lin;
	}

	public Point getIntersectionPoint(){
		return pt;
	}
}
