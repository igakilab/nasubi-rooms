package jp.ac.oit.igakilab.dwr.multiple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class Feedback {
	private MongoClient createClient(){
		//return new MongoClient("150.89.234.253");
		return new MongoClient("mongodb-server");
	}

	public TeamNowForm teamNow(String partyName){
		//フォームを生成
		TeamNowForm form = new TeamNowForm();

		//DBクライアント初期化
		MongoClient client = createClient();
		MongoDatabase db = client.getDatabase("myproject-room");
		MongoCollection<Document> coll1 = db.getCollection("party");
		MongoCollection<Document> coll2 = db.getCollection("judgement");
		MongoCollection<Document> coll3 = db.getCollection("taskcontain");

		//パーティー名取得
		Document partydata = coll1.find(Filters.eq("party", partyName)).first();
		//メンバー名取得
		@SuppressWarnings("unchecked")
		List<Object> members = (List<Object>)partydata.get("member");
		if( members.size() > 0 ){
			form.setMember1((String)members.get(0));
		}
		if( members.size() > 1 ){
			form.setMember2((String)members.get(1));
		}
		if( members.size() > 2 ){
			form.setMember3((String)members.get(2));
		}


		//タスクの情報を取得
		Document data = coll3.find(Filters.eq("team", partyName)).sort(Sorts.descending("date")).first();
		Date start = null;
		Date end = null;
		if(data!=null){
			form.setGoal(data.getInteger("goaltime"));

			@SuppressWarnings("unchecked")
			List<Object> tasks = (List<Object>)data.get("task");
			String[] casted = new String[tasks.size()];
			for(int i=0; i<tasks.size(); i++){
				if( tasks.get(i) instanceof String ){
					casted[i] = (String)tasks.get(i);
				}
			}
			form.setTask(casted);
			start = data.getDate("date");
			Calendar tmp = Calendar.getInstance();
			tmp.setTime(start);
			tmp.add(Calendar.DATE, 7);
			end = tmp.getTime();
		}
		//経験値計算＆取得
		FindIterable<Document> res = coll2.find(Filters.eq("party", partyName));
		int exp = 0;
		int nowexp = 0;

		for(Document doc : res){
			exp += doc.getInteger("exp", 0);
			if(start!=null){
				Date date = doc.getDate("start");
				int diff1 = start.compareTo(date);
				int diff2 = end.compareTo(date);
				if(diff1 < 0 && diff2 > 0){
					System.out.println("matched.");
					nowexp += doc.getInteger("exp", 0);
				}
			}
		}

		int lev = exp / 300;
		int nowtime = nowexp / 60;



		//テスト用の値を設定
		form.setNowtime(nowtime);
		form.setName(partyName);
		form.setLevel(lev);
		form.setExp(exp);

		client.close();
		return form;
	}

	public  List<TeamRankForm> teamRank(){

		//DBクライアント初期化
		MongoClient client = createClient();
		MongoDatabase db = client.getDatabase("myproject-room");
		MongoCollection<Document> coll2 = db.getCollection("judgement");

		AggregateIterable<Document> result = coll2.aggregate(Arrays.asList(
			Aggregates.group("$party", Accumulators.sum("exp", "$exp")),
			Aggregates.sort(Sorts.descending("exp"))
		));

		ArrayList<TeamRankForm> ranking = new ArrayList<>();

		for(Document doc :result){
			TeamRankForm form = new TeamRankForm();
			form.setPartyName(doc.getString("_id"));
			form.setLevel(doc.getInteger("exp")/300);
			ranking.add(form);
		}
		client.close();
		return ranking;
	}

	public TeamTaskForm teamTask(String partyName){
		//ファームを作成
		TeamTaskForm form = new TeamTaskForm();

		//DBクライアント初期化
		MongoClient client = createClient();
		MongoDatabase db = client.getDatabase("myproject-room");
		MongoCollection<Document> coll1 = db.getCollection("party");

		//パーティー名取得
		//Document partydata = coll1.find(Filters.eq("party", partyName)).first();
		form.setPartyName(partyName);

		client.close();
		return form;
	}

	public void transemitTask(String team, int hour, String contain){
		MongoClient client = createClient();
		MongoDatabase db = client.getDatabase("myproject-room");
		MongoCollection<Document> coll1 = db.getCollection("party");
		MongoCollection<Document> coll2 = db.getCollection("taskcontain");

		Calendar cal = Calendar.getInstance();

		String[] split = contain.split("\n");
		List<String> tasks = new ArrayList<String>();
		for(String s : split){
			if( s.trim().length() > 0 ){
				tasks.add(s);
			}
		}

		Document doc = new Document("team", team)
					.append("date", cal.getTime())
					.append("goaltime", hour)
					.append("task", tasks);
		coll2.insertOne(doc);

	}

}
