package com.alumni.repository;

import com.alumni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(User.Role role);

    @Query("SELECT u FROM User u WHERE u.role = 'ALUMNI' AND " +
           "(LOWER(u.company) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.department) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.skills) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<User> searchAlumni(@Param("keyword") String keyword);

    @Query("SELECT u FROM User u WHERE u.role = 'ALUMNI' AND LOWER(u.company) LIKE LOWER(CONCAT('%', :company, '%'))")
    List<User> findAlumniByCompany(@Param("company") String company);

    @Query("SELECT u FROM User u WHERE u.role = 'ALUMNI' AND LOWER(u.department) LIKE LOWER(CONCAT('%', :domain, '%'))")
    List<User> findAlumniByDomain(@Param("domain") String domain);
}
