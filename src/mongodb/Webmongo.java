package mongodb;

import java.util.Calendar;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class Webmongo {
	public static void main(String[] args){
		MongoClient client = new MongoClient("mongodb-server");
		//MongoCollection<Document> coll1 = client.getDatabase("myproject-room").getCollection("beacons");
		BeaconAggregator coll1 = new BeaconAggregator(client);

		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.SECOND, -60);
		coll1.setDeadline(cal1.getTime());
		System.out.println("border time: " + cal1.getTime().toString());

		//認識されたビーコンの一覧を取得
		List<Integer> beacons = coll1.getBeaconList();
		//現在時刻を取得
		Calendar cal2 = Calendar.getInstance();
		//mongoDBのreceiverからデータを取得する
		MongoCollection<Document> coll3 = client.getDatabase("myproject-room").getCollection("receivers");
		//mongoDBのbeacon1mからデータを取得する
		MongoCollection<Document> coll2 = client.getDatabase("myproject-room").getCollection("beacons1m");
		//mongoDBのbeacon1mzからデータを取得する

		MongoCollection<Document> coll4 = client.getDatabase("myproject-room").getCollection("beacons1mz");

		for(int i=0; i<beacons.size(); i++){
			int minor = beacons.get(i);

			//そのビーコンが認識されている受信機のリスト
			List<String> receivers = coll1.getBeaconReceiverList(minor);
			if(receivers.size() >= 3){
				System.out.println(minor + ": " + receivers.toString());
				double tmp;
				RCircle[] c = new RCircle[3];

				for(int j=0; j<receivers.size(); j++){
					c[j] = new RCircle();
					Document doc = coll3.find(Filters.eq("name", receivers.get(j))).first();
				//x座標を取得する
					double x = doc.getDouble("x");
				//y座標を取得する
					double y = doc.getDouble("y");
				//x座標をセット
					c[j].x = x;
				//y座標をセット
			    	c[j].y = y;
			    //平均距離をセットする
			    	c[j].r = coll1.getDistanceAverage(minor, receivers.get(j));
			    	tmp = c[j].r;
			    	System.out.println(c[j].toString());

			    //beacon1mに平均距離の配列を登録する
			    	coll2.insertOne(new Document("receiver", receivers.get(j))
			    		.append("minor", beacons.get(i))
			    		.append("avedist",tmp)
			    		.append("date",cal2.getTime()));
				}

			//座標計算をする
				RPoint p1 = ZahyoMongo.getPosition(c[0],c[1],c[2]);
					System.out.println(p1.toString());

			/*mongoDBにぶちこむための準備*/
			//ドキュメントを初期化する
				Document doc1 = new Document();
			//ドキュメントに値を設定する
				doc1.append("minor", minor)
					.append("x", p1.x)
					.append("y", p1.y)
					.append("date", cal2.getTime());
			//データベースに登録する
			coll4.insertOne(doc1);



			}
		}
		client.close();
	}
}
