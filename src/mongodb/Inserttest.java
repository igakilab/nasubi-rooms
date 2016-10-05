package mongodb;



import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class Inserttest {
	public static void main(String[] args){
			MongoClient client = new MongoClient("150.89.234.253", 27018);
			MongoCollection<Document> coll = client.getDatabase("test").getCollection("user");

			Document doc = new Document();
			doc.append("name", "kitaba");
			doc.append("team", "gt-rooms");

			coll.insertOne(doc);

			client.close();
	}
}
