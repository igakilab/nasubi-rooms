package mongodb;


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class Updatetest {

		public static void main(String[] args){
				MongoClient client = new MongoClient("150.89.234.253", 27018);

				MongoCollection<Document> coll = client.getDatabase("test").getCollection("user");

				UpdateResult result = coll.updateOne(Filters.eq("name", "koike"), Updates.set("team", "天パボンバー"));
				//coll.updateOne(Filters.eq("name", "koike"), Updates.set("message", "来年もがんばります。"));
				long modified = result.getModifiedCount();
				System.out.println(modified + "うんこです。");

				client.close();

		}
}
