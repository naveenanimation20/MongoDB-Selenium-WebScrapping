package MongoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class TestMongo {

	public static void main(String[] args) {

		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);

		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

		MongoDatabase database = mongoClient.getDatabase("person");
//
//		for (String name : database.listCollectionNames()) {
//
//			System.out.println(name);
//		}
//
//		MongoCollection<Document> collection = database.getCollection("employee");
//
//		try (MongoCursor<Document> cur = collection.find().iterator()) {
//
//			while (cur.hasNext()) {
//
//				Document doc = cur.next();
//				ArrayList cars = new ArrayList<>(doc.values());
//
//				System.out.printf("%s: %s%n", cars.get(1), cars.get(2));
//			}
//			
//			BasicDBObject bs = new BasicDBObject("name", new BasicDBObject("$eq", "Lisa"));
//			
//			collection.find(bs).iterator().forEachRemaining(doc -> System.out.println(doc.toJson()));
			
			
			//create collection and insert documents:
			database.createCollection("Cars"); //collection name
			MongoCollection<Document> carCollection = database.getCollection("Cars");

			List<Document> docsList = new ArrayList<Document>();

			Document d1 = new Document();
			d1.append("name", "Audi");
			d1.append("price", 70);
			docsList.add(d1);
			
			Document d2 = new Document();
			d2.append("name", "BMW");
			d2.append("price", 80);
			docsList.add(d2);
			
			Document d3 = new Document();
			d3.append("name", "Merck");
			d3.append("price", 65);
			docsList.add(d3);
			
			Document d4 = new Document();
			d4.append("name", "Honda");
			d4.append("price", 25);
			docsList.add(d4);
			
			BasicDBObject document = new BasicDBObject();
			document.put("name", "lokesh");
			document.put("website", "howtodoinjava.com");

			carCollection.insertMany(docsList);
			
			BasicDBObject bs1 = new BasicDBObject("name", new BasicDBObject("$eq", "Audi"));
			carCollection.deleteOne(bs1);
			
		}
	}

