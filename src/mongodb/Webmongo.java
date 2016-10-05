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
		MongoClient client = new MongoClient("150.89.234.253", 27017);
		MongoCollection<Document> coll = client.getDatabase("myproject-room").getCollection("beacons");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -60);

		FindIterable<Document> result11 = coll.find(Filters.and(Filters.eq("receiver", "1号機"), Filters.eq("minor", 101),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result12 = coll.find(Filters.and(Filters.eq("receiver", "1号機"), Filters.eq("minor", 102),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result21 = coll.find(Filters.and(Filters.eq("receiver", "2号機"), Filters.eq("minor", 101),Filters.gte("date", cal.getTime())));
		FindIterable<Document> result22 = coll.find(Filters.and(Filters.eq("receiver", "2号機"), Filters.eq("minor", 102),Filters.gte("date", cal.getTime())));

		int cnt11 = 0, cnt12 = 0, cnt21 = 0, cnt22 = 0;
		int sum11 = 0, sum12 = 0, sum21 = 0, sum22 = 0;
		double ave11 = 0, ave12 = 0, ave21 = 0, ave22 = 0;

		for(Document doc : result11){
			try{
				int val11 = doc.getInteger("強度");
				if(!Double.isNaN(val11)){
					sum11 += val11;
					cnt11++;
				}
			}catch(ClassCastException e0){}
		}
		for(Document doc : result12){
			try{
				int val12 = doc.getInteger("強度");
				if(!Double.isNaN(val12)){
					sum12 += val12;
					cnt12++;
				}
			}catch(ClassCastException e0){}
		}
		for(Document doc : result21){
			try{
				int val21 = doc.getInteger("強度");
				if(!Double.isNaN(val21)){
					sum21 += val21;
					cnt21++;
				}
			}catch(ClassCastException e0){}
		}
		for(Document doc : result22){
			try{
				int val22 = doc.getInteger("強度");
				if(!Double.isNaN(val22)){
					sum22 += val22;
					cnt22++;
				}
			}catch(ClassCastException e0){}
		}

		List<Document> data = new ArrayList<Document>();
		Date now = Calendar.getInstance().getTime();


		ave11 = (double)sum11 / cnt11 ;
		System.out.println("1号機 101の１分間の平均" + ave11 + "です。");
		Document doc11 = new Document();
		doc11.append("receiver", "1号機")
			.append("minor", 101)
			.append("平均強度", ave11)
			.append("date", now);
		data.add(doc11);

		ave12 = (double)sum12 / cnt12 ;
		System.out.println("1号機 102の１分間の平均" + ave12 + "です。");
		Document doc12 = new Document();
		doc12.append("receiver", "1号機")
			.append("minor", 102)
			.append("平均強度", ave12)
			.append("date", now);
		data.add(doc12);

		ave21 = (double)sum21 / cnt21 ;
		System.out.println("2号機 102の１分間の平均" + ave21 + "です。");
		Document doc21 = new Document();
		doc21.append("receiver", "2号機")
			.append("minor", 101)
			.append("平均強度", ave21)
			.append("date", now);
		data.add(doc21);

		ave22 = (double)sum22 / cnt22 ;
		System.out.println("2号機 102の１分間の平均" + ave22 + "です。");
		Document doc22 = new Document();
		doc22.append("receiver", "2号機")
			.append("minor", 102)
			.append("平均強度", ave22)
			.append("date", now);
		data.add(doc22);

		MongoCollection<Document> coll2 = client.getDatabase("myproject-room").getCollection("beacons1m");
		coll2.insertMany(data);

		client.close();
	}

}
