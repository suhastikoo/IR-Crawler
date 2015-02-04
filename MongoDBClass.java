package assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBClass {
	
static int Maxlen;
static String urlmax;
public void addpage(String text,String url){
	String normalisedtext = text.replaceAll("[^\\dA-Za-z]", " ");
	List<String> tokens = new ArrayList<String>();
	StringTokenizer token = new StringTokenizer(text);
	
	while(token.hasMoreTokens()){
		tokens.add(token.nextToken());
	}
	if(Maxlen<tokens.size()){
		Maxlen = tokens.size();
		urlmax=url;
	}
	
    // To connect to mongodb server
    MongoClient mongoClient;
	try {
		
		mongoClient = new MongoClient( "localhost" , 27017 );
        DB db = mongoClient.getDB( "IR1" );
		DBCollection webColl = db.getCollection("Webpages");
		DBObject obj = new BasicDBObject();
		obj.put("url", url);
		obj.put("text", tokens);
		webColl.save(obj);
	}
	catch(Exception e){
		System.out.println(" error in db page addition");
	}
}
}