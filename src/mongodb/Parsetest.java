package mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public abstract class Parsetest {
	public static void main(String[] args){
		MongoClient client = new MongoClient("150.89.234.253", 27018);
		MongoCollection<Document> coll = client.getDatabase("test").getCollection("user");

		coll.insertOne(Document.parse("{name:\"simizu|\", team:\"gt-room\"}"));

		client.close();
	}
}
