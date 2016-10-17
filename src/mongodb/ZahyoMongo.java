package mongodb;

import com.mongodb.MongoClient;

public class ZahyoMongo {
	public static void main(String[] args){
		MongoClient client = new MongoClient("mongodb-server");
		//MongoCollection<Document> coll = client.getDatabase("myproject-room").getCollection("beacons");

		/*Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -60);

		FindIterable<Document> result11 = coll.find(Filters.and(Filters.eq("receiver", "1号機"), Filters.eq("minor", 101),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result12 = coll.find(Filters.and(Filters.eq("receiver", "1号機"), Filters.eq("minor", 102),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result21 = coll.find(Filters.and(Filters.eq("receiver", "2号機"), Filters.eq("minor", 101),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result22 = coll.find(Filters.and(Filters.eq("receiver", "2号機"), Filters.eq("minor", 102),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result31 = coll.find(Filters.and(Filters.eq("receiver", "3号機"), Filters.eq("minor", 101),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result32 = coll.find(Filters.and(Filters.eq("receiver", "3号機"), Filters.eq("minor", 102),Filters.gte("date", cal.getTime())));



		MongoCollection<Document> coll2 = client.getDatabase("myproject-room").getCollection("beacons1mz");
		coll2.insertMany(data);*/
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

