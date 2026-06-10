package com.alumni.service;

import com.alumni.entity.User;
import com.alumni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateProfile(String email, User updatedUser) {
        User user = getCurrentUser(email);
        user.setFullName(updatedUser.getFullName());
        user.setBio(updatedUser.getBio());
        user.setCompany(updatedUser.getCompany());
        user.setJobTitle(updatedUser.getJobTitle());
        user.setLocation(updatedUser.getLocation());
        user.setSkills(updatedUser.getSkills());
        user.setLinkedinUrl(updatedUser.getLinkedinUrl());
        user.setPhone(updatedUser.getPhone());
        user.setDepartment(updatedUser.getDepartment());
        user.setGraduationYear(updatedUser.getGraduationYear());
        user.setDegree(updatedUser.getDegree());
        return userRepository.save(user);
    }

    public List<User> getAllAlumni() {
        return userRepository.findByRole(User.Role.ALUMNI);
    }

    public List<User> searchAlumni(String keyword) {
        return userRepository.searchAlumni(keyword);
    }

    public List<User> findAlumniByCompany(String company) {
        return userRepository.findAlumniByCompany(company);
    }

    public List<User> findAlumniByDomain(String domain) {
        return userRepository.findAlumniByDomain(domain);
    }
}
