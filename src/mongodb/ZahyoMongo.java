package mongodb;

//import com.mongodb.MongoClient;

public class ZahyoMongo {
	/*public static void main(String[] args){
		MongoClient client = new MongoClient("mongodb-server");

		client.close();
	}*/
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

