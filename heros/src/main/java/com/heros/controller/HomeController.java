package com.heros.controller;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.heros.model.Hero;
import com.heros.service.CallService;

@Controller
public class HomeController {

	@Autowired
	private CallService callService;
	Map<String, Hero> map = new HashMap<String, Hero>();
	List<String> CharacterList = new ArrayList<String>();
	List<Hero> namelist = new ArrayList<Hero>();
	int counter = 0;

	@RequestMapping(value = "/")
	public ModelAndView test(HttpServletResponse response) throws IOException {
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/home")
	public String hello() {
		return "test";
	}

	@RequestMapping(value = "/characters")
	public void method(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws JsonMappingException, JsonProcessingException {
		callCharacters();
		session = request.getSession();
		session.setAttribute("list", map);

	}

	@Scheduled(fixedRate = 10000)
	public void callCharacters() throws JsonMappingException, JsonProcessingException {

		callService.callCharacters("http://www.mocky.io/v2/5ecfd5dc3200006200e3d64b");
		callService.callCharacters("http://www.mocky.io/v2/5ecfd630320000f1aee3d64d");
		map = callService.callCharacters("http://www.mocky.io/v2/5ecfd6473200009dc1e3d64e");

	}

	@RequestMapping(value = "/saveName", method = RequestMethod.POST)
	public void saveNames(@RequestParam(value = "names") String name, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		++counter;
		if (counter == 1) {
			callService.emptyCharacters();
		}
		Hero hero = new Hero();

		hero = callService.saveNames(request, response, name);

		System.out.println(hero);

	}

}
