package com.example.application;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
@Theme(value = "demo")
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application implements AppShellConfigurator {

    private static final long serialVersionUID = 2362375656349024936L;

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String pass = "admin";
//        String encodedPass = bCryptPasswordEncoder.encode(pass);
//        System.out.println(encodedPass);
        
    }
}
