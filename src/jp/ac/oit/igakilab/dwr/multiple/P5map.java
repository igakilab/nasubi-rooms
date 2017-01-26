package jp.ac.oit.igakilab.dwr.multiple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class P5map {
	private MongoClient createClient(){
		//return new MongoClient("150.89.234.253");
		return new MongoClient("mongodb-server");
	}

	public List<PositionForm> latestPosition(){
		//DBクライアント初期化
		MongoClient client = createClient();
		MongoDatabase db = client.getDatabase("myproject-room");
		MongoCollection<Document> coll2 = db.getCollection("beacons1mz");
		MongoCollection<Document> coll3 = db.getCollection("member");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -2);
		Date since = cal.getTime();

		//メンバー一覧を取得
		List<Bson> query = Arrays.asList(
			Aggregates.match(Filters.gte("date", since)),
			Aggregates.group("$minor")
		);

		ArrayList<PositionForm> positions = new ArrayList<>();

		for(Document doc : coll2.aggregate(query)){
			Integer id = doc.getInteger("_id");
			Document m = coll3.find(Filters.eq("minor", id)).first();

			if( id == null || m == null ) continue;

			//インスタンス生成
			PositionForm mp = new PositionForm();
			mp.setMemberName(m.getString("name"));

			//メンバーを取得
			Bson filter = Filters.and(
				Filters.eq("minor", id),
				Filters.gte("date", since));

			for(Document doc2 : coll2.find(filter).sort(Sorts.descending("date"))){
				mp.getPositions().add(new PositionForm.Point(
					doc2.getDouble("x"), doc2.getDouble("y")));
			}
			positions.add(mp);
		}

		client.close();
		return positions;
	}
}
