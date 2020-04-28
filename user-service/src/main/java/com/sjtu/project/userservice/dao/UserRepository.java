package com.sjtu.project.userservice.dao;

import com.sjtu.project.userservice.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findOneByPhoneNumber(String phoneNumber);

    Optional<User> findOneByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByPhoneNumber(String phoneNumber);

    User findOneById(String id);

    @Override
    Optional<User> findById(String id);
}
