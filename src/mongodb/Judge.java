package mongodb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

public class Judge {

	public static ArrayList<Integer> getNearBeacons(MongoDatabase db, List<RBeacon> pj){

		MongoCollection<Document> coll1 = db.getCollection("judgement");
		MongoCollection<Document> coll2 = db.getCollection("member");
		MongoCollection<Document> coll3 = db.getCollection("party");

		ArrayList<Integer> pjb = new ArrayList<Integer>();

		FindIterable<Document> parties = coll3.find();
		for(Document party : parties){
			ArrayList<RBeacon> beacons = new ArrayList<>();

			//パーティーのメンバーリストを取得
			@SuppressWarnings("unchecked")
			List<Object> members = (List<Object>)party.get("member");
			for(Object m : members){
				if( m instanceof String ){
					//メンバーを文字列に変換
					String name = (String)m;
					System.out.println("member:" + name);
					//メンバーDBに問い合わせ
					Document tmp = coll2.find(Filters.eq("name", name)).first();
					//見つかったらminorsに追加
					if( tmp != null ){
						Integer minor = getIntData(tmp, "minor");
						for(RBeacon bc : pj){
							if( bc.minor == minor ){
								beacons.add(bc);
							}
						}
					}
				}
			}
			beacons.forEach((b -> System.out.print(b.minor + " ")));
			System.out.println();

			//ビーコンの位置からメンバーで活動しているか判定する
			boolean flag1 = false;
			for(int i=0; i<beacons.size(); i++){
				boolean flag2 = true;
				for(int j=i;j<beacons.size();j++){
					if(i!=j){
						if(beacons.get(i).distance(beacons.get(j))<1.5){
							flag2 =false;
						}
					}
				}
				if( flag2 ){
					flag1 = true;
					break;
				}
			}




			//judgeデータベースに登録する
			Calendar cal = Calendar.getInstance();
			String partyName = party.getString("party");
			System.out.println("party[" + partyName + "] working : " + flag1);

			//計測中のデータを取得する
			Document latestJudge = coll1.find(
				Filters.and(
					Filters.eq("party", partyName),
					Filters.eq("end", null)
				)
			).sort(
				Sorts.descending("start")
			).first();

			//計測中データがあるとき
			if( latestJudge != null ){
				//なおかつ、判定が真のとき -> 経験値をプラスする
				if( flag1 ){
					coll1.updateOne(
						Filters.eq("_id", latestJudge.get("_id")),
						Updates.inc("exp", 1)
					);
				//判定が偽の時 -> 計測を終了する
				}else{
					coll1.updateOne(
						Filters.eq("_id", latestJudge.get("_id")),
						Updates.combine(
							Updates.set("end", cal.getTime()),
							Updates.inc("exp", 1)
						)
					);
				}
			//計測中データがない時
			}else{
				//判定が真の時 -> 計測を開始する
				if( flag1 ){
					Document ndoc = new Document("party", partyName)
						.append("start", cal.getTime())
						.append("exp", 0);
					coll1.insertOne(ndoc);
				}
			}

		}
	return pjb;
	}


	public static int getIntData(Document doc, String field){
		Object data = doc.get(field);
		if( data instanceof Integer ){
			return (Integer)data;
		}else if( data instanceof Double ){
			return ((Double)data).intValue();
		}else{
			return 0;
		}
	}
}
