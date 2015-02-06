package assignment2;

import java.util.Hashtable;
import java.util.List;

public class ReturnObject {
	List<String> List;
	int numberOfWords;
	public ReturnObject(List<String> List, int numberOfWords){
		this.List = List;
		this.numberOfWords = numberOfWords;
	}
	public ReturnObject TempFunction(List<String> List, int numberOfWords){
		return new ReturnObject(List,numberOfWords);
	}

}