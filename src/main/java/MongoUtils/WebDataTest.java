package MongoUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDataTest {

	WebDriver driver;
	MongoCollection<Document> amazonCollection;

	@BeforeSuite
	public void connectMongoDB() {
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
		MongoDatabase database = mongoClient.getDatabase("automationDB");
		// create collection and insert documents:
		database.createCollection("web"); // collection name
		amazonCollection = database.getCollection("web");
	}

	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		ChromeOptions co = new ChromeOptions();
		co.addArguments("--headless");
		driver = new ChromeDriver(co);

	}

	@DataProvider(parallel=true)
	public Object[][] getWebData() {
		return new Object[][] { 
			{ "http://www.google.com" }, 
			{ "http://www.amazon.com" } 
		};
	}

	@Test(dataProvider = "getWebData")
	public void webScrapeTest(String url) {
		driver.get(url);
		String fullUrl = driver.getCurrentUrl();
		String title = driver.getTitle();
		int linkCount = driver.findElements(By.tagName("a")).size();
		int imageCount = driver.findElements(By.tagName("img")).size();
		List<WebElement> linksList = driver.findElements(By.tagName("a"));
		List<String> attrList = new ArrayList<String>();
		

		List<Document> docsList = new ArrayList<Document>();

		Document d1 = new Document();
		d1.append("url", fullUrl);
		d1.append("title", title);
		d1.append("linkCount", linkCount);
		d1.append("imageCount", imageCount);
		
		for(WebElement ele : linksList){
			String src = ele.getAttribute("href");
			attrList.add(src);
			d1.append("linkAttribute", attrList);
		}
		
		docsList.add(d1);

		amazonCollection.insertMany(docsList);

	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
