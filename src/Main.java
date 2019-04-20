import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONObject;

import client.Client;
import client.JsonConsumer;

/**
 * 
 */

/**
 * @author jgran
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client();
		JSONObject json = client.getJsonFromUrl("https://raw.githubusercontent.com/jdolan/quetoo/master/src/cgame/default/ui/settings/SystemViewController.json");
		
		JsonConsumer consumer = new JsonConsumer();
		consumer.initialUserInput(json);
	}
}
