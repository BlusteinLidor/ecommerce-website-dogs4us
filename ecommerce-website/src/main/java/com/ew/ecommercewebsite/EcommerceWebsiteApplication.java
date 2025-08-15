package com.ew.ecommercewebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The main Spring Boot application class for the E-commerce website.
 * This class serves as the entry point for the application and initializes the Spring Boot context.
 *
 * @SpringBootApplication Enables auto-configuration and component scanning
 */
@SpringBootApplication
public class EcommerceWebsiteApplication {

    /**
     * The main method which serves as the entry point for the Spring Boot application.
     * It starts the Spring application context and runs the application.
     *
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(EcommerceWebsiteApplication.class, args);
    }
}
