package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.taskmanager") // Ensure this package is scanned
public class TaskmanagerApplication {

    public static void main(String[] args) {
        // Get the application context after running the application
        ApplicationContext context = SpringApplication.run(TaskmanagerApplication.class, args);
        
        // Get all the beans that have been loaded by Spring Boot
        String[] beanNames = context.getBeanDefinitionNames();
        
        // Sort the bean names for better readability
        Arrays.sort(beanNames);
        
        // Print out the names of all beans
        System.out.println("List of all beans in the application context:");
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}