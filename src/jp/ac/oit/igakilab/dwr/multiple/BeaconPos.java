package jp.ac.oit.igakilab.dwr.multiple;

import java.util.Date;

public class BeaconPos {
	private Date date;
	private double x;
	private double y;

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	public String toString(){
		return String.format("[%s] %f, %f", date.toString(), x, y);
	}
}
