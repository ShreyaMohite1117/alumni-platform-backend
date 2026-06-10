package com.alumni.repository;

import com.alumni.entity.MentorshipRequest;
import com.alumni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorshipRequestRepository extends JpaRepository<MentorshipRequest, Long> {

    List<MentorshipRequest> findByMentee(User mentee);

    List<MentorshipRequest> findByMentor(User mentor);

    List<MentorshipRequest> findByMentorAndStatus(User mentor, MentorshipRequest.Status status);

    boolean existsByMenteeAndMentorAndStatus(User mentee, User mentor, MentorshipRequest.Status status);
}
