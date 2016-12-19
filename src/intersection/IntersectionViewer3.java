package intersection;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class IntersectionViewer3 extends Applet
implements MouseListener, MouseMotionListener{
	boolean TWO_CIRCLES_DETECT = false;

	Circle tmp = null;
	List<Circle> circles = new ArrayList<Circle>();

	public void init(){
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paint(Graphics g){
		DrawTools dg = new DrawTools(g);

		drawCircles(dg);

		if( tmp == null ){
			drawIntersections(dg);
		}
	}

	public void drawCircles(DrawTools dg){
		dg.setColor(Color.black);
		for(Circle c : circles){
			dg.drawStringCenter(String.format("(%d, %d, %d)",
				(int)c.x, (int)c.y, (int)c.r), (int)c.x, (int)c.y);
			dg.drawCircle(c, false);
		}
	}

	public void drawIntersections(DrawTools dg){
		IntersectionSolver solver = new IntersectionSolver();

		circles.forEach((c -> solver.addCircle(c)));
		solver.solve();

		Rectangle bounds = getBounds();
		dg.setColor(Color.blue);
		for(Linear lin : solver.getIntersectionLinears()){
			if( lin != null ) dg.drawLinear(lin, bounds);
		}

		dg.setColor(Color.red);
		intersection.Point pt = solver.getIntersectionPoint();
		if( pt != null ){
			dg.drawCircle((int)pt.x, (int)pt.y, 3, true);
			dg.drawStringCenter(String.format("(%d, %d)",
					(int)pt.x, (int)pt.y), (int)pt.x, (int)pt.y);
		}
	}

	public void mousePressed(MouseEvent e){
		Point p = e.getPoint();
		tmp = new Circle(p.x, p.y, 0);
		circles.add(tmp);
		repaint();
	}

	public void mouseReleased(MouseEvent e){
		tmp = null;
		repaint();
	}

	public void mouseDragged(MouseEvent e){
		Point p = e.getPoint();
		if( tmp != null ){
			tmp.r = Math.sqrt(
				Math.pow(p.x - tmp.x, 2) + Math.pow(p.y - tmp.y, 2));
		}
		repaint();
	}

	public void mouseClicked(MouseEvent e){
		tmp = null;
		circles.clear();
		repaint();
	}

	public void mouseMoved(MouseEvent e){
		Point p = e.getPoint();
		showStatus(String.format("(%d, %d)", p.x, p.y));
	}

	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}
