package mongodb;

import java.util.List;

import com.mongodb.MongoClient;

public class TestBeaconDataFetcher {
	public static void main(String[] args){
		BeaconDataFetcher.DB_NAME = "test";
		MongoClient client = new MongoClient();

		BeaconDataFetcher fet = new BeaconDataFetcher(client);

		List<Integer> minors = fet.getBeaconList();
		System.out.println(minors.toString());

		for(int i=0; i<minors.size(); i++){
			int beaconId = minors.get(i);
			List<String> receivers = fet.getBeaconReceiverList(beaconId);

			System.out.println("--" + beaconId + " " + receivers.toString());
			for(String recv : receivers){
				double dave = fet.getDistanceAverage(beaconId, recv);
				System.out.println("\t" + recv + " : " + dave);
			}

			if( receivers.size() > 2 ){
				RCircle c0 = new RCircle(0, 0, fet.getDistanceAverage(beaconId, receivers.get(0)));
				RCircle c1 = new RCircle(66, 30, fet.getDistanceAverage(beaconId, receivers.get(1)));
				RCircle c2 = new RCircle(0, 78, fet.getDistanceAverage(beaconId, receivers.get(2)));

				RPoint pt = getPosition(c0, c1, c2);
				System.out.println("\t POS: " + pt.toString());
			}
		}

		client.close();
	}

	public static RPoint getPosition(RCircle c1, RCircle c2, RCircle c3){
		RPoint p1 = new RPoint();
		RLinear l1 = new RLinear();
		RLinear l2 = new RLinear();


		l1.a = 2*(c2.x - c1.x);
		l1.b = 2*(c2.y - c1.y);
		l1.c = (c1.x - c2.x) * (c1.x + c2.x) + (c1.y - c2.y) * (c1.y + c2.y) - (c1.r  -c2.r) * (c1.r + c2.r);

		l2.a = 2*(c3.x - c2.x);
		l2.b = 2*(c3.y - c2.y);
		l2.c = (c2.x - c3.x) * (c2.x + c3.x) + (c2.y - c3.y) * (c2.y + c3.y) - (c2.r  -c3.r) * (c2.r + c3.r);

		p1.x = ((l1.b * l2.c) - (l2.b * l1.c)) / ((l1.a * l2.b) - (l2.a * l1.b));
		p1.y = ((l2.c * l1.c) - (l1.a * l2.c)) / ((l1.a * l2.b) - (l2.a * l2.b));

		return p1;
	}
}

class RPoint{
	  public double x;
	  public double y;

	  public RPoint(double x, double y){
	    this.x = x;
	    this.y = y;
	  }

	  public RPoint(){
	    this(0, 0);
	  }

	  public String toString(){
	    return String.format("RPoint: (%.1f, %.1f)", x, y);
	  }
}

class RCircle extends RPoint{
	  public double r;

	  public RCircle(double x, double y, double r){
	    super(x, y);
	    this.r = r;
	  }

	  public RCircle(){
	    this(0, 0, 0);
	  }

	  public String toString(){
	    return String.format("RCircle center=(%.1f, %.1f) radius=%.1f",
	      x, y, r);
	  }
}

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
