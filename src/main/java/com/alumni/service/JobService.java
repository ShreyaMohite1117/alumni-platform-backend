package com.alumni.service;

import com.alumni.entity.Job;
import com.alumni.entity.User;
import com.alumni.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired private JobRepository jobRepository;
    @Autowired private UserService userService;

    public Job createJob(Job job, String email) {
        User user = userService.getCurrentUser(email);
        job.setPostedBy(user);
        return jobRepository.save(job);
    }

    public List<Job> getAllActiveJobs() {
        return jobRepository.findByActiveTrue();
    }

    public List<Job> searchJobs(String keyword) {
        return jobRepository.searchJobs(keyword);
    }

    public List<Job> getJobsByType(String type) {
        return jobRepository.findByTypeAndActiveTrue(type);
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public Job updateJob(Long id, Job updatedJob, String email) {
        Job job = getJobById(id);
        User user = userService.getCurrentUser(email);
        if (!job.getPostedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized");
        }
        job.setTitle(updatedJob.getTitle());
        job.setCompany(updatedJob.getCompany());
        job.setDescription(updatedJob.getDescription());
        job.setLocation(updatedJob.getLocation());
        job.setSalary(updatedJob.getSalary());
        job.setType(updatedJob.getType());
        job.setApplyLink(updatedJob.getApplyLink());
        job.setSkills(updatedJob.getSkills());
        job.setDomain(updatedJob.getDomain());
        return jobRepository.save(job);
    }

    public void deleteJob(Long id, String email) {
        Job job = getJobById(id);
        User user = userService.getCurrentUser(email);
        if (!job.getPostedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Not authorized");
        }
        job.setActive(false);
        jobRepository.save(job);
    }
}
