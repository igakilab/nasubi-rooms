package jp.ac.oit.igakilab.dwr.multiple;

import java.util.ArrayList;
import java.util.List;

public class PositionForm {
	public static class Point{
		private double x;
		private double y;

		public Point(){
			this(0, 0);
		}

		public Point(double x, double y){
			this.x = x; this.y = y;
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
	}

	private String memberName;
	private List<Point> positions;

	public PositionForm(){
		positions = new ArrayList<Point>();
	}

	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public List<Point> getPositions() {
		return positions;
	}
	public void setPositions(List<Point> positions) {
		this.positions = positions;
	}
}
