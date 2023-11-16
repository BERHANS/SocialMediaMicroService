package com.berhan.repository;

import com.berhan.repository.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserProfile,Long> {

    Optional<UserProfile> findByAuthId(Long dto);

    Optional<UserProfile> findOptionalByAuthId(Long authId);
}
