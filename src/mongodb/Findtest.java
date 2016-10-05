package mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class Findtest {
	public static void main(String[] args){
		MongoClient client = new MongoClient("150.89.234.253", 27018);
		MongoCollection<Document> coll = client.getDatabase("test").getCollection("user");

		FindIterable<Document> result = coll.find();
		//FindIterable<Document> result = coll.find(Filters.eq("team", "gt-rooms"));

		for(Document doc : result){
				//System.out.println(doc);
			String name = doc.getString("name");
			String team = doc.getString("team");

			System.out.println(name + "さんは" + team + "のDTです。");
		}
		client.close();

	}
}
