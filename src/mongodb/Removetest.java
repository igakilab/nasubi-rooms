package mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

public class Removetest {

	public static void main(String[] args){
		MongoClient client = new MongoClient("150.89.234.253", 27018);

		MongoCollection<Document> coll = client.getDatabase("test").getCollection("user");

		coll.deleteOne(Filters.eq("name", "kawazoe"));

		client.close();
	}
}
