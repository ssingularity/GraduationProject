package com.sjtu.project.userservice.service;

import com.sjtu.project.common.exception.ObjectNotFoundException;
import com.sjtu.project.userservice.dao.SubscriptionRepository;
import com.sjtu.project.userservice.domain.Subscription;
import com.sjtu.project.userservice.domain.SubscriptionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription subscribeResource(Subscription subscription) {
        subscription.verifySelf();
        return subscriptionRepository.save(subscription);
    }

    public void updateSubscription(String subscriptionId, SubscriptionStatus targetStatus) {
        Optional<Subscription> res = subscriptionRepository.findById(subscriptionId);
        if (!res.isPresent()) {
            throw new ObjectNotFoundException("订阅请求");
        }

        Subscription subscription = res.get();
        subscription.update(targetStatus);
        subscriptionRepository.save(subscription);
    }
}
