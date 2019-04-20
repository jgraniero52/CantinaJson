package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONObject;

public class Client {

	/**
	 * @return The JSONObject of the json from the URL provided
	 */
	public final JSONObject getJsonFromUrl(String url) {
		try {
			InputStream inputStreamFromUrl = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamFromUrl, Charset.forName("UTF-8")));
			String jsonText = readJson(reader);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * @param reader - The input stream of the json from the json
	 * @return String version of the text
	 */
	private final String readJson(final BufferedReader reader) {
		StringBuilder stringBuilder = new StringBuilder();	
		String line;
		try {
			while((line = reader.readLine()) != null){
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//To debug and make sure i'm actually getting the json correctly
		//System.out.print(stringBuilder.toString());
		return stringBuilder.toString();
	}
}
