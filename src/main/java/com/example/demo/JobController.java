package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JobController {

    private final JobRepository jobRepository;
    private final JobApplicationRepository applicationRepository;

    public JobController(JobRepository jobRepository, JobApplicationRepository applicationRepository) {
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    // Redirect root URL to Login page
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    // Displays main job dashboard with optional keyword search
    @GetMapping("/dashboard")
    public String viewHomePage(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("jobs", jobRepository.findAll().stream()
                .filter(job -> job.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                               job.getLocation().toLowerCase().contains(keyword.toLowerCase()))
                .toList());
        } else {
            model.addAttribute("jobs", jobRepository.findAll());
        }
        return "index";
    }

    // Form to post a job
    @GetMapping("/add-job")
    public String showAddJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "add-job";
    }

    // Save job posting
    @PostMapping("/save-job")
    public String saveJob(@ModelAttribute("job") Job job) {
        jobRepository.save(job);
        return "redirect:/dashboard";
    }

    // Delete a job posting
    @GetMapping("/delete-job/{id}")
    public String deleteJob(@PathVariable("id") Long id) {
        jobRepository.deleteById(id);
        return "redirect:/dashboard";
    }

    // Form to edit a job
    @GetMapping("/edit-job/{id}")
    public String showEditJobForm(@PathVariable("id") Long id, Model model) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        model.addAttribute("job", job);
        return "edit-job";
    }

    // Apply for a job
    @GetMapping("/apply-job/{id}")
    public String applyForJob(@PathVariable("id") Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid job Id:" + id));
        
        // Save job details into applied jobs database table
        JobApplication app = new JobApplication("user@example.com", job.getTitle(), job.getCompanyName(), job.getLocation());
        applicationRepository.save(app);

        return "redirect:/applied-jobs";
    }

    // View all applied jobs
    @GetMapping("/applied-jobs")
    public String viewAppliedJobs(Model model) {
        model.addAttribute("applications", applicationRepository.findAll());
        return "applied-jobs";
    }
}