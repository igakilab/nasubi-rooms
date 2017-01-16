package jp.ac.oit.igakilab.dwr.multiple;

import java.util.ArrayList;
import java.util.Arrays;
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
		return new MongoClient("150.89.234.253");
	}

	public TeamNowForm teamNow(String partyName){
		//フォームを生成
		TeamNowForm form = new TeamNowForm();

		//DBクライアント初期化
		MongoClient client = createClient();
		MongoDatabase db = client.getDatabase("myproject-room");
		MongoCollection<Document> coll1 = db.getCollection("party");
		MongoCollection<Document> coll2 = db.getCollection("judgement");

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
		//経験値計算＆取得
		FindIterable<Document> res = coll2.find(Filters.eq("party", partyName));
		int exp = 0;

		for(Document doc : res){
			exp += doc.getInteger("exp", 0);
		}
		int lev = exp / 300;

		//テスト用の値を設定
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
}
