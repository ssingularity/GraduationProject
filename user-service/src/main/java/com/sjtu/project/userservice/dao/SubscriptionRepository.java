package com.sjtu.project.userservice.dao;

import com.sjtu.project.userservice.domain.Subscription;
import com.sjtu.project.userservice.domain.SubscriptionStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    List<Subscription> findAllByResourceIdAndUsername(String resourceId, String username);

    List<Subscription> findAllByResourceOwnerAndStatusIs(String username, SubscriptionStatus status);

    List<Subscription> findAllByUsername(String username, Sort sort);
}
