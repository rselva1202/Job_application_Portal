package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final JobRepository jobRepository;

    public DataInitializer(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Clear previous entries and insert 5 fresh samples
        jobRepository.deleteAll();

        jobRepository.save(new Job("Java Developer", "TCS", "Chennai", 650000.0, "Build and maintain enterprise REST APIs using Spring Boot."));
        jobRepository.save(new Job("Data Analyst", "SPYM Solution", "Tirunelveli", 350000.0, "Analyze dataset metrics using MS Excel, SQL, and Power BI."));
        jobRepository.save(new Job("Frontend Engineer", "Zoho", "Tenkasi", 500000.0, "Develop responsive user interfaces using HTML, CSS, JavaScript, and React."));
        jobRepository.save(new Job("Cloud Operations Engineer", "Infosys", "Bengaluru", 800000.0, "Manage AWS cloud infrastructure, CI/CD pipelines, and Docker containers."));
        jobRepository.save(new Job("QA Automation Tester", "Wipro", "Hyderabad", 450000.0, "Design and run automated test scripts using Selenium and JUnit."));
        
        System.out.println("Sample jobs successfully initialized into MySQL!");
    }
}