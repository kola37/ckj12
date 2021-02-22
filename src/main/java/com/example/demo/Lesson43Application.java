package com.example.demo;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Note;
import com.example.demo.repo.NoteRepository;

@SpringBootApplication
public class Lesson43Application {

	public static void main(String[] args) {
		SpringApplication.run(Lesson43Application.class, args);
	}

//	@Bean
//	public ServletWebServerFactory webServer() {
//		// переопределили поведение встроенного веб-сервера
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//			@Override
//			protected void postProcessContext(Context context) {
//				SecurityConstraint constraint = new SecurityConstraint();
//				constraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				constraint.addCollection(collection);
//				context.addConstraint(constraint);
//			}
//		};
//		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
//
//		return tomcat;
//	}
//
//	public Connector httpToHttpsRedirectConnector() {
//		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//		connector.setScheme("http");
//		connector.setPort(8080);
//		connector.setSecure(false);
//		connector.setRedirectPort(8443);
//		return connector;
//	}

//	@Bean
//	public CommandLineRunner commandLineRunner(NoteRepository noteRepository) {
//		CommandLineRunner runner = (args)->{
//			noteRepository.save(new Note("first label","first text"));
//			noteRepository.save(new Note("second label","second text"));
//			noteRepository.save(new Note("third label","third text"));
//		};
//		return runner;
//	}
}