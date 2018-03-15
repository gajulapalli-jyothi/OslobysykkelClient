package com.oslobysykkel.no.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oslobysykkel.no.pojo.Stations;
import com.oslobysykkel.no.service.ApplicationService;



/**
 * Controller class, responsible for mapping  application requests to appropriate methods and views
 *
 */
@Controller
@PropertySource("classpath:application.properties")
public class ApplicationController {
	private static Logger logger = Logger.getLogger(ApplicationController.class);
	
	@Value("${client.id}")
	private String clientID;

	@Value("${endpoint}")
	private String endpointURL;

	
	@RequestMapping(value = "/stations", method = RequestMethod.GET)
	public String claims(HttpServletRequest request, ModelMap model) throws Exception {
		ApplicationService service = new ApplicationService();
		List<Stations> stList = service.getDetails(endpointURL, clientID);
		logger.info("result:" + stList);
		model.put("stations", stList);
		return "status";
	}

}
