package com.heros.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.heros.model.Hero;

@Service
public class CallService {

	Map<String, Hero> map = new HashMap<String, Hero>();
	List<String> CharacterList = new ArrayList<String>();
	List<Hero> namelist = new ArrayList<Hero>();
	Map<String, Hero> map2 = new HashMap<String, Hero>();
	static int min = 0;
	int counterOfChar = 1;
	int countOfChar;

	// Connection to database
	private Connection getConnection() throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/superhero", "root", "root");
		return connection;

	}

	// Method to fetch data from api
	public Map<String, Hero> callCharacters(String uri) throws JsonMappingException, JsonProcessingException {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		String jsonString = (String) response.getBody();
		JSONObject jsonObject = new JSONObject(jsonString);
		System.out.println(jsonObject.getString("name"));
		JSONArray jsonArray = jsonObject.getJSONArray("character");
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			String heroName = obj.getString("name");
			int maxPower = obj.getInt("max_power");
			Hero hero = new Hero(heroName, maxPower);
			map.put(heroName, hero);
			CharacterList.add(heroName);
		}
		return map;
	}

	// Method to save Characters
	public Hero saveNames(HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		Hero hero = new Hero(name);
		Hero hero1 = new Hero();
		Hero heros = new Hero();
		// System.out.println(name);

		for (Map.Entry<String, Hero> entry : map.entrySet()) {
			if (name.equalsIgnoreCase(entry.getKey())) {
				hero1 = entry.getValue();
				System.out.println(hero1.toString());
				String name1 = hero1.getName();
				int power = hero1.getMax_power();
				int count = hero1.getCount();
				count++;
				Connection connection = getConnection();

				PreparedStatement statement = connection.prepareStatement(
						"insert into heros(name,power,count) values(?,?,?) on duplicate key update count=count+1");
				statement.setString(1, name1);
				statement.setInt(2, power);
				statement.setInt(3, count);
				statement.executeUpdate();

			}
		}
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("select * from heros");
		ResultSet res = preparedStatement.executeQuery();
		while (res.next()) {
			String names = res.getString("name");
			int powers = res.getInt("power");
			int counts = res.getInt("count");
			Hero hero3 = new Hero(names, powers, counts);

			map2.put(names, hero3);

		}

		for (Map.Entry<String, Hero> entry : map2.entrySet()) {
			if (hero.getName().equalsIgnoreCase(entry.getKey())) {
				heros = entry.getValue();
			}

			if (namelist.size() < 15 && min <= 14) {
				if (hero.getName().equalsIgnoreCase(entry.getKey())) {
					Hero h2 = entry.getValue();
					 insertInto(h2);
					
				}

			} else {
				System.out.println("sort and remove");
				sortAndRemove(heros);
			}
		}
		return null;

	}

	// Method to add character to list
	public void insertInto(Hero h) {
		if (namelist.size() != 0 && min<=14) {
			if (namelist.contains(h)) {
				int index = namelist.indexOf(h);
				storeCharacters(h, index);
			} else {
				addToList(h);
			}

		} else {
			namelist.add(h);
		}

		 showList();
		
	}

	// Method to replace the existing character
	public void storeCharacters(Hero h, int index) {
		System.out.println(index);
		namelist.set(index, h);
	}

	// Method that adds characters into list
	public void addToList(Hero h) {
		namelist.add(h);
	}

	// Method used to sort and remove the character
	public void sortAndRemove(Hero hero) {
		if (min == 15 || min>15) {
			min = min - 1;
			Collections.sort(namelist);
			namelist.remove(0);
			insertInto(hero);
			counterOfChar = 0;
			for (Hero heros : namelist) {
				countOfChar = heros.getCount();
				counterOfChar += countOfChar;
			}
			min = counterOfChar;
			System.out.println("************************");
			System.out.println(min);
			System.out.println(namelist);
		}

	}

	// Method to display the list
	public void showList() {
		counterOfChar = 0;
		for (Hero heros : namelist) {
			countOfChar = heros.getCount();
			counterOfChar += countOfChar;
		}
		min = counterOfChar;
		System.out.println("//////////////////////////////////");
		System.out.println(min);
		System.out.println("///////////////////////////////////");
		System.out.println("///////////////////////////////////");
		System.out.println("///////////////////////////////////");
		for(Hero h:namelist) {
			System.out.println(h);
		}
		
	}

	public void emptyCharacters() throws Exception {
		Connection connection = getConnection();
		String sql = "Delete from heros";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.executeUpdate();
	}

}
