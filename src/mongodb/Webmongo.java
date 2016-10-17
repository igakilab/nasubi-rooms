package mongodb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class Webmongo {
	public static void main(String[] args){
		MongoClient client = new MongoClient("mongodb-server");
		MongoCollection<Document> coll1 = client.getDatabase("myproject-room").getCollection("beacons");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -60);
		System.out.println("border time: " + cal.getTime().toString());

		FindIterable<Document> result11 = coll1.find(Filters.and(Filters.eq("receiver", "1号機"), Filters.eq("minor", 101),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result12 = coll1.find(Filters.and(Filters.eq("receiver", "1号機"), Filters.eq("minor", 102),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result21 = coll1.find(Filters.and(Filters.eq("receiver", "2号機"), Filters.eq("minor", 101),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result22 = coll1.find(Filters.and(Filters.eq("receiver", "2号機"), Filters.eq("minor", 102),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result31 = coll1.find(Filters.and(Filters.eq("receiver", "3号機"), Filters.eq("minor", 101),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result32 = coll1.find(Filters.and(Filters.eq("receiver", "3号機"), Filters.eq("minor", 102),Filters.gte("date", cal.getTime())));

		//coll1.deleteMany(new Document());

		int cnt11 = 0, cnt12 = 0, cnt21 = 0, cnt22 = 0, cnt31 = 0, cnt32 = 0;
		double sum11 = 0, sum12 = 0, sum21 = 0, sum22 = 0, sum31 = 0, sum32 = 0;
		double ave11 = 0, ave12 = 0, ave21 = 0, ave22 = 0, ave31 = 0, ave32 = 0;

		for(Document doc : result11){
			System.out.print("receiver1 - 101 : ");
			try{
				double val11 = doc.getDouble("距離");
				System.out.println(val11);
				if(!Double.isNaN(val11)){
					sum11 += val11;
					cnt11++;
				}
			}catch(ClassCastException e0){
				System.out.println(e0.getMessage());
			}
		}
		System.out.println("receiver1 - 101 : [SUM VALUE] " + sum11 + "(" + cnt11 + ")");
		for(Document doc : result12){
			try{
				double val12 = doc.getDouble("距離");
				if(!Double.isNaN(val12)){
					sum12 += val12;
					cnt12++;
				}
			}catch(ClassCastException e0){
				System.out.println(e0.getMessage());
			}
		}
		for(Document doc : result21){
			System.out.print("receiver2 - 101 : ");
			try{
				double val21 = doc.getDouble("距離");
				System.out.println(val21);
				if(!Double.isNaN(val21)){
					sum21 += val21;
					cnt21++;
				}
			}catch(ClassCastException e0){
				System.out.println(e0.getMessage());
			}
		}
		System.out.println("receiver2 - 101 : [SUM VALUE] " + sum21 + "(" + cnt21 + ")");
		for(Document doc : result22){
			try{
				double val22 = doc.getDouble("距離");
				if(!Double.isNaN(val22)){
					sum22 += val22;
					cnt22++;
				}
			}catch(ClassCastException e0){
				System.out.println(e0.getMessage());
			}
		}

		for(Document doc : result31){
			try{
				double val31 = doc.getDouble("距離");
				if(!Double.isNaN(val31)){
					sum31 += val31;
					cnt31++;
				}
			}catch(ClassCastException e0){
				System.out.println(e0.getMessage());
			}
		}
		for(Document doc : result32){
			try{
				double val32 = doc.getDouble("距離");
				if(!Double.isNaN(val32)){
					sum32 += val32;
					cnt32++;
				}
			}catch(ClassCastException e0){
				System.out.println(e0.getMessage());
			}
		}

		List<Document> data = new ArrayList<Document>();
		Date now = Calendar.getInstance().getTime();


		ave11 = (double)sum11 / cnt11 ;
		System.out.println("1号機 101の１分間の平均" + ave11 + "です。");
		Document doc11 = new Document();
		if(!Double.isNaN(ave11)){
				doc11.append("receiver", "1号機")
				.append("minor", 101)
				.append("平均距離", ave11)
				.append("date", now);
				data.add(doc11);
		}

		ave12 = (double)sum12 / cnt12 ;
		System.out.println("1号機 102の１分間の平均" + ave12 + "です。");
		Document doc12 = new Document();
		if(!Double.isNaN(ave12)){
			doc12.append("receiver", "1号機")
				.append("minor", 102)
				.append("平均距離", ave12)
				.append("date", now);
			data.add(doc12);
		}

		ave21 = (double)sum21 / cnt21 ;
		System.out.println("2号機 101の１分間の平均" + ave21 + "です。");
		Document doc21 = new Document();
		if(!Double.isNaN(ave21)){
			doc21.append("receiver", "2号機")
				.append("minor", 101)
				.append("平均距離", ave21)
				.append("date", now);
			data.add(doc21);
		}

		ave22 = (double)sum22 / cnt22 ;
		System.out.println("2号機 102の１分間の平均" + ave22 + "です。");
		Document doc22 = new Document();
		if(!Double.isNaN(ave22)){
			doc22.append("receiver", "2号機")
				.append("minor", 102)
				.append("平均距離", ave22)
				.append("date", now);
			data.add(doc22);
		}

		ave31 = (double)sum31 / cnt31 ;
		System.out.println("3号機 101の１分間の平均" + ave31 + "です。");
		Document doc31 = new Document();
		if(!Double.isNaN(ave31)){
			doc31.append("receiver", "3号機")
				.append("minor", 101)
				.append("平均距離", ave31)
				.append("date", now);
			data.add(doc31);
		}

		ave32 = (double)sum32 / cnt32 ;
		System.out.println("3号機 102の１分間の平均" + ave32 + "です。");
		Document doc32 = new Document();
		if(!Double.isNaN(ave32)){
			doc32.append("receiver", "3号機")
				.append("minor", 102)
				.append("平均距離", ave32)
				.append("date", now);
			data.add(doc32);
		}

		MongoCollection<Document> coll2 = client.getDatabase("myproject-room").getCollection("beacons1m");

		if( data.size() > 0 ){
			coll2.insertMany(data);
		}
		coll1.deleteMany(new Document());

		RCircle c1 = new RCircle();
		RCircle c2 = new RCircle();
		RCircle c3 = new RCircle();

	//mongoDBのreceiverからデータを取得する
		MongoCollection<Document> coll3 = client.getDatabase("myproject-room").getCollection("receivers");
	//受信機１のレコードを取得する
		Document doc = coll3.find(Filters.eq("name", "1号機")).first();
	//x座標を取得する
		double x = doc.getDouble("x");
	//y座標を取得する
		double y = doc.getDouble("y");
	//c1.xにx座標をセット
		c1.x = x;
	//c1.yにy座標をセット
		c1.y = y;
	//受信機2のレコードを取得する
		doc = coll3.find(Filters.eq("name", "2号機")).first();
	//c2.xにx座標をセット
		c2.x = doc.getDouble("x");
	//c2.yにy座標をセット
		c2.y = doc.getDouble("y");
	//受信機3のレコードを取得する
		doc = coll3.find(Filters.eq("name", "3号機")).first();
		//c3.xにx座標をセット
		c3.x = doc.getDouble("x");
	//c3.yにy座標をセット
		c3.y = doc.getDouble("y");

	/*RCircleにビーコン101の1～3号機からの平均距離を設定する*/

	//c1.rに平均距離をセットする
		c1.r = ave11;
	//c2.rに平均距離をセットする
		c2.r = ave21;
	//c3.rに平均距離をセットする
		c3.r = ave31;

	//101の座標計算をする
		RPoint p1 = ZahyoMongo.getPosition(c1, c2, c3);

	/*mongoDBにぶちこむための準備*/

	//ドキュメントを初期化する
		Document doc1 = new Document();
	//ドキュメントに値を設定する
		doc1.append("minor", 101)
			.append("x座標", p1.x)
			.append("y座標", p1.y)
			.append("date", now);
	//コレクションを取得する
	MongoCollection<Document> coll4 = client.getDatabase("myproject-room").getCollection("beacons1mz");
	//データベースに登録する
	coll4.insertOne(doc1);



	client.close();
	}

}
