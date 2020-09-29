package MongoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumMongoDB {

	public static void main(String[] args) {

		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);

		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

		MongoDatabase database = mongoClient.getDatabase("automationDB");
		
		//create collection and insert documents:
		database.createCollection("Amazon"); //collection name
		MongoCollection<Document> amazonCollection = database.getCollection("Amazon");
		
		WebDriverManager.chromedriver().setup();
		ChromeOptions co = new ChromeOptions();
		co.addArguments("--headless");
		WebDriver driver = new ChromeDriver(co);
		driver.get("http://www.amazon.com");
		
		String url = driver.getCurrentUrl();
		String title = driver.getTitle();
		
		List<Document> docsList = new ArrayList<Document>();

		Document d1 = new Document();
		d1.append("url", url);
		d1.append("title", title);
		docsList.add(d1);
		
		amazonCollection.insertMany(docsList);
		
	}

}
