package mongodb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class Webmongo {
	public static void main(String[] args){
		MongoClient client = new MongoClient("mongodb-server");
		//MongoClient client = new MongoClient("150.89.234.253");
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
		MongoCollection<Document> coll4 = client.getDatabase("myproject-room").getCollection("beacons1m");
		//mongoDBのbeacon1mzからデータを取得する
		MongoCollection<Document> coll5 = client.getDatabase("myproject-room").getCollection("beacons1mz");

		ArrayList<RBeacon> pj = new ArrayList<RBeacon>();

		for(int i=0; i<beacons.size(); i++){
			int minor = beacons.get(i);

			//レシーバーとビーコンの平均距離を求める
			RReceiver c[] = CalcAve.posit(minor, coll1, coll3);

			//それぞれの平均距離をデータベースに入れる
			for(int j=0; j<c.length; j++){
			//ドキュメントを初期化する
				Document doc1 = new Document();

			//ドキュメントに値を設定する
				doc1.append("minor", minor)
					.append("receiver",c[j].name)
					.append("avedist", c[j].r)
					.append("date", cal2.getTime());
			//データベースに登録する
				coll4.insertOne(doc1);
			}

			//ビーコンの座標を求める
			RPoint p1 = CalcAve.posiz(c);
			System.out.println("結果(" + minor + ") " + p1);

			if( p1 != null ){
				//座標をデータベースに入れる
				//ドキュメントを初期化する
				Document doc2 = new Document();

				//ドキュメントに値を設定する
				doc2.append("minor", minor)
					.append("x", p1.x)
					.append("y", p1.y)
					.append("date", cal2.getTime());
				//データベースに登録する
				coll5.insertOne(doc2);

				RBeacon bcon = new RBeacon();
				bcon.minor = minor;
				bcon.x = p1.x;
				bcon.y = p1.y;
				pj.add(bcon);
			}
		}

		Judge.getNearBeacons(client.getDatabase("myproject-room"), pj);


		client.close();
	}
}
