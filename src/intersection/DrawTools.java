package intersection;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class DrawTools {
	private Graphics g;

	public DrawTools(Graphics g){
		this.g = g;
	}

	public void setColor(Color c){
		g.setColor(c);
	}

	public void drawCircle(int cx, int cy, int rad, boolean fill){
		if( fill ){
			g.fillOval(cx - rad, cy - rad, rad * 2, rad * 2);
		}else{
			g.drawOval(cx - rad, cy - rad, rad * 2, rad * 2);
		}
	}

	public void drawCircle(Circle c, boolean fill){
		drawCircle((int)c.x, (int)c.y, (int)c.r, fill);
	}

	public void drawCircle(int cx, int cy, int px, int py, boolean fill){
		drawCircle((int)cx, (int)cy, (int)(
			Math.sqrt(Math.pow(px - cx, 2) + Math.pow(py - cy, 2))), fill);
	}

	public void drawGrid(int lux, int luy, int width, int height, int gap){
		for(int x=lux; x<=width; x+=gap){
			g.drawLine(x, luy, x, height);
		}
		for(int y=luy; y<=height; y+=gap){
			g.drawLine(lux, y, width, y);
		}
	}

	public void drawLinear(Linear linear, Rectangle bounds){
		int x1, y1, x2, y2;
		if( linear.getY(0) >= 0 ){
			x1 = 0; y1 = (int)linear.getY(x1);
		}else{
			x1 = bounds.width; y1 = (int)linear.getY(x1);
		}
		if( linear.getX(0) >= 0 ){
			y2 = 0; x2 = (int)linear.getX(y2);
		}else{
			y2 = bounds.height; x2 = (int)linear.getX(y2);
		}
		g.drawLine(x1, y1, x2, y2);
	}

	public void drawStringCenter(String str, int x, int y){
		FontMetrics fm = g.getFontMetrics();
		g.drawString(str, x - fm.stringWidth(str) / 2, y);
	}
}
