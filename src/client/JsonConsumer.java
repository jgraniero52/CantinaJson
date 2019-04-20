package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonConsumer {

	private static int resultsCounter = 0;
	/**
	 * @param json - the JSONObject coming from the main.
	 */
	public void initialUserInput(JSONObject json) {
		//Enter data using BufferReader 
		System.out.println("To select a view, please type the class, classNames, or identifier first followed by a colon and the value.");
		System.out.println("For example type class:Input to look for all classes of type input (type exit to close.)");
		System.out.println("The output will display the information from the view that contains what you're searching for.");
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
	         
	        String selector;
			
			selector = reader.readLine();
			String[] input = selector.split(":");
	        
			if(selector.equals("exit")){
	        	return;
	        }
			else if(!selector.equals("")) {
				displayInformation(input, json);
				System.out.println(resultsCounter + " results found.");
				System.out.println("**********************");
				resultsCounter = 0;
				initialUserInput(json);
			}
	        else {
	        	System.out.println("Result not found. \n");
	        	resultsCounter = 0;
	        	initialUserInput(json);
	        }
		}
		catch(Exception e){
			System.out.println("Result not found.");
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * @param selector - input from the user to traverse the json and find the views
	 * @param json - original json from file
	 */
	private void displayInformation(final String[] selector, final JSONObject json) {
		System.out.println("Results: \n");
		if(json.has("subviews")) {
			getSubViews(selector, json.getJSONArray("subviews"));
		}
	}
	
	/**
	 * @param selector - input from the user for which view to display
	 * @param json - gets the subview for supplied json array
	 */
	private void getSubViews(final String[] selector, final JSONArray json) {
		for(int i = 0 ; i < json.length() ; i++){
			if(json.getJSONObject(i).has(selector[0])) {
				displayView(selector, json.getJSONObject(i));
			}
			if(json.getJSONObject(i).has("subviews")) {
				getSubViews(selector, json.getJSONObject(i).getJSONArray("subviews"));
			}
			
			if(json.getJSONObject(i).has("contentView")) {
				getSubViews(selector, json.getJSONObject(i).getJSONObject("contentView").getJSONArray("subviews"));
			}
		}
	}
	
	/**
	 * @param selector - input from the user for which view to display
	 * @param json - json array which you want to display the view from
	 */
	private void displayView (final String[] selector, final JSONObject json) {
		if(json.has(selector[0])) {
			if(selector[0].equals("classNames")) {
				boolean hasSearch = false;
				if(json.has("classNames")) {
					JSONArray classNameArray = json.getJSONArray("classNames");
					for(int names = 0; names < classNameArray.length(); names++) {
						if(classNameArray.getString(names).equals(selector[1])) {
							hasSearch = true;
						}
					}
				}
				if(hasSearch) {
					printValues(selector, json);
				}
			}
			else if(json.getString(selector[0]).equals(selector[1])) {
				printValues(selector, json); 
			}
		}
		
		if(json.has("control")) {
			if(json.getJSONObject("control").optString(selector[0]).equals(selector[1])) {
				printValues(selector, json.getJSONObject("control"));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void printValues (final String[] selector, final JSONObject json) {
		Set<String> keySet = (Set<String>) json.keySet();
		for(String key : keySet) {
			if(key.equals("class") || key.equals("classNames") || key.equals("identifier")) {
				System.out.println(key+" : " + json.optString(key));
			}
		}
		System.out.println(); 
		resultsCounter++;
	}
}
