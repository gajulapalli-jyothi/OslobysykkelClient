package com.oslobysykkel.no;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;

/**
 * Entry class, starts the SpringBoot application
 *
 */
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@PropertySource("classpath:application.properties")
public class MainApplication {
	private static final Logger LOGGER = Logger.getLogger(MainApplication.class);

	@Value("${base.url}")
	private String baseUrl;
	
	@Value("#{systemProperties['user.dir']}#{systemProperties['file.separator']}${logging.file}") 
	private String loggingPath;
	

	/**
	 * Write logs into console and file, after server started successfully
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		LOGGER.info("Demo app started");
		System.out.println("\n\nLog file path: "+ loggingPath);
		System.out.println("\n\nPlease point your preferred browser to " + baseUrl + "/stations");
	}
	
	public static void main(String[] args) {
		
		SpringApplication.run(MainApplication.class, args);
	}

}
