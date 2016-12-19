package intersection;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class IntersectionViewer extends Applet{
	public void paint(Graphics g){
		Circle c1 = new Circle(80, 70, 50);
		Circle c2 = new Circle(100, 110, 40);
		Circle c3 = new Circle(140, 80, 40);

		DrawTools dg = new DrawTools(g);
		Rectangle br = getBounds();
		dg.setColor(Color.lightGray);
		dg.drawGrid(br.x, br.y, br.width, br.height, 20);

		dg.setColor(Color.black);
		dg.drawCircle(c1, false);
		dg.drawCircle(c2, false);
		dg.drawCircle(c3, false);

		Linear cli = IntersectionSolver.getCircleIntersectionLinear(c1, c2);
		Linear cli2 = IntersectionSolver.getCircleIntersectionLinear(c2, c3);
		Linear cli3 = IntersectionSolver.getCircleIntersectionLinear(c3, c1);
		dg.setColor(Color.blue);
		dg.drawLinear(cli, getBounds());
		dg.drawLinear(cli2, getBounds());
		dg.drawLinear(cli3, getBounds());

		Point p1 = IntersectionSolver.getLinearIntersection(cli, cli2);
		dg.setColor(Color.red);
		dg.drawCircle((int)p1.x, (int)p1.y, 5, true);
	}
}
