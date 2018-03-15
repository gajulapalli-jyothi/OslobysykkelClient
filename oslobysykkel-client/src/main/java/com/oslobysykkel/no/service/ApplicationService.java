package com.oslobysykkel.no.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oslobysykkel.no.pojo.Stations;

public class ApplicationService {

	/**
	 * Get Details
	 * @param endpointURL
	 * @param clientID
	 * @return
	 * @throws Exception
	 */
	public List<Stations> getDetails(String endpointURL, String clientID) throws Exception {

		String stationjson = getStations(endpointURL + "/stations", clientID);
		Map<String, String> stationsMap = getStationsMap(stationjson);

		String availjson = getStations(endpointURL + "/stations/availability", clientID);
		List<Stations> sts = getAvailObj(availjson);
		for (Stations ss : sts) {
			ss.setTitle(stationsMap.get(ss.getId()));
		}
		return sts;
	}

	/**
	 * Get Stations
	 * @param endpointURL
	 * @param clientID
	 * @return
	 */
	private String getStations(String endpointURL, String clientID) {
		StringBuilder response = new StringBuilder();
		HttpURLConnection connection = null;

		try {
			// Create connection
			URL url = new URL(endpointURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Client-Identifier", clientID);
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response.toString();
	}

	/**
	 * @param stationsjson
	 * @return
	 */
	private Map<String, String> getStationsMap(String stationsjson) {

		Map<String, String> map = new HashMap<String, String>();
		JsonParser parser = new JsonParser();
		JsonObject jo = (JsonObject) parser.parse(stationsjson);

		// Parsing back the string as Array
		JsonArray ja = (JsonArray) parser.parse(jo.get("stations").toString());
		for (JsonElement je : ja) {
			JsonObject j = (JsonObject) je;
			map.put(j.get("id").getAsString(), j.get("title").getAsString());
		}
		return map;
	}

	/**
	 * @param availjson
	 * @return
	 */
	private List<Stations> getAvailObj(String availjson) {

		List<Stations> stationsList = new ArrayList<Stations>();
		JsonParser parser = new JsonParser();
		JsonObject jo = (JsonObject) parser.parse(availjson);

		// Parsing back the string as Array
		JsonArray ja = (JsonArray) parser.parse(jo.get("stations").toString());
		for (JsonElement je : ja) {
			JsonObject j = (JsonObject) je;
			Stations st = new Stations();
			st.setId(j.get("id").getAsString());

			JsonElement b = j.get("availability");
			JsonObject c = (JsonObject) b;

			st.setBikes(c.get("bikes").toString());
			st.setLocks(c.get("locks").toString());

			stationsList.add(st);
		}
		return stationsList;
	}

}
